package com.example.libtetsting;

import java.util.HashMap;

public class MyClass {
    public static void main(String[] args) {
        HashMap<String, Integer> numbers = new HashMap<>();
        numbers.put("a", 10);
        numbers.put("a", 20);
        numbers.put("b", 10);
        numbers.put("c", 30);
        numbers.put("d", 10);
        System.out.println(numbers.toString());
    }
}