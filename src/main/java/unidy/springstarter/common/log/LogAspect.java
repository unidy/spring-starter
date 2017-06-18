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
	private static final String	PREFIX	= " ------------ ";
	private static final String	SUFFIX	= " ------------ ";

	@Around(value = "@within(unidy.springstarter.common.log.Loggable) || @annotation(unidy.springstarter.common.log.Loggable)")
	public Object around(ProceedingJoinPoint point) throws Throwable {

		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Loggable loggableMethod = method.getAnnotation(Loggable.class);
		Class<? extends Object> clazz = point.getTarget().getClass();
		Loggable loggableClass = (Loggable) clazz.getAnnotation(Loggable.class);
		LogLevel logLevel = loggableMethod != null ? loggableMethod.level() : loggableClass.level();

		preHandle(clazz, logLevel, method.getName());

		Object result = null;
		try {

			result = handle(point, loggableClass, method, loggableMethod, logLevel);

		} catch (Exception e) {
			printException(point.getTarget().getClass(), e);

			throw e;

		} finally {

			postHandle(clazz, logLevel, method.getName());

		}

		return result;
	}

	private void preHandle(Class<? extends Object> clazz, LogLevel logLevel, String message) {
		Logger.log(clazz, logLevel, PREFIX + message + "() starts execution" + SUFFIX);
	}

	private Object handle(ProceedingJoinPoint point, Loggable loggableClass, Method method, Loggable loggableMethod, LogLevel logLevel) throws Throwable {
		printParameters(loggableMethod, point, loggableClass, method, logLevel);

		long startTime = System.currentTimeMillis();

		Object result = point.proceed();

		long endTime = System.currentTimeMillis();

		printResult(result, loggableMethod, point, loggableClass, logLevel);

		printExecutionTime(point.getTarget().getClass(), logLevel, (endTime - startTime));

		return result;
	}

	private void printException(Class<? extends Object> clazz, Exception e) {
		Logger.log(clazz, LogLevel.ERROR, "[Exception]: " + e.toString());
	}

	private void postHandle(Class<? extends Object> clazz, LogLevel logLevel, String message) {
		Logger.log(clazz, logLevel, PREFIX + message + "() finished execution" + SUFFIX);
	}

	private void printParameters(Loggable loggableMethod, ProceedingJoinPoint point, Loggable loggableClass, Method method, LogLevel logLevel) {
		boolean showParams = loggableMethod != null ? loggableMethod.params() : loggableClass.params();

		if (point.getArgs() == null || point.getArgs().length == 0) {
			return;
		}

		StringBuilder parameters = new StringBuilder();
		for (int i = 0; i < point.getArgs().length; i++) {
			parameters.append(method.getParameterTypes()[i].getName() + ":" + point.getArgs()[i]);
			if (i < point.getArgs().length - 1)
				parameters.append(", ");
		}

		if (showParams && !"".equals(parameters)) {
			Logger.log(point.getTarget().getClass(), logLevel, "[Parameters] : " + parameters.toString());
		}
	}

	private void printResult(Object result, Loggable loggableMethod, ProceedingJoinPoint point, Loggable loggableClass, LogLevel logLevel) {
		if (result != null) {
			boolean showResults = loggableMethod != null ? loggableMethod.result() : loggableClass.result();
			if (showResults) {
				Logger.log(point.getTarget().getClass(), logLevel, "[Return] : " + result);
			}
		}
	}

	private void printExecutionTime(Class<? extends Object> clazz, LogLevel logLevel, long executionTime) {
		Logger.log(clazz, logLevel, "[Execution]: " + executionTime + " milliseconds.");
	}
}
