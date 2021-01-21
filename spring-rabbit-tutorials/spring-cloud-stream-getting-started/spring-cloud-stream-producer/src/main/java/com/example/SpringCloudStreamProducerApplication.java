package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
public class SpringCloudStreamProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamProducerApplication.class, args);
    }

    @Bean
    public Supplier<Person> source1() {
        return () -> {
            Person person = new Person();
            person.setName("John Doe");
            System.out.println("Sending value: " + person);
            return person;
        };
    }

    public static class Person {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String toString() {
            return this.name;
        }
    }
}
