package ro.facultate.aplicatieHR.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class ExcelGenerator {

    public static ByteArrayInputStream createExcel(List<? extends Object> lista,
                                                   boolean hasValidator) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Customers");


        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        addBorder(headerCellStyle);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        try {
            //obtinem clasa obiectului ce va constitui fiecare rand
            Class<?> clazz = lista.get(0).getClass();
            Row headerRow = sheet.createRow(0);
            //obtinem numarul coloanelor
            int columnsNo=hasValidator? clazz.getDeclaredFields().length-1: clazz.getDeclaredFields().length;

            for (int i = 0; i < columnsNo; i++){
                //alimentam header-ul tabelului cu numele membrilor obiectului din lista
                Field f = clazz.getDeclaredFields()[i];
                f.setAccessible(true);
                Cell headerCell= headerRow.createCell(i);
                String propertyName=f.getName();
                headerCell.setCellValue(propertyName);
                headerCell.setCellStyle(headerCellStyle);

                for (int j=0; j<lista.size(); j++){
                    //verificam daca randul a fost deja creat
                    Row contentRow;
                    if (sheet.getRow(j+1) != null){
                        contentRow = sheet.getRow(j+1);
                    }
                    else {
                        contentRow = sheet.createRow(j+1);
                    }
                    Class<?> contentLine = lista.get(j).getClass();
                    Field contentField = contentLine.getDeclaredField(propertyName);
                    contentField.setAccessible(true);
                    Cell contentCell = contentRow.createCell(i);
                    //obtinem valorile membrilor din fiecare obiect
                    String cellValue = contentField.get(lista.get(j)) == null? "": contentField.get(lista.get(j)).toString();

                    CellStyle cellStyle = workbook.createCellStyle();
                    //in functie de tipul de data setam formatul din fisierul Excel
                    if (contentField.get(lista.get(j))!=null && contentField.getType().isAssignableFrom(Integer.class)){
                        contentCell.setCellValue(Integer.parseInt(cellValue));
                        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
                    }
                    else if(contentField.get(lista.get(j))!=null && contentField.getType().isAssignableFrom(Double.class)){
                        contentCell.setCellValue(Double.parseDouble(cellValue));
                        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
                    }
                    else if(contentField.get(lista.get(j))!=null && contentField.getType().isAssignableFrom(Date.class)){
                        try {
                            contentCell.setCellValue(java.sql.Date.valueOf(LocalDate.parse(cellValue, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.s"))));
                        }
                        catch (DateTimeParseException e){
                            contentCell.setCellValue(java.sql.Date.valueOf(LocalDate.parse(cellValue, DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                        }

                        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
                    }
                    else{
                        contentCell.setCellValue(cellValue);
                    }
                    addBorder(cellStyle);

                    //daca obiectul din lista contine validator atunci ii obtinem valorea
                    // in functie de care setam culoarea fundalului rosu
                    if (hasValidator && contentLine.getDeclaredField("validate").getBoolean(lista.get(j)) == true){
                        setAsInvalid(cellStyle);
                    }
                    contentCell.setCellStyle(cellStyle);


                }
                sheet.autoSizeColumn(i);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }



        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void addBorder (CellStyle style){
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
    }

    private static void setAsInvalid (CellStyle style){
        style.setFillForegroundColor(IndexedColors.RED1.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }
}
