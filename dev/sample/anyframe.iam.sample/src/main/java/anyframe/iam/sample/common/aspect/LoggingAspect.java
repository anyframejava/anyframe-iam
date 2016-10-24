package anyframe.iam.sample.common.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

public class LoggingAspect {
	@SuppressWarnings("unchecked")
	public void beforeLogging(JoinPoint thisJoinPoint) {
		Class clazz = thisJoinPoint.getTarget().getClass();
		String className = (thisJoinPoint.getTarget().getClass().getName())
				.toLowerCase();
		String methodName = thisJoinPoint.getSignature().getName();

		StringBuffer buf = new StringBuffer();
		buf.append("\n** Logging Aspect : executed " + methodName + "() in "
				+ className + " Class.");
		Object[] arguments = thisJoinPoint.getArgs();
		if (arguments.length > 0) {
			for (int i = 0; i < arguments.length; i++) {
				buf
						.append("\n*************"
								+ arguments[i].getClass().getName()
								+ "*************\n");
				buf.append(arguments[i].toString());
				buf.append("\n*******************************************\n");
			}
		} else
			buf.append("\nNo arguments\n");

		Log logger = LogFactory.getLog(clazz);
		if (logger.isDebugEnabled())
			logger.debug(buf.toString());
	}
}
