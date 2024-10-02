import java.util.ArrayList;

public class Course {
    private final String course_code;
    private String course_title;
    private int credits;
    private Professor professor;
    private String timings;
    private ArrayList<Course> prerequisites;
    private String syllabus;
    private boolean enrollment_limit_exists = false;
    private Integer enrollment_limit = null; //Wrapper Classes
    private int sem;
    private ArrayList<Student> current_students;
    private String location;
    private int number_of_enrolled_students = 0;
    private static Course_Catalog course_catalog;
    private static Complaint_Catalog complaintCatalog;
    private static Feedback_Manager feedbackmanager;
    private ArrayList<Teaching_Assistant> TAs= new ArrayList<>();
    private boolean Anonymous_Feedback = false;

    // Constructor with enrollment limit
    public Course(String _course_code, String _course_title, int _credits, String _professor, String _timings,
                  ArrayList<Course> _prerequisites, String _syllabus, int _sem, String _location, int _enrollment_limit,Feedback_Manager feedbackmanager) {
        this.course_code = _course_code;
        this.course_title = _course_title;
        this.credits = _credits;
        this.professor = find_professor_by_email(_professor);
        this.timings = _timings;
        this.prerequisites = _prerequisites;
        this.syllabus = _syllabus;
        this.sem = _sem;
        this.location = _location;
        this.current_students = new ArrayList<>();
        this.enrollment_limit = _enrollment_limit;
        this.enrollment_limit_exists = true;
        professor.add_current_courses(this);
        this.feedbackmanager = feedbackmanager;
        //this.course_catalog = course_catalog;
    }

    // Constructor without enrollment limit
    public Course(String _course_code, String _course_title, int _credits, String _professor, String _timings,
                  ArrayList<Course> _prerequisites, String _syllabus, int _sem, String _location,Feedback_Manager feedbackmanager) {
        this.course_code = _course_code;
        this.course_title = _course_title;
        this.credits = _credits;
        this.professor = find_professor_by_email(_professor);
        this.timings = _timings;
        this.prerequisites = _prerequisites;
        this.syllabus = _syllabus;
        this.sem = _sem;
        this.location = _location;
        this.current_students = new ArrayList<>();
        this.enrollment_limit_exists = false; // No enrollment limit by default
        professor.add_current_courses(this);
        this.feedbackmanager = feedbackmanager;
        //this.course_catalog = course_catalog;
    }

    public static Course_Catalog get_course_catalog() {
        return course_catalog;
    }
    // Getter for course code
    public String get_course_code() {
        return this.course_code;
    }
    // No setter for course_code as its unique and final

    // Getter and setter for course title
    public String get_course_title() {
        return this.course_title;
    }

    public void set_course_title(String new_title){
        this.course_title = new_title;
    }

    // Getter and Setter for credits (Only for admin)
    public int get_credits() {
        return this.credits;
    }

    public void set_credits(int new_credits) {
        this.credits = new_credits;
    }

    // Getter and Setter for professor (Only for admin)
    public Professor get_professor() {
        return this.professor;
    }

    public void set_professor(Professor new_professor) {
        this.professor = new_professor;
        professor.add_current_courses(this);
    }

    // Getter and Setter for timings
    public String get_timings() {
        return this.timings;
    }

    public void set_timings(String new_timings) {
        this.timings = new_timings;
    }

    // Getter and Setter for prerequisites
    public ArrayList<Course> get_prerequisites() {
        return this.prerequisites;
    }

    public void add_prerequisites(Course prereq) {
        if (!get_prerequisites().contains(prereq)) {
            this.prerequisites.add(prereq);
        } else {
            System.out.println("This course is already a part of the prerequisites.");
        }
    }

    public void drop_prerequisites(Course prereq) {
        if (get_prerequisites().contains(prereq)) {
            this.prerequisites.remove(prereq);
            System.out.println(prereq.get_course_title() + " has been removed from prerequisites.");
        } else {
            System.out.println("This course is not a prerequisite.");
        }
    }

    public void view_prerequisites() {
        if (prerequisites.isEmpty()) {
            System.out.println("There are no prerequisites for this course.");
        } else {
            System.out.println("Prerequisites for this course:");
            for (Course prereq : prerequisites) {
                prereq.print_course_details();
            }
        }
    }

