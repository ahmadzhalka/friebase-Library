package com.example.friebase_library;

import java.util.ArrayList;

public class Worker {
    private String name;
    private String title;
    private int id;
    private int salary;
    public Worker() {

    }


    public Worker( String name, String title, int id, int salary) {

        this.name = name;
        this.title = title;
        this.id = id;
        this.salary = salary;
    }




    public int getId() {
        return id;
    }

    public Worker setId(int id) {
        this.id = id;
        return this;
    }

    public int getSalary() {
        return salary;
    }

    public Worker setSalary(int salary) {
        this.salary = salary;
        return this;
    }

    public String getName() {
        return name;
    }

    public Worker setName(String name) {
        this.name = name;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Worker setTitle(String title) {
        this.title = title;
        return this;
    }



}
