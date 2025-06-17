package org.example;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("------------------------------Задача 1-----------------------------------------------");
        checkEvenNumber(2);
        checkEvenNumber(3);

        checkNumberOfCharacters();

        int[] array = {2, 3, 4, 5};
        System.out.println(Arrays.toString(reverseArray(array)));
        System.out.println();
        System.out.println("------------------------------Задача 4------------------------------------------------");

        checkString("Hello");
        checkString("world");
        System.out.println();
        System.out.println("------------------------------Задача 5------------------------------------------------");

        checkEmail("oleg03@gmail.com");
        checkEmail("oleg03gmail.com");
        checkEmail("oleg@gmailcom");
        System.out.println();
        System.out.println("------------------------------Задача 6------------------------------------------------");

        //Задача 6: Создай класс Ticket с полем value и методом isLucky(), который возвращает всегда true. Вызови его в main() и выведи в консоль.
        Ticket ticket = new Ticket();
        System.out.println(ticket.isLucky());
        System.out.println();
        System.out.println("------------------------------Задача 7------------------------------------------------");

        System.out.println(luckyTicket("123321"));
        System.out.println(luckyTicket("111222"));
    }

    //Задача 1: Метод проверяющий является ли число четным
    public static void checkEvenNumber(int number) {
        if (number % 2 == 0) {
            System.out.println("Число четное");
        } else {
            System.out.println("Число нечетное");
            System.out.println();
            System.out.println("------------------------------Задача 2------------------------------------------------");
        }
    }

    //Задача 2: Метод, проверяющий, что введённое имя не больше 20 символов.
    public static void checkNumberOfCharacters() {
        System.out.println("Введите имя для проверки количества символов: ");

        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        if (name.length() <= 20) {
            System.out.println("Количество символов в имени не больше 20");
        } else {
            System.out.println("Количество символов в имени больше 20");
        }
        System.out.println();
        System.out.println("------------------------------Задача 3------------------------------------------------");
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
    public static void checkString(String str) {
        if (Character.isUpperCase(str.charAt(0))) {
            System.out.println("Строка начинается с большой буквы");
        } else {
            System.out.println("Строка начинается с маленькой буквы");
        }
    }

    //Задача 5: Метод, который принимает email и проверяет, есть ли в нём @ и точка после него
    public static void checkEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            System.out.println("Email содержит символы: \"@\" и \".\"");
        } else {
            System.out.println("Email не содержит символы: \"@\" или \".\"");
        }
    }

    //Задача 7: метод, который принимает строку из ровно 6 цифр (например, "385916") и возвращает true, если это счастливый билет.
    public static boolean luckyTicket(String ticket) {
        String firstPart = ticket.substring(0, 3);
        String secondPart = ticket.substring(3);

        char[] firstPartCharArray = firstPart.toCharArray();
        char[] secondPartCharArray = secondPart.toCharArray();

        int firstPartSum = 0;
        int seconfPartSum = 0;

        for (int i = 0; i < firstPartCharArray.length; i++) {
            firstPartSum += firstPartCharArray[i];
        }

        for (int i = 0; i < secondPartCharArray.length; i++) {
            seconfPartSum += secondPartCharArray[i];
        }

        if (firstPartSum == seconfPartSum) {
            return true;
        } else {
            return false;
        }
    }
}