    // Getter and Setter for syllabus
    public String get_syllabus() {
        return this.syllabus;
    }

    public void set_syllabus(String new_syllabus) {
        this.syllabus = new_syllabus;
    }

    //Getter and Setter for enrollment limit exists

    public boolean get_enrollment_limit_exists(){
        return this.enrollment_limit_exists;
    }

    public void set_enrollment_limit_exists(boolean exists){
        this.enrollment_limit_exists = exists;
    }

    // Getter and Setter for enrollment limit
    public int get_enrollment_limit() {
        if (get_enrollment_limit_exists()) {
            return this.enrollment_limit;
        } else {
            System.out.println("Enrollment limit not set.");
            return 0;  // Return 0 or any other default value if limit is not set
        }
    }

    public void set_enrollment_limit(int new_enrollment_limit) {
        this.enrollment_limit = new_enrollment_limit;
        set_enrollment_limit_exists(true);
    }

    public void drop_enrollment_limit() {
        this.enrollment_limit = null;  // Set to null when the limit is dropped
        set_enrollment_limit_exists(false);
    }

    // Getter and Setter for semester
    public int get_sem() {
        return this.sem;
    }

    public void set_sem(int new_sem){
        this.sem = new_sem;
    }

    // Getter and Setter for location
    public String get_location() {
        return this.location;
    }

    public void set_location(String new_location) {
        this.location = new_location;
    }

    // Getter for current students
    public ArrayList<Student> get_current_students() {
        return this.current_students;
    }

    // Method to add a student to the course
    public void add_current_student(Student student) {
        if (student != null && !get_current_students().contains(student)) {
            this.current_students.add(student);
            number_of_enrolled_students++;
        } else {
            System.out.println("Student is already enrolled or is null.");
        }
    }

    // Method to drop a student from the course
    public void drop_current_student(Student student) {
        if (student != null) {
            this.current_students.remove(student);
            number_of_enrolled_students--;
        } else {
            System.out.println("Student is null.");
        }
    }

    // Print course details
    public void print_course_details() {
        System.out.println(course_code + ": " + course_title + ", Professor: " + professor.get_email() +
                ", Credits: " + credits + ", Timings: " + timings + ", Semester: " + sem + ", Location: " + location);
        if (!prerequisites.isEmpty()) {
            System.out.print("Prerequisites: ");
            for (Course prereq : prerequisites) {
                System.out.print(prereq.get_course_code() + " "); // Use course_code for clarity
            }
            System.out.println();
        } else {
            System.out.println("Prerequisites: None");
        }
    }

    private static Professor find_professor_by_email(String email) {
        for (User user : User.userList) {
            if (user instanceof Professor) {
                Professor professor = (Professor) user; // Down-casting
                if (professor.get_email().equals(email)) {
                    return professor;
                }
            }
        }

        // If professor not found, create a new one
        System.out.println("Professor not found. Creating new professor...");
        Professor newProfessor = new Professor(email, course_catalog,complaintCatalog,feedbackmanager); // Assuming Professor constructor is similar to User
        newProfessor.sign_up("XYZ"); // Add the new professor to userList
        return newProfessor;
    }


    //Adding functionalities of TA
    public ArrayList<Teaching_Assistant> getTAs(){
        return this.TAs;
    }

    public void setTAs(ArrayList<Teaching_Assistant> TAs){
        this.TAs = TAs;
    }

    public void add_TAs(Teaching_Assistant TA){
        TAs.add(TA);
    }

    public void remove_TA(Teaching_Assistant TA){
        if (TAs.contains(TA)){
            TAs.remove(TA);
        }
        else{
            System.out.println("Already Not a TA");
        }
    }

    public void view_TAs(){
        if (!getTAs().isEmpty()) {
            for (Teaching_Assistant TAs : getTAs()) {
                System.out.println(TAs.get_name() + " : " + TAs.get_email());
            }
        }
        else{
            System.out.println("No TAs assigned yet");
        }
    }

    public boolean isAnonymous_Feedback() { return Anonymous_Feedback; }

    public void setAnonymous_Feedback(boolean anonymous_Feedback) { Anonymous_Feedback = anonymous_Feedback; }
}
