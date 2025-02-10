package org.ex.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONHelper {
    public static String fileToJSON(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
        return null;
    }

    public static String prepareJsonQuestion(String jsonString, int questionId, String checkName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonString);
        // Изменяем значение поля "question"
            if (rootNode instanceof ObjectNode) {
                ((ObjectNode) rootNode).put("question", questionId);
            }
        // Получаем узел "LTP"
            JsonNode ltpNode = rootNode.get("LTP");
            if (ltpNode instanceof ObjectNode) {
        // Получаем узел "data"
                JsonNode dataNode = ltpNode.get("data");
                if (dataNode instanceof ObjectNode) {
        // Изменяем значение поля "name"
                    ((ObjectNode) dataNode).put("name", checkName);
                }
            }

            return objectMapper.writeValueAsString(rootNode);

        } catch (IOException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        }
        return null;
    }
}
