package org.example;

import lombok.Data;

import java.util.List;

@Data
public class Employee {
    String name;
    List<String> skills;

    public Employee(String name, List<String> skills) {
        this.name = name;
        this.skills = skills;
    }
}
