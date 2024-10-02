import java.util.ArrayList;
import java.util.Comparator;

public class Complaint_Catalog extends Registration_system<Complaint> {
    private ArrayList<Complaint> complaints;

    public Complaint_Catalog() {
        this.complaints = new ArrayList<>();
    }

    public ArrayList<Complaint> get_complaints(){
        return this.complaints;
    }

    public void set_complaints(ArrayList<Complaint> complaints){
        this.complaints = complaints;
    }

    // View all complaints sorted by the issue time
    @Override
    public void view() {
        if (!complaints.isEmpty()){
            complaints.sort(Comparator.comparing(Complaint::get_issue_time));
            for (Complaint complaint : complaints) {
                System.out.println(complaint);
                System.out.println("==================================");
            }
        }
        else{
            System.out.println("No Complaints have been issued yet.");
        }

    }

    // Add a complaint to the catalog
    @Override
    public void add(Complaint complaint) {
        complaints.add(complaint);
        System.out.println("Complaint added: " + complaint.get_complaintId());
    }

    // Delete a complaint from the catalog
    @Override
    public void delete(Complaint complaint) {
        if (complaints.contains(complaint)) {
            complaints.remove(complaint);
            System.out.println("Complaint deleted: " + complaint.get_complaintId());
        } else {
            System.out.println("Complaint not found.");
        }
    }
}
