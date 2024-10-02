import java.util.ArrayList;
import java.util.HashMap;
import java.util.SimpleTimeZone;
import java.util.UUID;

public class Student extends User implements User_action {
    private ArrayList<Course> current_courses = new ArrayList<>();
    private int current_semester = 1;
    private int current_credits = 0;
    private float CGPA;
    private String name;
    private boolean isAlreadyTA = false;
    private Feedback_Manager feedbackManager;
    private Teaching_Assistant TA= null;

    // Dictionary to store completed courses and their GPA
    private HashMap<Course, Float> completed_courses = new HashMap<>();


    // GETTERS AND SETTERS
    public Student(String email, Course_Catalog _course_catalog,Complaint_Catalog complaintCatalog,Feedback_Manager feedbackManager) {
        super(email, _course_catalog,complaintCatalog);
        this.feedbackManager = feedbackManager;
    }

    public String get_name() {
        return this.name;
    }

    public void set_name(String _name) {
        this.name = _name;
    }

    public ArrayList<Course> get_current_courses() {
        return current_courses;
    }

    protected void set_current_courses(ArrayList<Course> new_current_courses){
        this.current_courses = new_current_courses;
    }

    public void add_current_course(Course course) {
        if (!current_courses.contains(course)) {
            current_courses.add(course);
        }
    }

    public void remove_current_course(Course course) {
        current_courses.remove(course);
    }

    public HashMap<Course, Float> get_completed_courses() {
        return completed_courses;
    }

    public void add_completed_course(Course course, float gpa) {
        completed_courses.put(course, gpa);
    }

    public void remove_completed_course(Course course) {
        completed_courses.remove(course);
    }

    public void set_current_semester(int _current_semester) {
        this.current_semester = _current_semester;
    }

    public int get_current_semester() {
        return this.current_semester;
    }

    public void set_current_credits(int _credits) {
        this.current_credits = _credits;
    }

    public int get_credits() {
        return this.current_credits;
    }

    public void set_CGPA(float _CGPA) {
        this.CGPA = _CGPA;
    }

    public float get_CGPA() {
        return this.CGPA;
    }

    public boolean get_isAlreadyTA(){ return this.isAlreadyTA; }

    protected void setAlreadyTA(boolean bool){ this.isAlreadyTA = bool; }

    public Feedback_Manager getFeedbackManager(){ return this.feedbackManager; }

    public void setFeedbackManager(Feedback_Manager feedbackManager){ this.feedbackManager = feedbackManager; }

    public Teaching_Assistant getTA(){
        return this.TA;
    }

    public void setTA(Teaching_Assistant TA){
        this.TA = TA;
    }
    // FUNCTIONALITIES

    //1 . VIEW COURSES
    // A. VIEW CURRENT_COURSES
    // B. VIEW AVAILABLE COURSES

    public void view_current_courses() {
        if (get_current_courses().isEmpty()) {
            System.out.println("No current courses.");
        } else {
            for (Course course : get_current_courses()) {
                course.print_course_details();
            }
        }
    }

    public void view_available_courses() {
        if (course_catalog != null) {
            System.out.println("The courses available for the current semester are: ");
            for (Course course : course_catalog.get_courses()) {
                if (course.get_sem() == get_current_semester()) {
                    course.print_course_details();
                }
            }
        } else {
            System.out.println("Course catalog is not available.");
        }
    }

