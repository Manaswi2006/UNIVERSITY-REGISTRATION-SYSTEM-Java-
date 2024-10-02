import java.util.ArrayList;

public class Professor extends User implements Course_Manager, User_action{
    private String office_hours; //Assuming they have a specific office hour common to all courses
    private ArrayList<Course> current_courses = new ArrayList<>(); //A list of all courses they are teaching
    private Course_Catalog course_catalog;
    private Feedback_Manager feedbackManager;

    public Professor(String email, Course_Catalog _course_catalog, Complaint_Catalog complaintCatalog, Feedback_Manager feedbackManager){
        super(email, _course_catalog,complaintCatalog);
        this.feedbackManager = feedbackManager;
        // In the constructor
        //System.out.println("Professor instance: " + System.identityHashCode(this));

        //System.out.println("y: "+feedbackManager);
//        System.out.println("Z: "+this.feedbackManager);
    }

    //GETTERS AND SETTERS
    public String get_office_hours(){
        return this.office_hours;
    }

    public void set_office_hours(String new_office_hours){
        this.office_hours = new_office_hours;
    }

    public ArrayList<Course> get_current_courses(){
        return this.current_courses;
    }

    public void add_current_courses(Course course){
        get_current_courses().add(course);
    }

    public void drop_current_courses(Course course){
        get_current_courses().remove(course);
    }

    public void setCurrent_courses(ArrayList<Course> current_courses){
        this.current_courses = current_courses;
    }
    //Getter of course catalog alr in user class that prof extends
    public Feedback_Manager getFeedbackManager(){
        return this.feedbackManager;
    }
    public void setFeedbackManager(Feedback_Manager feedbackManager){
        this.feedbackManager = feedbackManager;
    }
    // FUNCTIONALITIES

    // 1.VIEW YOUR COURSES
    public void view_courses(){
        if (!current_courses.isEmpty()){
            for (Course course: current_courses){
                course.print_course_details();
            }
        }
        else{
            System.out.println("Professor has no current courses.");
        }
    }

    //2. CHANGE A COURSE'S SYLLABUS                -> INPUT : COURSE COURSE , STRING NEW SYLLABUS
    @Override
    public void change_course_syllabus(Course course, String new_syllabus){
        if (get_current_courses().contains(course)){
            course.set_syllabus(new_syllabus);
        }
        else{
            System.out.println("You don't have this course.");
        }
    }

    //3. CHANGE A COURSE'S TIMINGS                 -> INPUT : COURSE COURSE, STRING NEW TIMINGS
    @Override
    public void change_course_timings(Course course, String new_timings){
        if (get_current_courses().contains(course)){
            course.set_timings(new_timings);
        }
        else{
            System.out.println("You don't have this course.");
        }
    }

    // 4. CHANGE A COURSE'S CREDITS                -> INPUT : COURSES COURSE , INT NEW CREDITS
    @Override
    public void change_course_credits(Course course, int new_credits){
        if (get_current_courses().contains(course)){
            course.set_credits(new_credits);
        }
        else{
            System.out.println("You don't have this course.");
        }
    }

    // 5 . CHANGE PRE-REQUISITES
    // A. ADD A PRE-REQUISITE                            -> INPUT : COURSE COURSE, COURSE NEW COURSE
    // B. DROP A PRE-REQUISITE                           -> INPUT : COURSE COURSE, COURSE OLD COURSE
    // C. VIEW PRE-REQUISITES                            -> INPUT : COURSE COURSE

    @Override
    public void add_prerequisites(Course course, Course new_course){
        if (current_courses.contains(course)){
            course.add_prerequisites(new_course);
        }
        else{
            System.out.println("You don't have this course.");
        }
    }

    @Override
    public void drop_prerequisites(Course course, Course old_prerequisite) {
        if (get_current_courses().contains(course)) {
            course.drop_prerequisites(old_prerequisite);
        } else {
            System.out.println("You are not assigned to this course.");
        }
    }

    @Override
    public void view_prerequisites(Course course){
        course.view_prerequisites();
        // Even if prof doesn't have the course they can view its pre-requisites
    }

