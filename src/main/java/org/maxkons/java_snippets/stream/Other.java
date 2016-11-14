package org.maxkons.java_snippets.stream;


import java.io.IOException;
import java.text.ParseException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Other {

    public static void main(String[] args) throws IOException, ParseException {
        joinStringStream();
    }

    // Transformation: [1, 2, 3, 4, 5] -> "1,2,3,4,5"
    public static void joinStringStream() {
        System.out.println(IntStream.range(1, 10).mapToObj(i->""+i).collect(Collectors.joining(",")));
    }

}
