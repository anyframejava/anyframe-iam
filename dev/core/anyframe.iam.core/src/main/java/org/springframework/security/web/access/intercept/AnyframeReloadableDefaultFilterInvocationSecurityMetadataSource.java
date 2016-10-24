/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.web.access.intercept;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.UrlMatcher;

import anyframe.common.exception.BaseException;
import anyframe.iam.core.securedobject.ISecuredObjectService;

/**
 * Current secured resources based on DB with URL type - Extension class of
 * DefaultFilterInvocationDefinitionSource of Spring Security to reflect directly
 * authority mapping information in runtime.
 * It follows Spring Security package for extension of method situated default modifier.
 *
 * @author ByungHun Woo
 */
public class AnyframeReloadableDefaultFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource,
		ApplicationContextAware {

    private static final Set<String> HTTP_METHODS = new HashSet<String>(Arrays.asList("DELETE", "GET", "HEAD", "OPTIONS", "POST", "PUT", "TRACE"));

    protected final Log logger = LogFactory.getLog(getClass());

    //private Map<Object, List<ConfigAttribute>> requestMap = new LinkedHashMap<Object, List<ConfigAttribute>>();
    /** Stores request maps keyed by specific HTTP methods. A null key matches any method */
    private Map<String, Map<Object, Collection<ConfigAttribute>>> httpMethodMap =
        new HashMap<String, Map<Object, Collection<ConfigAttribute>>>();

    private UrlMatcher urlMatcher;

    private boolean stripQueryStringFromUrls;

    //~ Constructors ===================================================================================================

    /**
     * Builds the internal request map from the supplied map. The key elements should be of type {@link RequestKey},
     * which contains a URL path and an optional HTTP method (may be null). The path stored in the key will depend on
     * the type of the supplied UrlMatcher.
     *
     * @param urlMatcher typically an ant or regular expression matcher.
     * @param requestMap order-preserving map of request definitions to attribute lists
     */
    public AnyframeReloadableDefaultFilterInvocationSecurityMetadataSource(UrlMatcher urlMatcher,
            LinkedHashMap<RequestKey, Collection<ConfigAttribute>> requestMap) {
        this.urlMatcher = urlMatcher;

        for (Map.Entry<RequestKey, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            addSecureUrl(entry.getKey().getUrl(), entry.getKey().getMethod(), entry.getValue());
        }
    }

    //~ Methods ========================================================================================================

    /**
     * Adds a URL,attribute-list pair to the request map, first allowing the <tt>UrlMatcher</tt> to
     * process the pattern if required, using its <tt>compile</tt> method. The returned object will be used as the key
     * to the request map and will be passed back to the <tt>UrlMatcher</tt> when iterating through the map to find
     * a match for a particular URL.
     */
    private void addSecureUrl(String pattern, String method, Collection<ConfigAttribute> attrs) {
        Map<Object, Collection<ConfigAttribute>> mapToUse = getRequestMapForHttpMethod(method);

        mapToUse.put(urlMatcher.compile(pattern), attrs);

        if (logger.isDebugEnabled()) {
            logger.debug("Added URL pattern: " + pattern + "; attributes: " + attrs +
                    (method == null ? "" : " for HTTP method '" + method + "'"));
        }
    }

    /**
     * Return the HTTP method specific request map, creating it if it doesn't already exist.
     * @param method GET, POST etc
     * @return map of URL patterns to <tt>ConfigAttribute</tt>s for this method.
     */
    private Map<Object, Collection<ConfigAttribute>> getRequestMapForHttpMethod(String method) {
        if (method != null && !HTTP_METHODS.contains(method)) {
            throw new IllegalArgumentException("Unrecognised HTTP method: '" + method + "'");
        }

        Map<Object, Collection<ConfigAttribute>> methodRequestMap = httpMethodMap.get(method);

        if (methodRequestMap == null) {
            methodRequestMap = new LinkedHashMap<Object, Collection<ConfigAttribute>>();
            httpMethodMap.put(method, methodRequestMap);
        }

        return methodRequestMap;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Map.Entry<String, Map<Object, Collection<ConfigAttribute>>> entry : httpMethodMap.entrySet()) {
            for (Collection<ConfigAttribute> attrs : entry.getValue().values()) {
                allAttributes.addAll(attrs);
            }
        }

        return allAttributes;
    }


    public Collection<ConfigAttribute> getAttributes(Object object) {
        if ((object == null) || !this.supports(object.getClass())) {
            throw new IllegalArgumentException("Object must be a FilterInvocation");
        }

        String url = ((FilterInvocation) object).getRequestUrl();
        String method = ((FilterInvocation) object).getHttpRequest().getMethod();

        return lookupAttributes(url, method);
    }

    /**
     * Performs the actual lookup of the relevant <tt>ConfigAttribute</tt>s for the given <code>FilterInvocation</code>.
     * <p>
     * By default, iterates through the stored URL map and calls the
     * {@link UrlMatcher#pathMatchesUrl(Object path, String url)} method until a match is found.
     *
     * @param url the URI to retrieve configuration attributes for
     * @param method the HTTP method (GET, POST, DELETE...), or null for any method.
     *
     * @return the <code>ConfigAttribute</code>s that apply to the specified <code>FilterInvocation</code>
     * or null if no match is found
     */
    public final Collection<ConfigAttribute> lookupAttributes(String url, String method) {
        if (stripQueryStringFromUrls) {
            // Strip anything after a question mark symbol, as per SEC-161. See also SEC-321
            int firstQuestionMarkIndex = url.indexOf("?");

            if (firstQuestionMarkIndex != -1) {
                url = url.substring(0, firstQuestionMarkIndex);
            }
        }

        if (urlMatcher.requiresLowerCaseUrl()) {
            url = url.toLowerCase();

            if (logger.isDebugEnabled()) {
                logger.debug("Converted URL to lowercase, from: '" + url + "'; to: '" + url + "'");
            }
        }

        // Obtain the map of request patterns to attributes for this method and lookup the url.
        Collection<ConfigAttribute> attributes = extractMatchingAttributes(url, httpMethodMap.get(method));

        // If no attributes found in method-specific map, use the general one stored under the null key
        if (attributes == null) {
            attributes = extractMatchingAttributes(url, httpMethodMap.get(null));
        }

        return attributes;
    }

    private Collection<ConfigAttribute> extractMatchingAttributes(String url, Map<Object, Collection<ConfigAttribute>> map) {
        if (map == null) {
            return null;
        }

        final boolean debug = logger.isDebugEnabled();

        for (Map.Entry<Object, Collection<ConfigAttribute>> entry : map.entrySet()) {
            Object p = entry.getKey();
            boolean matched = urlMatcher.pathMatchesUrl(entry.getKey(), url);

            if (debug) {
                logger.debug("Candidate is: '" + url + "'; pattern is " + p + "; matched=" + matched);
            }

            if (matched) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    protected UrlMatcher getUrlMatcher() {
        return urlMatcher;
    }

    public boolean isConvertUrlToLowercaseBeforeComparison() {
        return urlMatcher.requiresLowerCaseUrl();
    }

    public void setStripQueryStringFromUrls(boolean stripQueryStringFromUrls) {
        this.stripQueryStringFromUrls = stripQueryStringFromUrls;
    }

	/*********************************************************************************/

	private MessageSource messageSource;

	/**
	 * set ApplicationContext.
	 *
	 * @param applicationContext to be set by container
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.messageSource = (MessageSource) applicationContext.getBean("messageSource");
	}

	/**
	 * @return the messageSource
	 */
	protected MessageSource getMessageSource() {
		return messageSource;
	}

	private ISecuredObjectService securedObjectService;

	public void setSecuredObjectService(ISecuredObjectService securedObjectService) {
		this.securedObjectService = securedObjectService;
	}

	/**
	 * Reloading authority mapping information of secured resources
	 *
	 * @throws Exception
	 */
	public void reloadRequestMap() throws Exception {

		try {
			Map reloadedMap = securedObjectService.getRolesAndUrl();

			Iterator iterator = reloadedMap.entrySet().iterator();

			// 이전 데이터 삭제
			Map mapToUse = getRequestMapForHttpMethod(null);
			mapToUse.clear();

			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				RequestKey reqKey = (RequestKey) entry.getKey();
				addSecureUrl(reqKey.getUrl(), reqKey.getMethod(), (List<ConfigAttribute>) entry.getValue());
			}

			if (logger.isInfoEnabled()) {
				logger.info("Secured Url Resources - Role Mappings reloaded at Runtime!");
			}

		}
		catch (Exception e) {
			logger.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "Reload RequestMap" }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}
	}

	void addSecureUrl(String pattern, String method, List<ConfigAttribute> attr) {
		Map mapToUse = getRequestMapForHttpMethod(null);

		mapToUse.put(getUrlMatcher().compile(pattern), attr);

		if (logger.isDebugEnabled()) {
			logger.debug("Added URL pattern: " + pattern + "; attributes: " + attr
					+ (method == null ? "" : " for HTTP method '" + method + "'"));
		}
	}




}
