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

package anyframe.iam.admin.common.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.framework.Advised;

/**
 * This LoggingAspect class contains beforeLogging method for logging through
 * aspect service
 * @author Taeho Kim
 */
public class LoggingAspect {

	/**
	 * catch and logging through aspect service ( setting :
	 * [src/main/resources/spring/common/context-aspect.xml] )
	 * 
	 * @param thisJoinPoint aspect service JoinPoint
	 */
	public void beforeLogging(JoinPoint thisJoinPoint) {

		Object target = thisJoinPoint.getTarget();

		while (target instanceof Advised) {
			try {
				target = ((Advised) target).getTargetSource().getTarget();
			}
			catch (Exception e) {
				LogFactory.getLog(this.getClass()).error("Fail to get target object from JointPoint.", e);
			}
		}

		if (target == null)
			return;

		String className = target.getClass().getSimpleName();
		String opName = (thisJoinPoint.getSignature().getName());

		StringBuffer buf = new StringBuffer();
		buf.append("\n** Logging Aspect : executed " + opName + "() in " + className + " Class.");
		Object[] arguments = thisJoinPoint.getArgs();
		if (arguments.length > 0) {
			buf.append("\n************* " + arguments[0].getClass().getName() + " *************\n");
			buf.append(arguments[0].toString());
			buf.append("\n*********************************************************\n");
		}
		else
			buf.append("\nNo arguments\n");

		Log logger = LogFactory.getLog(target.getClass());
		if (logger.isDebugEnabled())
			logger.debug(buf.toString());
	}
}