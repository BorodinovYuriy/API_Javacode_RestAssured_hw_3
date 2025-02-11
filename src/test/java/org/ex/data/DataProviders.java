package org.ex.data;


import com.ex.dtos.newuser.CustomData;
import com.ex.dtos.newuser.NewUserDTO;
import com.github.javafaker.Faker;
import org.ex.helpers.ExcelDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class DataProviders {
    private static final Logger logger = LoggerFactory.getLogger(DataProviders.class);
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

    public static Stream<Object[]> excelPositive() {
        return getSheet("positive");
    }
    public static Stream<Object[]> excelNegative() {
        return getSheet("negative");
    }

    private static Stream<Object[]> getSheet(String sheetName){
        try {
            String filePath = "src/test/resources/xlsl/adduserdata.xlsx";
            List<Object[]> data = ExcelDataReader.readDataFromExcel(filePath, sheetName);

            return data.stream()
                    .map(row -> {
                        NewUserDTO userDTO = new NewUserDTO();
                        CustomData customData = new CustomData();

                        userDTO.setFirstName(convertToString(row[1]));
                        userDTO.setSurname(convertToString(row[2]));
                        userDTO.setEmail(convertToString(row[3]));
                        userDTO.setUsername(convertToString(row[4]));
                        userDTO.setPlainPassword(convertToString(row[5]));
                        userDTO.setRoles(convertToString(row[6]));

                        if (row[7] != null) {
                            customData.setIsCV(Boolean.parseBoolean(row[7].toString()));
                        } else {
                            customData.setIsCV(false);
                        }

                        if (row[8] != null) {
                            if (row[8] instanceof Date legacyDate) {
                                LocalDateTime localDateTime = legacyDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                String formattedDate = localDateTime.format(formatter);
                                customData.setSalesOpenTime(formattedDate);
                            } else {
                                customData.setSalesOpenTime(convertToString(row[8]));
                            }
                        } else {
                            customData.setSalesOpenTime(null);
                        }

                        customData.setSalesStatus(convertToString(row[9]));
                        userDTO.setCustomData(customData);
                        return new Object[]{userDTO};
                    });

        } catch (IOException e) {
            logger.error("Error reading data from Excel: " + e.getMessage());
            return Stream.empty();
        }
    }

    private static String convertToString(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}