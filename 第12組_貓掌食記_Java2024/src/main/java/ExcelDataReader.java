import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel .Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

public class ExcelDataReader {
    double maxHeightGap = 0;
    double maxWeightGap = 0;
    String choose;
    int daysToSubtract;
    Date startDate, lastDate;
    public ExcelDataReader(String choose)
    {
        this.choose = choose;
    }
    
    public void readData(TimeSeries heightSeries, TimeSeries weightSeries) {
        try {
            FileInputStream file = new FileInputStream(new File("health_records.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is on the first sheet

            // Get the last date in the data
            int lastRow = sheet.getPhysicalNumberOfRows()-1;
            Row lastRowData = sheet.getRow(lastRow);
            Cell lastDateCell = lastRowData.getCell(0);
            if (lastDateCell != null) {
                if (lastDateCell.getCellType() == CellType.NUMERIC) {
                    lastDate = lastDateCell.getDateCellValue();
                } else if (lastDateCell.getCellType() == CellType.STRING) {
                    String dateString = lastDateCell.getStringCellValue();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    lastDate = dateFormat.parse(dateString);
                } else {
                    lastDate = null;
                }
            }

            // Calculate the start date for the chart based on the last date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lastDate);
            if(choose.equals("week"))
            {
                daysToSubtract = 6;
            }
            else if(choose.equals("month"))
            {
                daysToSubtract = 29;
            }
            calendar.add(Calendar.DAY_OF_YEAR, -daysToSubtract);
            startDate = calendar.getTime();
            
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { // Start from the second row
                Row row = sheet.getRow(i);
                
                Cell dateCell = row.getCell(0);
                Cell heightCell = row.getCell(1);
                Cell weightCell = row.getCell(2);
    
                if (dateCell != null && heightCell != null && weightCell != null) {
                    Date dateValue;
                    if (dateCell.getCellType() == CellType.NUMERIC) {
                        // Handle numeric date format
                        dateValue = dateCell.getDateCellValue();
                    } else if (dateCell.getCellType() == CellType.STRING) {
                        // Handle string date format (e.g., "yyyy-MM-dd")
                        String dateString = dateCell.getStringCellValue();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        dateValue = dateFormat.parse(dateString);
                    } else {
                        dateValue = null;
                    }
                    if (dateValue != null) {
                        Day day = new Day(dateValue);
                        heightSeries.addOrUpdate(day, heightCell.getNumericCellValue());
                        weightSeries.addOrUpdate(day, weightCell.getNumericCellValue());
                    }
                }

                if (i > 1) {
                    double heightGap = Math.abs(heightCell.getNumericCellValue() - heightSeries.getValue(i - 2).doubleValue());
                    double weightGap = Math.abs(weightCell.getNumericCellValue() - weightSeries.getValue(i - 2).doubleValue());
                    if (heightGap > maxHeightGap) {
                        maxHeightGap = heightGap;
                    }
                    if (weightGap > maxWeightGap) {
                        maxWeightGap = weightGap;
                    }
                }
            }
    
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getLastDate()
    {
        return lastDate;
    }

    public double getMaxWeightGap()
    {
        return maxWeightGap;
    }
}


