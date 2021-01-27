package com.example.fooservice;

public class Work {

    private String id;

    public Work(String id) {
        this.id = id;
    }

    public Work() {
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id='" + id + '\'' +
                '}';
    }
}
