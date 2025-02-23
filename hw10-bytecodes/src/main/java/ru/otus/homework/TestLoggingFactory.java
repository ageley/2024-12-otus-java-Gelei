package ru.otus.homework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestLoggingFactory {
    public static TestLoggingInterface createTestLoggingProxied(TestLoggingInterface testLogging) {
        return (TestLoggingInterface) Proxy.newProxyInstance(
                TestLoggingFactory.class.getClassLoader(),
                new Class<?>[] {TestLoggingInterface.class},
                new LogInvocationHandler(testLogging));
    }

    @Slf4j
    private static class LogInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLogging;
        private final Set<Method> annotatedMethods;

        @SneakyThrows(NoSuchMethodException.class) // Never trows NoSuchMethodException
        private LogInvocationHandler(TestLoggingInterface testLogging) {
            this.testLogging = testLogging;
            this.annotatedMethods = new HashSet<>();

            for (Method method : TestLoggingInterface.class.getMethods()) {
                if (this.testLogging
                        .getClass()
                        .getMethod(method.getName(), method.getParameterTypes())
                        .isAnnotationPresent(Log.class)) {
                    this.annotatedMethods.add(method);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (annotatedMethods.contains(method)) {
                log.info("executed method: {}, params: {}", method.getName(), Arrays.toString(args));
            }

            return method.invoke(testLogging, args);
        }
    }
}
