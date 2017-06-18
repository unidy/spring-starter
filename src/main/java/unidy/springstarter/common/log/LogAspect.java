package unidy.springstarter.common.log;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

	@Around(value = "@within(unidy.springstarter.common.log.Loggable) || @annotation(unidy.springstarter.common.log.Loggable)")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = signature.getMethod();
		Loggable loggableMethod = method.getAnnotation(Loggable.class);

		Loggable loggableClass = proceedingJoinPoint.getTarget().getClass().getAnnotation(Loggable.class);

		// get current log level
		LogLevel logLevel = loggableMethod != null ? loggableMethod.level() : loggableClass.level();

		String prefix = " ------------ ";
		String suffix = " ------------ ";
		// before
		Logger.log(proceedingJoinPoint.getTarget().getClass(), logLevel, prefix + method.getName() + "() starts execution" + suffix);

		// show params
		boolean showParams = loggableMethod != null ? loggableMethod.params() : loggableClass.params();
		if (showParams) {
			if (proceedingJoinPoint.getArgs() != null && proceedingJoinPoint.getArgs().length > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < proceedingJoinPoint.getArgs().length; i++) {
					sb.append(method.getParameterTypes()[i].getName() + ":" + proceedingJoinPoint.getArgs()[i]);
					if (i < proceedingJoinPoint.getArgs().length - 1)
						sb.append(", ");
				}

				Logger.log(proceedingJoinPoint.getTarget().getClass(), logLevel, "[Parameters] : " + sb);
			}

		}

		long startTime = System.currentTimeMillis();
		// start method execution
		Object result = proceedingJoinPoint.proceed();

		long endTime = System.currentTimeMillis();

		// show results
		if (result != null) {
			boolean showResults = loggableMethod != null ? loggableMethod.result() : loggableClass.result();
			if (showResults) {
				Logger.log(proceedingJoinPoint.getTarget().getClass(), logLevel, "[Return] : " + result);
			}
		}

		Logger.log(proceedingJoinPoint.getTarget().getClass(), logLevel, "[Execution]: " + (endTime - startTime) + " milliseconds.");

		// show after
		Logger.log(proceedingJoinPoint.getTarget().getClass(), logLevel, prefix + method.getName() + "() finished execution" + suffix);

		return result;
	}
}
