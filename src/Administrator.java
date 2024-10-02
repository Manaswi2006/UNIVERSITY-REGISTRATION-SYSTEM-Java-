import java.util.*;

public class Administrator extends User implements Course_Manager, Grade_Manager {
    Scanner scanner = new Scanner(System.in);
    public Administrator(String email, Course_Catalog course_catalog, Complaint_Catalog complaintCatalog) {
        super(email, course_catalog,complaintCatalog);
        this.password = "XYZ"; // Default password;
    }

    //COURSE CATALOG MANAGER
    public void view_catalog() {
        get_course_catalog().view();
    }

    public void add_course(Course course) {
        if (course != null) {
            get_course_catalog().add(course);
        } else {
            System.out.println("Invalid course.");
        }
    }

    public void delete_course(Course course) {
        if (course != null) {
            get_course_catalog().delete(course);
        } else {
            System.out.println("Invalid course.");
        }
    }

    // COURSE MANAGER METHODS
    @Override
    public void change_course_syllabus(Course course, String new_syllabus) {
        course.set_syllabus(new_syllabus);
    }

    @Override
    public void change_course_timings(Course course, String new_timings) {
        course.set_timings(new_timings);
    }

    @Override
    public void change_course_credits(Course course, int new_credits) {
        course.set_credits(new_credits);
    }

    @Override
    public void add_prerequisites(Course course, Course new_course) {
        course.add_prerequisites(new_course);
    }

    @Override
    public void drop_prerequisites(Course course, Course old_prerequisite) {
        course.drop_prerequisites(old_prerequisite);
    }

    @Override
    public void view_prerequisites(Course course) {
        course.view_prerequisites();
    }

    @Override
    public void change_enrollment_limits(Course course, int new_enrollment_limit) {
        course.set_enrollment_limit(new_enrollment_limit);
    }

    @Override
    public void drop_enrollment_limits(Course course) {
        course.drop_enrollment_limit();
    }

    //MANAGE STUDENT RECORDS
    public void view_current_courses(Student student){
        student.view_current_courses();
    }

    public void add_courses(Student student, Course course) throws CourseFullException{
        student.add_course(course);
    }

    public void drop_course(Student student, Course course){
        student.drop_course(course);
    }

    public void view_CGPA(Student student){
        student.view_CGPA();
    }

    public void view_SGPA(Student student , int semester){
        student.view_SGPA(semester);
    }

    public void view_grade_for_course(Student student , Course course) {
        student.view_grade_for_course(course);
    }

    public void view_grade_for_sem(Student student , int semester ){
        student.view_semester_wise_grades(semester);
    }

    public void view_all_grades(Student student){
        student.view_all_grades();
    }

    public int view_credits(Student student){
        return student.get_credits();
    }
    //No update method for credits -> Assuming cannot be changed without add or drop of a course or change of credits of a course

    public int view_semester(Student student){
        return student.get_current_semester();
    }

    public String view_name(Student student){
        return student.get_name();
    }

    public void change_name(Student student, String new_name){
        student.set_name(new_name);
    }

    public void calculate_CGPA(Student student) {
        float total_grade_points = 0;
        int total_credits = 0;

        for (Map.Entry<Course, Float> entry : student.get_completed_courses().entrySet()) {
            Course course = entry.getKey();
            float grade = entry.getValue();
            total_grade_points += grade * course.get_credits();
            total_credits += course.get_credits();
        }

        // Set CGPA based on total grade points and total credits
        if (total_credits > 0) {
            float CGPA = total_grade_points / total_credits;
            student.set_CGPA(CGPA);
            System.out.println("Updated CGPA: " + CGPA);
        } else {
            System.out.println("No courses completed to calculate CGPA.");
        }
    }
    /*
    SEMESTER COMPLETION LOGIC:

    - The entire process of managing the CGPA, SGPA, and grade assignments for courses is handled within a function that executes
      once a semester is completed. The function updates the student's academic progress and resets certain attributes in preparation
      for the new semester.

    ASSUMPTIONS:
    1. If a student fails any course, they are required to repeat the entire semester.
       - This simplifies the calculation and management of CGPA and SGPA by avoiding the complications of handling backlogs.
    2. Failure is defined by a grade below a certain threshold (e.g., less than or equal to 4.0).

 */
    @Override
    public void view_grade(Student student, Course course) {
        if (student.get_completed_courses().containsKey(course)) {
            // Get the grade from the completed courses
            float grade = student.get_completed_courses().get(course);
            System.out.printf("Grade for course is: %.2f%n", grade); // Print with two decimal places
        } else {
            System.out.println("Course hasn't been completed yet");
        }
    }

    @Override
    public void assign_grade(Student student, Course course, float grade){
        student.add_completed_course(course, grade);
    }

