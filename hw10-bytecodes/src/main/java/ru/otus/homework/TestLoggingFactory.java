package ru.otus.homework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestLoggingFactory {
    public static TestLoggingInterface createTestLogging() {
        return new TestLogging();
    }

    public static TestLoggingInterface createTestLoggingProxied() {
        return (TestLoggingInterface) Proxy.newProxyInstance(
                TestLoggingFactory.class.getClassLoader(),
                new Class<?>[] {TestLoggingInterface.class},
                new LogInvocationHandler(new TestLogging()));
    }

    @Slf4j
    private static class TestLogging implements TestLoggingInterface {
        @Log
        @Override
        public void calculation(int param1) {
            log.info("calculation method with 1 param executed");
        }

        @Override
        public void calculation(int param1, int param2) {
            log.info("calculation method with 2 params executed");
        }

        @Log
        @Override
        public void calculation(int param1, int param2, String param3) {
            log.info("calculation method with 3 params executed");
        }
    }

    @Slf4j
    private record LogInvocationHandler(TestLoggingInterface testLogging) implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (testLogging
                            .getClass()
                            .getMethod(method.getName(), method.getParameterTypes())
                            .getAnnotation(Log.class)
                    != null) {
                log.info("executed method: {}, params: {}", method.getName(), Arrays.toString(args));
            }

            return method.invoke(testLogging, args);
        }
    }
}
