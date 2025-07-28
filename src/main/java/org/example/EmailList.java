package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EmailList {
    public static List<String> emailList = Arrays.asList(
            "user1@gmail.com",
            "USER2@GMAIL.com",
            "admin@yahoo.com",
            "test@mail.ru",
            "oleg123@adREs.ru",
            "test123@Zadres.com"
    );

    public static List<String> sortedEmailList(List<String> emails) {
        return emails.stream()
                .map(x-> x.substring(x.indexOf("@") + 1).toLowerCase())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
