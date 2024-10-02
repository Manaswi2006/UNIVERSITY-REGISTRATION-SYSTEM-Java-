import java.util.UUID;
import java.time.LocalDateTime;

public class Complaint {
    private final UUID complaintId;
    private final String description;
    private String status;
    private final LocalDateTime issueTime;


    public Complaint(String description) {
        this.complaintId = UUID.randomUUID();
        this.description = description;
        this.status = "Pending";
        this.issueTime = LocalDateTime.now();
    }

    public UUID get_complaintId() {
        return complaintId;
    }

    public String get_description() {
        return description;
    }

    public String get_status() {
        return status;
    }

    public void set_status(String status) {
        this.status = status;
    }

    public LocalDateTime get_issue_time() {
        return issueTime;
    }

    // Overriding toString() method to display complaint details //Object class method
    @Override
    public String toString() {
        return "Complaint ID: " + get_complaintId() +
                "\nDescription: " + get_description() +
                "\nStatus: " + get_status() +
                "\nIssue Time: " + get_issue_time();
    }
}