    //2. REGISTER FOR COURSES                          INPUT -> COURSE COURSE
    public void add_course(Course course) throws CourseFullException {
        // Check if the course's semester matches the student's current semester
        if (course.get_sem() != get_current_semester()) {
            System.out.println("Cannot add course. It is not offered in the current semester.");
            return;
        }

        // Check if adding the course would exceed the credit limit
        if (get_credits() + course.get_credits() > 20) {
            System.out.println("Cannot add course. Credit limit exceeded.");
            return;
        }

        // Check if all prerequisites are met
        boolean prerequisitesMet = true;
        for (Course prereq : course.get_prerequisites()) {
            if (!completed_courses.containsKey(prereq)) {
                prerequisitesMet = false;
                System.out.println("Cannot add course. Prerequisite not met: " + prereq.get_course_title());
                break;
            }
        }

        if (prerequisitesMet) {
            // Check if student is already enrolled
            if (!course.get_current_students().contains(this)) { // 'this' refers to the current student
                // Check if the course has an enrollment limit and if it's reached
                if (!course.get_enrollment_limit_exists() || course.get_current_students().size() < course.get_enrollment_limit()) {
                    course.add_current_student(this); // Add the student to the course
                    add_current_course(course); // Add the course to the student's course list
                    set_current_credits(get_credits() + course.get_credits()); // Update the student's total credits
                    System.out.println("Course added: " + course.get_course_title());
                } else {
                    throw new CourseFullException("Enrollment limit reached. Cannot add more students.");
                }
            } else {
                System.out.println("Already enrolled in this course.");
            }
        }
    }


    // 3. VIEW SCHEDULE
    public void view_schedule() {
        if (current_courses.isEmpty()) {
            System.out.println("No courses enrolled.");
        } else {
            for (Course course : current_courses) {
                System.out.println("Course: " + course.get_course_title() +
                        " | Timings: " + course.get_timings() +
                        " | Location: " + course.get_location() +
                        " | Professor: " + course.get_professor().get_email());
            }
        }
    }

    // 4. TRACK ACADEMIC PROGRESS

    // A. VIEW CGPA
    // B. VIEW SGPA FOR A SEMESTER                             -> INPUT: INT SEMESTER
    // C. VIEW GRADE FOR A SPECIFIC COURSE                     -> INPUT: COURSE COURSE
    // D. VIEW GRADE FOR ALL COURSES OF A SPECIFIC SEMESTER    -> INPUT: INT SEMESTER
    // E. VIEW GRADE FOR ALL COURSES

    public void view_CGPA() {
        System.out.println("Current CGPA is " + get_CGPA());
    }

    public void view_SGPA(int semester) {
        if (semester < get_current_semester()) {
            float semester_credits = 0;
            float sum_grades = 0;
            for (Course courses : get_completed_courses().keySet()) {
                if (courses.get_sem() == semester) {
                    semester_credits += courses.get_credits();
                    sum_grades += (courses.get_credits() * get_completed_courses().get(courses));
                }
            }
            float SGPA = sum_grades / semester_credits;
            System.out.println("The SGPA for semester " + semester + ": " + SGPA);
        } else {
            System.out.println("This semester has not been completed to view its SGPA");
        }
    }

    public void view_grade_for_course(Course course) {
        if (completed_courses.containsKey(course)) {
            System.out.println("Course: " + course.get_course_title() + " -> Grade: " + completed_courses.get(course));
        } else {
            System.out.println("Course not completed yet");
        }
    }

    public void view_semester_wise_grades(int semester) {
        if (semester < get_current_semester()) {
            for (Course courses : completed_courses.keySet()) {
                if (courses.get_sem() == semester) {
                    System.out.println("Course: " + courses.get_course_title() + " -> Grade: " + completed_courses.get(courses));
                }
            }
        } else {
            System.out.println("Semester not completed to view its grades");
        }
    }

    public void view_all_grades() {
        for (Course courses : completed_courses.keySet()) {
            System.out.println("Course: " + courses.get_course_title() + " -> Grade: " + completed_courses.get(courses));
        }
    }

    // 5. DROP COURSES                            -> INPUT: COURSE COURSE
    public void drop_course(Course course) {
        // Check if the student is currently enrolled in the course
        if (current_courses.contains(course)) {
            // Remove the course from the student's current courses
            remove_current_course(course);

            // Remove the student from the course's current student list
            course.drop_current_student(this);

            // Update the student's current credits
            set_current_credits(get_credits() - course.get_credits());

            System.out.println("Dropped the course: " + course.get_course_title());
        } else {
            System.out.println("Not enrolled in this course, so it cannot be dropped.");
        }
    }