    // 6. CHANGE ENROLLMENT LIMITS
    // A. ADD A NEW ENROLLMENT LIMIT             -> INPUT : COURSE COURSE , INT NEW ENROLLMENT LIMIT
    // B. DROP ENROLLMENT LIMITS                 -> INPUT : COURSE COURSE
    @Override
    public void change_enrollment_limits (Course course, int new_enrollment_limit){
        if (get_current_courses().contains(course)){
            course.set_enrollment_limit(new_enrollment_limit);
        }
        else{
            System.out.println("You don't have this course.");
        }
    }

    @Override
    public void drop_enrollment_limits ( Course course ){
        if (get_current_courses().contains(course)){
            course.drop_enrollment_limit();
        }
        else{
            System.out.println("You don't have this course.");
        }
    }

    //7. CHANGE OFFICE HOURS                               -> INPUT : STRING NEW TIMINGS
    public void change_office_hours(String _new_timings){
        set_office_hours(_new_timings);
    }

    // 8. VIEW ENROLLED STUDENTS OF A COURSE               -> INPUT : COURSE COURSE
    public void view_enrolled_students(Course _course) {
        if (get_current_courses().contains(_course)){
            ArrayList<Student> enrolledStudents = _course.get_current_students();

            if (enrolledStudents.isEmpty()) {
                System.out.println("No students enrolled in this course.");
            } else {
                System.out.println("Enrolled students in " + _course.get_course_title() + ":");
                for (Student student : enrolledStudents) {
                    System.out.println("Student Name: " + student.get_name()
                            + ", CGPA: " + student.get_CGPA()
                            + ", Email: " + student.get_email());
                }
            }
        }
        else{
            System.out.println("You don't have this Course.");
        }
    }

    //9. MANAGE TA'S
    // A. VIEW TA'S
    // B. ASSIGN TA
    // C. REMOVE TA

    public void view_TAs(Course course){
        if (this.get_current_courses().contains(course)){
            course.view_TAs();
        }
        else{
            System.out.println("You don't teach this course");
        }
    }
    public void assign_TA(Student student, Course course){
//        if (student != null) {
        if (this.get_current_courses().contains(course)) {
            if (student.get_completed_courses().containsKey(course)) {
                if (student.get_completed_courses().get(course) >= 8) {
                    student.promote_to_TA(course);
                } else {
                    System.out.println("Student has GPA of less than 8 in this course and therefore cannot take it");
                }
            } else {
                System.out.println("Student has not completed the course");
            }
        }
        else{
            System.out.println("You don't teach this course");
        }
//        }
//        else{
//            System.out.println("Student cant");
//        } null pt execption handled in main because find student by name is defined there
    }

    public void remove_TA(Student student, Course course){
        if (this.get_current_courses().contains(course)){
            if (student.getTA().getTA_courses() == course) {
                course.remove_TA(student.getTA());
                student.remove_TA(this,course);
                System.out.println("TA removed.");
            }
        }
        else{
            System.out.println("You don't teach this course");
        }
    }

    //10. MANAGE FEEDBACKS
    //A. CHANGE ANONYMITY/VISIBILITY OF A COURSE'S FEEDBACK
    //B. VIEW A COURSE'S FEEDBACK
    public void change_anonymity(Course course, boolean bool){
        if (get_current_courses().contains(course)){
            course.setAnonymous_Feedback(bool);
        }
        else{
            System.out.println("You don't teach this course");
        }
    }

    public void viewFeedback(Course course) {

// In the viewFeedback method
        //System.out.println("Professor instance in viewFeedback: " + System.identityHashCode(this));

        if (get_current_courses().contains(course)){
            //System.out.println(feedbackManager);
            feedbackManager.viewfeedback(course);
        }
        else{
            System.out.println("You don't teach this course");
        }
    }

    //RESET AND SIGN UP FUNCTIONS

    // Set password: checks old password before allowing change
    @Override
    public void reset_password(String old_password, String new_password) {
        if (get_password().equals(old_password)) {
            set_password(new_password);
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



    // FOR ADMIN TO USE : ADD OR DROP PROFESSOR'S CURRENT COURSES
    public void add_course(Course new_course){
        if (!get_current_courses().contains(new_course)){
            current_courses.add(new_course);
        }
        else{
            System.out.println("This course is already assigned to them.");
        }
    }

    public void drop_course(Course old_course){
        if (get_current_courses().contains(old_course)){
            current_courses.remove(old_course);
        }
        else{
            System.out.println("This course is not assigned to them.");
        }
    }

}
