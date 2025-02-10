package org.ex.data;


import com.github.javafaker.Faker;
import org.example.dto.addfakeuser.AddFakeUserDTO;
import org.example.dto.addmodule.AddModuleDTO;
import org.example.dto.authuser.AuthRequestDTO;
import org.example.dto.questionadd.QuestionDTO;
import org.example.helpers.PropertiesLoader;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProviders {
    static Faker faker = new Faker();

    public static String makeUsername() {
        return faker.name().username();
    }
    public static String makePassword() {
        return faker.internet().password();
    }
    public static String makeName() {
        return faker.name().name();
    }
    public static String makeSurname() {
        return faker.name().lastName();
    }
    public static String makeEmail() {
        return faker.internet().emailAddress();
    }
    public static String makeQuestion() {
        return faker.lorem().sentence(5);
    }

    @DataProvider(name = "realUser")
    public static Object[][] realUser(){
        AuthRequestDTO user = new AuthRequestDTO();
        user.setUsername(PropertiesLoader.getUsername());
        user.setPassword(PropertiesLoader.getPassword());
        return new  Object[][] {{user}};
    }

    @DataProvider(name = "fakeUserAdd")
    public static Object[][] fakeUserAdd() {
        AddFakeUserDTO user = new AddFakeUserDTO();
        user.setFirst_name(makeName());
        user.setSurname(makeSurname());
        user.setUsername(makeUsername());
        user.setEmail(makeEmail());
        user.setPlain_password(makePassword());
        return new Object[][]{{user}};
    }

    @DataProvider(name = "questionAdd")
    public static Object[][] questionAdd() {
        QuestionDTO question = new QuestionDTO();
        question.setName(makeQuestion());
        return new Object[][]{{question}};
    }

    @DataProvider(name = "editData")
    public static Object[][] editData() {
        File jsonFile = new File("src/test/resources/jsons/editquestion.json");
        return new Object[][]{{jsonFile}};
    }

    @DataProvider
    public static Object[][] questionAndJson() {
        QuestionDTO question = new QuestionDTO();
        question.setName(makeQuestion());
        File jsonFile = new File("src/test/resources/jsons/editquestion.json");
        String checkName = "checkingNameModifier";
        return new Object[][]{
                {question, jsonFile,checkName}
        };
    }

    @DataProvider
    public static Object[][] addQuiz() {
        File jsonFile = new File("src/test/resources/jsons/addquiz.json");
        return new Object[][]{{jsonFile}};
    }

    @DataProvider
    public static Object[][] addModule() {
        AddModuleDTO module = new AddModuleDTO();
        module.setName("test");
        module.setQuestions(
                Stream.of("1000", "1001", "1002", "1005")
                .collect(Collectors.toList()));
        return new Object[][]{{module}};
    }

    @DataProvider
    public static Object[][] addCourse() {
        File jsonFile = new File("src/test/resources/jsons/addcourse.json");
        return new Object[][]{{jsonFile}};
    }

    @DataProvider
    public static Object[][] addExam() {
        File jsonFile = new File("src/test/resources/jsons/addexam.json");
        return new Object[][]{{jsonFile}};
    }

    @DataProvider
    public static Object[][] addTemplate() {
        File jsonFile = new File("src/test/resources/jsons/addtemplate.json");
        return new Object[][]{{jsonFile}};
    }

    @DataProvider
    public static Object[][] wrongCred() {
        Map<String,String> cred = new HashMap<>();
        cred.put(makeSurname(),makePassword());
        return new Object[][]{{cred}};
    }
}