    // 6. MANAGE COMPLAINTS
    // A. ISSUE A COMPLAINT                      : INPUT -> STRING DESCRIPTION
    // B. VIEW A COMPLAINT                       : INPUT -> COMPLAINT_ID

    public void issueComplaint(String description) {
        Complaint newComplaint = new Complaint(description);  // Create a new complaint
        complaintCatalog.add(newComplaint);  // Add the complaint to the catalog
    }

    public void viewComplaint(UUID complaintId) {
        for (Complaint complaint : complaintCatalog.get_complaints()) {
            if (complaint.get_complaintId().equals(complaintId)) {
                System.out.println("Complaint Found:\n" + complaint);
                return;
            }
        }
        System.out.println("Complaint not found for ID: " + complaintId);
    }

    //7. MANAGE FEEDBACKS
    //A. SUBMIT FEEDBACK FOR A COURSE

    public <T> void submitFeedback(Course course, T feedback){
        if (get_completed_courses().containsKey(course) || current_courses.contains(course)){
            feedbackManager.submitFeedback(course,feedback,this);
        }
        else{
            System.out.println("You dont have this course");
        }
    }

    //RESET PASSWORD AND SIGN IN

    // Set password: checks old password before allowing change
    @Override
    public void reset_password(String old_password, String new_password) {
        if (get_password().equals(old_password)) {
            this.password = new_password;
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("Incorrect old password.");
        }
    }

    // Sign-up: allows the user to set the password
    @Override
    public void sign_up(String password) {
        // Check if the user already exists
        for (User user : get_userList()) {
            if (user.get_email().equals(get_email())) {
                System.out.println("User already signed up!");
                return;
            }
        }
        set_password(password);
        userList.add(this);
        System.out.println("Sign-up successful!");
    }

    public void promote_to_TA(Course course) {
        if (!get_isAlreadyTA()) {
            setAlreadyTA(true);

            // Create a new TA object
            setTA(new Teaching_Assistant(this.get_email(), this.get_course_catalog(), this.get_complaint_catalog(), this.getFeedbackManager()));

            // Copy all data from Student to TA
            copy_student_data_to_TA(this.TA);

            // Add the TA to the course
            this.TA.setTA_courses(course);
            course.add_TAs(this.TA);

            System.out.println("Student " + this.get_name() + " has been promoted to TA for course: " + course.get_course_title());
        } else {
            System.out.println("The student is already a TA.");
        }
    }
    private void copy_student_data_to_TA(Teaching_Assistant ta) {
        // Copy all the fields from student to TA
        ta.set_name(this.get_name());
        ta.set_current_semester(this.get_current_semester());
        ta.set_current_courses(this.get_current_courses());
        ta.set_current_credits(this.get_credits());
        ta.set_CGPA(this.get_CGPA());

        // Copy completed courses and their GPA
        for (Course course : this.get_completed_courses().keySet()) {
            float grade = this.get_completed_courses().get(course);
            ta.add_completed_course(course, grade);
        }

        System.out.println("All student data copied to TA successfully.");
    }

    public void remove_TA(Object o, Course course) {
        // Check if the object is a Professor
        if (o instanceof Professor) {
            // Ensure the course matches the TA's course before removing
            if (course.equals(this.getTA().getTA_courses())) {
                this.setTA(null);  // Remove the TA
            } else {
                System.out.println("The student is not a TA of this course");
            }
        }
        // Check if the object is the student themselves
        else if (o.equals(this)) {
            this.setTA(null);
        }
        else {
            System.out.println("Only the professor teaching the course or the student themselves can remove TA-ship");
        }
    }


}
