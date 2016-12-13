package org.maxkons.java_snippets.test;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;

public class TestHelper {

    @SuppressWarnings("unchecked")
    @Test
    public void testInnerClass() {
        InnerClass systemUnderTest = new InnerClass();
        List<Integer> in = Arrays.asList(1, 2);
        List<Integer> out = (List<Integer>) callPrivateMethod(systemUnderTest, "addNumbersInternal", new Class[]{List.class, int.class}, new Object[]{in, 1});
        assertEquals(
                Arrays.asList(2, 3),
                out
        );
    }

    /**
     * Call a private method, this method can't work with primitive data type, because of boxing. 
     * Use Powermock Whitebox.invokeMethod(...) if you need more functionality.
     */
    public static Object callPrivateMethod(Object objectToUse, String methodToCall, Class[] argsTypes, Object[] args) {
        try {
            Method method = objectToUse.getClass().getDeclaredMethod(methodToCall, argsTypes);
            method.setAccessible(true);
            return method.invoke(objectToUse, args);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}

class InnerClass {

    private List<Integer> addNumbersInternal(List<Integer> list, int b) {
        return list.stream().map(a -> a + b + 1).collect(Collectors.toList()); // Wrong logic that should be discovered in a test
    }

}
