import java.time.LocalDate;

public class HealthRecordTest {
    public static void main(String[] args) {
        // Example usage
        HealthRecord record = new HealthRecord(LocalDate.now(), 170, 70);
        record.saveRecord("health_records.xlsx");
    }
}
