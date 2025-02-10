package org.ex.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

import java.io.IOException;

public class DocumentConverter {

    public static <T> T convertDocumentToDTO(Document userDocument, Class<T> dtoClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(userDocument.toJson(), dtoClass);
        } catch (IOException e) {
            System.err.println("Ошибка при преобразовании Document в " + dtoClass.getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
