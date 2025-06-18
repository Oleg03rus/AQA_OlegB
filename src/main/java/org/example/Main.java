package org.example;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println(isNumberEven(2));
        System.out.println(isNumberEven(3));

        System.out.println(checkNumberOfCharacters());

        int[] array = {2, 3, 4, 5};
        System.out.println(Arrays.toString(reverseArray(array)));

        System.out.println(checkString("Hello"));
        System.out.println(checkString("world"));

        System.out.println(checkEmail("oleg03@gmail.com"));
        System.out.println(checkEmail("oleg03gmail.com"));
        System.out.println(checkEmail("oleg@gmailcom"));
        System.out.println(checkEmail("asdsdffAQSS@yandex.ru"));
    }

    //Задача 1: Метод проверяющий является ли число четным
    public static Boolean isNumberEven(int number) {
       return (number % 2 == 0);
    }

    //Задача 2: Метод, проверяющий, что введённое имя не больше 20 символов.
    public static boolean checkNumberOfCharacters() {
        System.out.println("Введите имя для проверки количества символов: ");

        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        return (name.length() <= 20);
    }

    //Задача 3: Метод, который принимает массив и возвращает новый массив в обратном порядке.
    public static int[] reverseArray(int[] array) {
        int[] newReverseArray = new int[array.length];
        int index = array.length - 1;

        for (int i = 0; i < array.length; i++) {
            newReverseArray[index] = array[i];
            index--;
        }
        return newReverseArray;
    }

    //Задача 4: Метод, который проверяет, начинается ли строка с большой буквы.
    public static boolean checkString(String str) {
        return  (Character.isUpperCase(str.charAt(0)));
    }

    //Задача 5: Метод, который принимает email и проверяет, есть ли в нём @ и точка после него
    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("(\\w+[\\.-]?\\w+)+@(\\w+[\\.]{1}[a-z]{2,4})");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}