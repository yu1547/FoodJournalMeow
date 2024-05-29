import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        int lastRowNum = sheet.getLastRowNum();
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
            } else {
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
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
                    // Recalculate BMI
                    existingRow.getCell(3).setCellValue(calculateBMI());   
                    // Save changes and exit
                    fos = new FileOutputStream(fileName);
                    workbook.write(fos);

                }
            }

            Double lastHeight = getLastRecordValue(sheet, 1);
            Double lastWeight = getLastRecordValue(sheet, 2);

            Scanner scanner = new Scanner(System.in);
            sheet = workbook.getSheetAt(0);

           
            System.out.println("Enter height (in cm) or press Enter to use the last recorded height (" + (lastHeight != null ? lastHeight : "N/A") + "): ");
            String heightInput = scanner.nextLine();
            double height = heightInput.isEmpty() && lastHeight != null ? lastHeight : Double.parseDouble(heightInput);
            
            System.out.println("Enter weight (in kg) or press Enter to use the last recorded weight (" + (lastWeight != null ? lastWeight : "N/A") + "): ");
            String weightInput = scanner.nextLine();
            double weight = weightInput.isEmpty() && lastWeight != null ? lastWeight : Double.parseDouble(weightInput);

            this.height = height;
            this.weight = weight;

            int rowNum = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(date.format(DateTimeFormatter.ISO_DATE));
            row.createCell(1).setCellValue(height);
            row.createCell(2).setCellValue(weight);
            row.createCell(3).setCellValue(calculateBMI());

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

    private static Row findRowByDate(Sheet sheet, LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row
            Cell dateCell = row.getCell(0);
            if (dateCell != null && dateCell.getCellType() == CellType.STRING) {
                LocalDate rowDate = LocalDate.parse(dateCell.getStringCellValue(), formatter);
                if (rowDate.equals(date)) {
                    return row;
                }
            }
        }
        return null;
    }
}