    // If any course is failed, remove all courses from completed courses for this semester
    @Override
    public void fail_student(Student student){
        System.out.println("Student has failed the semester. Removing all courses for this semester from completed courses.");
        int current_sem = student.get_current_semester();
        for (Course courses : student.get_completed_courses().keySet()){
            if (courses.get_sem() == current_sem){
                student.remove_completed_course(courses);
            }
        }
    }

    public void semester_completion(Student student) {
        boolean fail = false;
        // Iterate over current courses and assign GPA
        for (Course course : student.get_current_courses()) {
            System.out.println("Assign GPA for course " + course.get_course_title());
            float grade = scanner.nextFloat();
             // Add course to completed courses
            assign_grade(student, course, grade);
            // If course is failed
            if (grade <= 4.0) { // Assuming grade below or equal to 4.0 is a fail
                fail = true;
            }
        }

        if (fail) {
            fail_student(student);
        } else {
            // No failures, proceed to calculate CGPA and promote to next semester
            calculate_CGPA(student);
            student.set_current_semester(student.get_current_semester() + 1); // Move to next semester
        }

        // Now drop all current courses (this happens regardless of pass or fail)
        List<Course> coursesToRemove = new ArrayList<>(student.get_current_courses());
        for (Course course : coursesToRemove) {
            student.drop_course(course); // Remove course from current courses
            System.out.println("Dropped the course: " + course.get_course_title());
        }
    }


    //CHANGE COURSE PROFESSORS
    private boolean professor_is_available(Professor professor, Course new_course) {
        TimeParser parser = new TimeParser();

        // Get start and end time of the new course
        String[] new_course_timings = parser.get_timings(new_course.get_timings());
        String new_course_start = new_course_timings[0];
        String new_course_end = new_course_timings[1];

        // Check conflicts with professor's current courses
        for (Course current_course : professor.get_current_courses()) {
            String[] current_course_timings = parser.get_timings(current_course.get_timings());
            String current_course_start = current_course_timings[0];
            String current_course_end = current_course_timings[1];

            // If there is an overlap in timings, professor is not available
            if (time_conflict(new_course_start, new_course_end, current_course_start, current_course_end)) {
                System.out.println("Professor is not available during " + new_course.get_timings() + " due to course: " + current_course.get_course_title());
                return false;
            }
        }

        // Check if office hours are set before checking for conflicts
        if (professor.get_office_hours() != null) {
            String[] office_hours_timings = parser.get_timings(professor.get_office_hours());
            String office_start = office_hours_timings[0];
            String office_end = office_hours_timings[1];

            if (time_conflict(new_course_start, new_course_end, office_start, office_end)) {
                System.out.println("Professor is not available during " + new_course.get_timings() + " due to office hours: " + professor.get_office_hours());
                return false;
            }
        } else {
            System.out.println("Office hours not set for professor.");
        }

        return true; // Professor is available
    }


    // Function to check if two time intervals conflict
    private boolean time_conflict(String start1, String end1, String start2, String end2) {
        return start1.compareTo(end2) < 0 && start2.compareTo(end1) < 0;
    }

    public void assign_professor(Professor professor, Course course){
        if (professor_is_available(professor,course)){
            course.get_professor().drop_current_courses(course); //remove course from old profs current courses
            course.set_professor(professor);
            professor.add_current_courses(course);// add course to new profs current_courses
            System.out.println("Professor "+ professor.get_email() + "has been assigned course: "+ course.get_course_title());
        }
    }


    // MANAGE COMPLAINTS
    // A. VIEW ALL COMPLAINTS
    // B. CHANGE A COMPLAINT'S STATUS               : INPUT -> STRING COMPLAINT ID, STRING NEW STATUS

    public void viewAllComplaints() {
        if (complaintCatalog.get_complaints().isEmpty()) {
            System.out.println("No complaints found.");
            return;
        }

        System.out.println("All Complaints:");
        complaintCatalog.view();
    }

    public void changeComplaintStatus(UUID complaintId, String newStatus) {
        for (Complaint complaint : complaintCatalog.get_complaints()) {
            if (complaint.get_complaintId().equals(complaintId)) {
                complaint.set_status(newStatus);  // Pending / Resolved
                System.out.println("Complaint ID " + complaintId + " status updated to: " + newStatus);
                return;
            }
        }
        System.out.println("Complaint not found for ID: " + complaintId);
    }

    public void sign_up() {
        // Check if the user already exists
        for (User user : get_userList()) {
            if (user.get_email().equals(get_email())) {
                System.out.println("User already signed up!");
                return;
            }
        }
        userList.add(this);
        System.out.println("Sign-up successful!");
    }
}
