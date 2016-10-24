package com.sds.emp.common.web.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Administrator To change the template for
 *         this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class DebuggingFilter implements Filter {

    private boolean debug = true;

    private Log logger = null;

    /**
     * The filter configuration object we are
     * associated with. If this value is null, this
     * filter instance is not currently configured.
     */    
    private FilterConfig filterConfig = null;

    /**
     * Place this filter into service.
     * @param filterConfig
     *        The filter configuration object
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            logger = LogFactory.getLog("webLogger");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.filterConfig = filterConfig;
        if ("true".equalsIgnoreCase(filterConfig.getInitParameter("debug"))) {
            this.debug = true;
        } else {
            this.debug = false;
        }
    }

    /**
     * Take this filter out of service.
     */
    public void destroy() {

        this.debug = false;
        this.filterConfig = null;

    }

    /**
     * Select and set (if specified) the character
     * encoding to be used to interpret request
     * parameters for this request.
     * @param request
     *        The servlet request we are processing
     * @param result
     *        The servlet response we are creating
     * @param chain
     *        The filter chain we are processing
     * @exception IOException
     *            if an input/output error occurs
     * @exception ServletException
     *            if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        if (debug
            && (((HttpServletRequest) request).getRequestURI().endsWith(".do") || ((HttpServletRequest) request)
                .getRequestURI().endsWith(".jsp"))) {
            logger.debug("��û IP \t = "
                + ((HttpServletRequest) request).getRemoteAddr());
            logger.debug("��ûURL \t = "
                + ((HttpServletRequest) request).getRequestURI());
            String contentType = request.getContentType();
            if (contentType != null
                && contentType.startsWith("multipart/form-data")) {
                // printMultiPartsParams(request);
            } else {
                printParams(request);
            }
        }
        chain.doFilter(request, response);
    }

    private void printParams(ServletRequest request) {
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] values;
            values = request.getParameterValues(paramName);
            if (values.length == 1) {
                logger.debug(paramName + "\t = " + values[0]);
            } else {
                StringBuffer valueString = new StringBuffer();
                valueString.append(values[0]);
                for (int i = 1; i < values.length; i++) {
                    valueString.append("," + values[i]);
                }
                logger.debug(paramName + "\t = " + valueString.toString());
            }

        }

    }
}
