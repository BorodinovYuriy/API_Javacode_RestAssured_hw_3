package org.ex.data;


import com.github.javafaker.Faker;

public class DataProviders {
    static Faker faker = new Faker();
    public static String makeName() {
        return faker.name().name();
    }
    public static String makeSurname() {
        return faker.name().lastName();
    }
    public static String makeUsername() {
        return faker.name().username();
    }
    public static String makeEmail() {
        return faker.internet().emailAddress();
    }
    public static String makePassword() {
        return faker.internet().password();
    }
    public static String makeWords() {
        return faker.lorem().sentence(5);
    }

}