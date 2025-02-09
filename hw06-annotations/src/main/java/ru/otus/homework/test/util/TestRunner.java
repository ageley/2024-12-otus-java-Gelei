package ru.otus.homework.test.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestRunner {
    /**
     * @param testInstance a test class instance to be used for a method invocation.
     * @param method       a method to be invoked.
     * @return True if no exceptions were thrown, False otherwise.
     */
    private static boolean checkInvokeMethod(Object testInstance, Method method) {
        log.info("{} - STARTED", method.getName());

        try {
            method.invoke(testInstance);
        } catch (Exception e) {
            log.error("{} - FAILED", method.getName());
            log.error(e.getCause().getMessage(), e.getCause());
            return false;
        }

        log.info("{} - FINISHED", method.getName());
        return true;
    }

    /**
     * @param testInstance a test class instance to be used for a methods' invocation.
     * @param methods      methods to be invoked.
     * @return True if no exceptions were thrown, and False immediately after the first exception were thrown.
     */
    @SuppressWarnings("java:S3516")
    private static boolean checkInvokeBeforeMethods(Object testInstance, List<Method> methods) {
        for (Method method : methods) {
            if (!checkInvokeMethod(testInstance, method)) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param testInstance a test class instance to be used for a methods' invocation.
     * @param methods      methods to be invoked.
     * @return True if no exceptions were thrown,
     * and False after all methods were invoked if any method had thrown an exception.
     */
    private static boolean checkInvokeAfterMethods(Object testInstance, List<Method> methods) {
        boolean result = true;

        for (Method method : methods) {
            if (!checkInvokeMethod(testInstance, method)) {
                result = false;
            }
        }

        return result;
    }

    /**
     * If there were an exception before a test method invocation, the test method invocation is skipped.
     * All methods, that were supposed to be invoked after the test method invocation, will be invoked anyway.
     *
     * @param testInstance  a test class instance to be used for a methods' invocation.
     * @param method        a test method to be invoked.
     * @param beforeMethods methods to be invoked before a test method.
     * @param afterMethods  methods to be invoked after a test method.
     * @return True if no exceptions were thrown, and False if any method had thrown an exception.
     */
    private static boolean checkInvokeTestMethod(
            Object testInstance, Method method, List<Method> beforeMethods, List<Method> afterMethods) {
        log.info("{}: BEGIN", method.getName());
        boolean result =
                (!checkInvokeBeforeMethods(testInstance, beforeMethods) || !checkInvokeMethod(testInstance, method))
                        && checkInvokeAfterMethods(testInstance, afterMethods);
        log.info("{}: END", method.getName());
        return result;
    }

    /**
     * Parses methods from a class, annotated with {@link Test @Test}, {@link Before @Before} and {@link After @After},
     * and invokes them.
     * Calculates total/pass/fail tests count.
     *
     * @param testClassName a fully qualified class name with tests to be run.
     */
    public static void runTests(String testClassName)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
                    IllegalAccessException {
        Class<?> testClass = Class.forName(testClassName);
        Method[] methods = testClass.getDeclaredMethods();
        List<Method> testMethods = new ArrayList<>();
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();

        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Test) {
                    testMethods.add(method);
                } else if (annotation instanceof Before) {
                    beforeMethods.add(method);
                } else if (annotation instanceof After) {
                    afterMethods.add(method);
                }
            }
        }

        int total = testMethods.size();
        int pass = 0;
        int fail = 0;

        for (Method method : testMethods) {
            if (checkInvokeTestMethod(testClass.getConstructor().newInstance(), method, beforeMethods, afterMethods)) {
                pass++;
            } else {
                fail++;
            }
        }

        log.info("total: {}", total);
        log.info("pass: {}", pass);
        log.info("fail: {}", fail);
    }
}
