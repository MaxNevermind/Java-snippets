package org.maxkons.java_snippets.string;

import java.io.IOException;
import java.text.ParseException;

public class StringFunctions {

    public static void main(String[] args) throws IOException, ParseException {
        PrintLeadingZeros(123);
    }


    /*
        Format 123 -> 00123
     */
    public static void PrintLeadingZeros(int numberToPrint) {
        System.out.println(String.format("%05d", numberToPrint));
    }


}
