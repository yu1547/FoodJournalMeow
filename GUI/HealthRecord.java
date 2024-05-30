// Import required classes
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class HealthRecord {
    private LocalDate date;
    private double height; // in cm
    private double weight; // in kg

    public HealthRecord(LocalDate date, double height, double weight) {
        this.date = date;
        this.height = height;
        this.weight = weight;
    }

    public double calculateBMI() {
        double heightInMeters = height / 100;
        return Math.round((weight / (heightInMeters * heightInMeters)) * 100.0) / 100.0;
    }

    private static Double getLastRecordValue(Sheet sheet, int cellIndex) {
        int lastRowNum = sheet.getPhysicalNumberOfRows()-1;
        if (lastRowNum > 0) {
            Row lastRow = sheet.getRow(lastRowNum);
            if (lastRow != null) {
                Cell cell = lastRow.getCell(cellIndex);
                if (cell != null) {
                    return cell.getNumericCellValue();
                }
            }
        }
        return null;
    }

    private static Row findRowByDate(Sheet sheet, LocalDate date) {
         for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row
            if(row == null || row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) return null;
            Cell dateCell = row.getCell(0);
            if (dateCell != null && dateCell.getCellType() == CellType.STRING) {
                String dateString = dateCell.getStringCellValue();
                LocalDate rowDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
                if (rowDate.equals(date)) {
                    return row;
                }
            }
        }
        return null;
    }
    
    

    public void saveRecord(String fileName) {
        Workbook workbook = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        Sheet sheet = null;

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Health Records");
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Date");
                headerRow.createCell(1).setCellValue("Height (cm)");
                headerRow.createCell(2).setCellValue("Weight (kg)");
                headerRow.createCell(3).setCellValue("BMI");

                // Set date column format to date format
            } else {
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
            }

            Double lastHeight = getLastRecordValue(sheet, 1);
            Double lastWeight = getLastRecordValue(sheet, 2);

            if (height == 0.0 && lastHeight != null && weight != 0.0) {
                height = lastHeight;
            }
            else if (weight == 0.0 && lastWeight != null && height != 0.0) {
                weight = lastWeight;
            }
            

            sheet = workbook.getSheetAt(0);

            Row existingRow = findRowByDate(sheet, date);
            if (existingRow != null) {
                Cell heightCell = existingRow.getCell(1);
                Cell weightCell = existingRow.getCell(2);
                if (heightCell != null) {
                    heightCell.setCellValue(height);
                }
                if (weightCell != null) {
                    weightCell.setCellValue(weight);
                }
                existingRow.getCell(3).setCellValue(calculateBMI());
            } else {
                int rowNum = sheet.getPhysicalNumberOfRows();
                Row row = sheet.createRow(rowNum);
                System.out.println(rowNum);
                row.createCell(0).setCellValue(date.format(DateTimeFormatter.ISO_DATE));
                row.createCell(1).setCellValue(height);
                row.createCell(2).setCellValue(weight);
                row.createCell(3).setCellValue(calculateBMI());
            }

            fos = new FileOutputStream(fileName);
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
