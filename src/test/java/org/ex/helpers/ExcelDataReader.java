package org.ex.helpers;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExcelDataReader {
    public static List<Object[]> readDataFromExcel(String filePath, String sheetName) throws IOException {
        List<Object[]> data = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheet(sheetName);

        Iterator<Row> rowIterator = sheet.iterator();
        // Пропустите ряд заголовка, если присутствует
        if (rowIterator.hasNext()) {
            rowIterator.next(); // Пропустить ряд заголовка
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Object[] rowData = new Object[row.getLastCellNum()]; // Массив для хранения данных
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); // Use CREATE_NULL_AS_BLANK
                switch (cell.getCellType()) {
                    case STRING:
                        rowData[i] = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            rowData[i] = cell.getDateCellValue();
                        } else {
                            rowData[i] = cell.getNumericCellValue();
                        }
                        break;
                    case BOOLEAN:
                        rowData[i] = cell.getBooleanCellValue();
                        break;
                    case FORMULA:
                        rowData[i] = cell.getCellFormula(); // Или оценить формулу
                        break;
                    case BLANK:
                        rowData[i] = null; // Обрабатывать пустые ячейки
                        break;
                    default:
                        rowData[i] = null; // Обрабатывать другие типы ячеек по мере необходимости
                }
            }
            data.add(rowData);
        }

        workbook.close();
        fileInputStream.close();
        return data;
    }
}

