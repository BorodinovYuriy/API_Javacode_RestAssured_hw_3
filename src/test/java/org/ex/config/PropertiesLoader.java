package org.ex.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class PropertiesLoader {
    private static final String PROPERTIES_FILE = "application.properties";
    private static final Properties properties = new Properties();
    static {
        loadProperties();
    }
    private static void loadProperties() {
        try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                System.err.println("Не удалось найти файл пропертей: " + PROPERTIES_FILE);
                return;
            }
            properties.load(input);

        } catch (IOException ex) {
            System.err.println("Ошибка при загрузке конфигурации: " + PROPERTIES_FILE + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }
//--------------------------------------------
    public static String getBaseURI() {
        return properties.getProperty("baseURI");
    }
    public static String getPassword() {
        return properties.getProperty("password");
    }
    public static String getUsername() {
        return properties.getProperty("username");
    }
    public static String getMongoUri() {
        return properties.getProperty("mongoUri");
    }
    public static String getMongoDbName() {
        return properties.getProperty("mongoDbName");
    }
    public static String getMongoCollectionUsers() {
        return properties.getProperty("mongoCollectionUsers");
    }
    public static String getMongoCollectionCourseModules() {
        return properties.getProperty("mongoCollectionCourseModule");
    }
    public static String getMongoCollectionQuizzes() {
        return properties.getProperty("mongoCollectionQuizzes");
    }
    public static String getMongoCollectionCourses() {
        return properties.getProperty("mongoCollectionCourses");
    }
    public static String getMongoCollectionExams() {
        return properties.getProperty("mongoCollectionExams");
    }
    public static String getMongoCollectionTemplates() {
        return properties.getProperty("mongoCollectionTemplates");
    }
    public static int getRegisterUserId() {
        return Integer.parseInt(properties.getProperty("user_id"));
    }
}
