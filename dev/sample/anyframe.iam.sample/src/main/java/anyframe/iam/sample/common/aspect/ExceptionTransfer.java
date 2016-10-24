package anyframe.iam.sample.common.aspect;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.framework.Advised;
import org.springframework.context.MessageSource;

import anyframe.common.exception.BaseException;

import anyframe.iam.sample.common.SalesException;

public class ExceptionTransfer {

	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}	

	public void transfer(JoinPoint thisJoinPoint, Exception exception)
			throws SalesException {
		Object target = thisJoinPoint.getTarget();
		while (target instanceof Advised) {
			try {
				target = ((Advised) target).getTargetSource().getTarget();
			} catch (Exception e) {
				LogFactory.getLog(this.getClass()).error(
						"Fail to get target object from JointPoint.", e);
				break;
			}
		}

		String className = target.getClass().getSimpleName().toLowerCase();
		String opName = (thisJoinPoint.getSignature().getName()).toLowerCase();
		Log logger = LogFactory.getLog(target.getClass());

		if (exception instanceof SalesException) {
			SalesException empEx = (SalesException) exception;
			logger.error(empEx.getMessage(), empEx);
			throw empEx;
		}
		
		if (exception instanceof BaseException) {
			BaseException baseEx = (BaseException) exception;
			logger.error(baseEx.getMessage(), baseEx);
			throw new SalesException(messageSource, "error." + className + "."
					+ opName, new String[] {}, exception);
		}		

		logger.error(messageSource.getMessage("error." + className + "."
				+ opName, new String[] {}, "no messages", Locale.getDefault()),
				exception);

		throw new SalesException(messageSource, "error." + className + "."
				+ opName, new String[] {}, exception);
	}
}
