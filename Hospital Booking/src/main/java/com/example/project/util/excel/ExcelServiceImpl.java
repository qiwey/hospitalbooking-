package com.example.project.util.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public byte[] exportXlsx(List<?> dataList, Class<?> dataClass)
            throws IOException, IllegalAccessException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Field[] fields = dataClass.getDeclaredFields();
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            headerRow.createCell(i).setCellValue(field.getName());
        }

        for (int rowIndex = 0; rowIndex < dataList.size(); rowIndex++) {
            Row dataRow = sheet.createRow(rowIndex + 1);
            Object dataObject = dataList.get(rowIndex);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(dataObject);
                dataRow.createCell(i).setCellValue(value == null ? "" : value.toString());
            }
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream.toByteArray();
    }
}
