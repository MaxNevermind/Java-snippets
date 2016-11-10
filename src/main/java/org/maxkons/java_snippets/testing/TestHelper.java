package org.maxkons.java_snippets.testing;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

public class TestHelper {

    public static void main(String[] args) {
        callPrivateMethod(new InnerClass(), "privateMethod");
    }

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

    /**
     * Make sure passed files are presented in specified directory but no other.
     * @param parentDir directory to check.
     * @param files files path relatively to parentDir.
     */
    public static void ensureFileStructure(String parentDir, String[] files) throws IOException {
        for (int i = 0; i < files.length; i++) {
            files[i] = Paths.get(files[i]).normalize().toString();
            assertTrue("File " + files[i] + " should exist.", new File(files[i]).exists());
        }
        Files
                .walk(Paths.get(parentDir))
                .filter(currPath -> {
                    for (int i = 0; i < files.length; i++)
                        if (files[i].contains(currPath.toString()))
                            return false;
                    return true;
                })
                .forEach(path -> fail("File " + path.toString() + " should not exist."));
    }

}


class InnerClass {

    private void privateMethod() {
        System.out.println("privateMethod call");
    }

}