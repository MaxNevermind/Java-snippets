package org.maxkons.java_snippets.testing;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

public class TestHelper {

    @Test
    public void testInnerClass() {
        InnerClass systemUnderTest = new InnerClass();
        assertEquals(4, callPrivateMethod(systemUnderTest, "addNumbersInternal", 2, 2));
    }

    /**
     * Call a private method, this method can't work with primitive data type, because of boxing.
     */
    public static Object callPrivateMethod(Object objectToUse, String methodToCall, Object... methodArgs) {
        @SuppressWarnings("RedundantCast")
        Class[] argClasses = (Class[]) Arrays.stream(methodArgs).map(Object::getClass).toArray(Class[]::new);
        try {
            Method method = objectToUse.getClass().getDeclaredMethod(methodToCall, argClasses);
            method.setAccessible(true);
            return method.invoke(objectToUse, methodArgs);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}

class InnerClass {

    public Integer addNumbers(Integer a, Integer b) {
        return addNumbersInternal(a, b);
    }

    private Integer addNumbersInternal(Integer a, Integer b) {
        return a + b + 1; // Wrong logic testing should discover
    }

}