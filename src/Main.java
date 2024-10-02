import java.util.*; //we will use ArrayList and Scanner
import java.text.SimpleDateFormat;

public class Main {
    private static Date dropDeadline;
    static Course_Catalog course_catalog = new Course_Catalog(); //course_catalog = new ArrayList<Course>();
    static Complaint_Catalog complaintCatalog = new Complaint_Catalog();
    static Feedback_Manager feedbackManager = new Feedback_Manager();
    public static void main(String[] args) throws InvalidLoginException, CourseFullException {
        Scanner scanner = new Scanner(System.in);

        Professor profPravesh = new Professor("Prof. Pravesh Bihyani", course_catalog,complaintCatalog,feedbackManager);
        profPravesh.set_office_hours("1:00 PM to 2:00 PM");
        profPravesh.sign_up("XYZ");

        Professor profSamresh = new Professor("Prof. Samresh Chaterjee", course_catalog,complaintCatalog,feedbackManager);
        profSamresh.set_office_hours("2:00 PM to 3:00 PM");
        profSamresh.sign_up("XYZ");

        Professor profDebarka = new Professor("Prof. Debarka Sengupta", course_catalog,complaintCatalog,feedbackManager);
        profDebarka.set_office_hours("11:00 AM to 12:00 PM");
        profDebarka.sign_up("XYZ");

        Professor profKirti = new Professor("Prof. Kirti Kanjilal", course_catalog,complaintCatalog,feedbackManager);
        profKirti.set_office_hours("2:00 PM to 3:00 PM");
        profKirti.sign_up("XYZ");

        Professor profDeepak = new Professor("Prof. Deepak Prince", course_catalog,complaintCatalog,feedbackManager);
        profDeepak.set_office_hours("10:00 AM to 11:00 AM");
        profDeepak.sign_up("XYZ");

        add_data_for_courses(course_catalog);

        Student student1 = new Student("student1@example.com", course_catalog,complaintCatalog,feedbackManager);
        student1.set_name("Manaswi Singh");
        student1.set_current_semester(1);
        student1.set_current_credits(8);
        student1.add_current_course(findCourseByCode("CSE101"));
        student1.add_current_course(findCourseByCode("MTH101"));
        student1.sign_up("XYZ");

        Student student2 = new Student("student2@example.com", course_catalog,complaintCatalog,feedbackManager);
        student2.set_name("Random name 1");
        student2.set_current_semester(2);
        student2.set_current_credits(8);
        student2.set_CGPA(8.8f);
        student2.add_current_course(findCourseByCode("CSE201"));
        student2.add_current_course(findCourseByCode("ECE201"));
        student2.add_completed_course(findCourseByCode("CSE101"), 8.9f);
        student2.add_completed_course(findCourseByCode("ECE101"), 8.6f);
        student2.sign_up("XYZ");

        Student student3 = new Student("student3@example.com", course_catalog,complaintCatalog,feedbackManager);
        student3.set_name("Random name 2");
        student3.set_current_semester(3);
        student3.add_completed_course(findCourseByCode("SSH101"),9.4f);
        student3.add_completed_course(findCourseByCode("DES101"),8.6f);
        student3.add_completed_course(findCourseByCode("CSE201"),9.6f);
        student3.add_completed_course(findCourseByCode("ECE201"),7.6f);
        student3.set_current_credits(4);
        student3.add_current_course(findCourseByCode("SSH301"));
        student3.add_current_course(findCourseByCode("ECE301"));
        student3.sign_up("XYZ");

        Administrator admin1 = new Administrator("admin1@example.com", course_catalog,complaintCatalog);
        Administrator admin2 = new Administrator("admin2@example.com", course_catalog,complaintCatalog);

        admin2.sign_up();
        admin1.sign_up();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
            dropDeadline = sdf.parse("02 OCT 2024, 04:00 PM");
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("Welcome to the University Course Registration System");
            System.out.println("1. LOGIN ");
            System.out.println("2. SIGN - IN ");
            String choice_internal = scanner.nextLine();
            switch (choice_internal) {
                case "2":
                    System.out.println("1. Sign in as Student");
                    System.out.println("2. Sign in as Professor");
                    System.out.println("3. Sign in as Administrator");
                    String _choice = scanner.nextLine();
                    System.out.println("Enter email: ");
                    String _email = scanner.nextLine();
                    switch (_choice) {
                        case "1": // Student Sign-up
                            handle_student_sign_up(scanner, _email);
                            break;

                        case "2": // Professor Sign-up
                            handle_professor_sign_up(scanner, _email);
                            break;

                        case "3": // Administrator Sign-up
                            handle_administrator_sign_up(scanner, _email);
                            break;

                        case "4": //To go back to Sign in or Login in
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                case "1":

                    System.out.println("1. Login as Student");
                    System.out.println("2. Login as Professor");
                    System.out.println("3. Login as Administrator");
                    System.out.println("4. Go back");
                    String choice = scanner.nextLine();
                    System.out.println("Enter email: ");
                    String email = scanner.nextLine();

                    switch (choice) {
                        case "1": // Student Login
                            handle_student_session(scanner, email);
                            break;

                        case "2": // Professor Login
                            handle_professor_session(scanner, email);
                            break;

                        case "3": // Administrator Login
                            handle_administrator_session(scanner, email);
                            break;

                        case "4": //To go back to Sign in or Login in
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1 or 2");
            }
        }
    }

    //Function that by defaults sets up a course catalog
    private static void add_data_for_courses(Course_Catalog _course_catalog) {
        // Semester 1
        _course_catalog.add(new Course("CSE101", "Introduction to Programming", 4, "Prof. Binod", "10:00 AM to 11:00 AM", new ArrayList<>(), "Syllabus Default", 1, "Room 101",feedbackManager));
        _course_catalog.add(new Course("ECE101", "Digital Circuits", 4, "Prof. Pravesh Bihyani", "9:00 AM to 10:00 AM", new ArrayList<>(), "Syllabus Default", 1, "Room 102",feedbackManager));
        _course_catalog.add(new Course("MTH101", "Maths I - Linear Algebra", 4, "Prof. Samresh Chaterjee", "2:00 PM to 3:00 PM", new ArrayList<>(), "Syllabus Default", 1, "Room 103",feedbackManager));
        _course_catalog.add(new Course("SSH101", "Communication Skills", 2, "Prof. Payal", "10:00 AM to 1:00 PM", new ArrayList<>(), "Syllabus Default", 1, "Room 104",feedbackManager));
        _course_catalog.add(new Course("DES101", "HCI", 2, "Prof. Rajiv Shah", "1:00 PM to 2:00 PM", new ArrayList<>(), "Syllabus Default", 1, "Room 105",feedbackManager));

        // Semester 2
        _course_catalog.add(new Course("CSE201", "Data Structures and Algorithms", 4, "Prof. Debarka Sengupta", "11:00 AM to 12:00 PM", new ArrayList<>(Arrays.asList(findCourseByCode("CSE101"))), "Syllabus Default", 2, "Room 201",feedbackManager));
        _course_catalog.add(new Course("ECE201", "Basic Electronics", 4, "Prof. Sayan Basu Roy", "1:00 PM to 2:00 PM", new ArrayList<>(), "Syllabus Default", 2, "Room 202",feedbackManager));
        _course_catalog.add(new Course("MTH201", "Maths II - Probability & Statistics", 4, "Prof. Sanjit Kaul", "3:00 PM to 4:00 PM", new ArrayList<>(), "Syllabus Default", 2, "Room 203",feedbackManager));
        _course_catalog.add(new Course("SSH201", "Money and Banking", 2, "Prof. Kirti Kanjilal", "2:00 PM to 3:00 PM", new ArrayList<>(), "Syllabus Default", 2, "Room 204",feedbackManager));
        _course_catalog.add(new Course("CSE202", "Computer Organisation", 2, "Prof. Tammam Tilo", "10:00 AM to 11:00 AM", new ArrayList<>(), "Syllabus Default", 2, "Room 205",feedbackManager));
        _course_catalog.add(new Course("SSH202", "Anthropology", 2, "Prof. Deepak Prince", "10:00 AM to 11:00 AM", new ArrayList<>(), "Syllabus Default", 2, "Room 206",feedbackManager));

        // Semester 3
        _course_catalog.add(new Course("CSE301", "Advanced Programming", 4, "Prof. Miller", "1:00 PM to 2:00 PM", new ArrayList<>(Arrays.asList(findCourseByCode("CSE101"))), "Syllabus Default", 3, "Room 301",feedbackManager));
        _course_catalog.add(new Course("CSE302", "Operating Systems", 4, "Prof. Vivek Bohra", "10:00 AM to 11:00 AM", new ArrayList<>(Arrays.asList(findCourseByCode("CSE202"))), "Syllabus Default", 3, "Room 302",feedbackManager));
        _course_catalog.add(new Course("MATH301", "Discrete Mathematics", 4, "Prof. Bapi Chhaterjee", "11:00 AM to 12:00 PM", new ArrayList<>(), "Syllabus Default", 3, "Room 303",feedbackManager));
        _course_catalog.add(new Course("SSH301", "Political Anthropology", 2, "Prof. Deepak Prince", "12:00 PM to 1:00 PM", new ArrayList<>(), "Syllabus Default", 3, "Room 304",feedbackManager));
        _course_catalog.add(new Course("SSH302", "Politics in Digital Era", 2, "Prof. Sanjay", "10:00 AM to 11:00 AM", new ArrayList<>(), "Syllabus Default", 3, "Room 305",feedbackManager));
        _course_catalog.add(new Course("ECE301", "Circuit Theory Design", 2, "Prof. Adani Kuna", "10:00 AM to 11:00 AM", new ArrayList<>(), "Syllabus Default", 3, "Room 306",feedbackManager));

        // Semester 4
        _course_catalog.add(new Course("CS401", "Database Management Systems", 4, "Prof. Modi", "10:00 AM to 11:00 AM", new ArrayList<>(), "Syllabus Default", 4, "Room 401",feedbackManager));
        _course_catalog.add(new Course("CS402", "Signals and Systems", 4, "Prof. Anubha Gupta", "1:00 PM to 2:00 PM", new ArrayList<>(Arrays.asList(findCourseByCode("ECE201"))), "Syllabus Default", 4, "Room 402",feedbackManager));
        _course_catalog.add(new Course("MATH401", "Algorithm Design and Analysis", 4, "Prof. Debarka Sengupta", "3:00 PM to 4:00 PM", new ArrayList<>(Arrays.asList(findCourseByCode("CSE201"), findCourseByCode("CSE301"))), "Syllabus Default", 4, "Room 403",feedbackManager));
        _course_catalog.add(new Course("HUM401", "Social Philosophy", 2, "Prof. Dravid Khanna", "10:00 AM to 11:00 AM", new ArrayList<>(), "Syllabus Default", 4, "Room 404",feedbackManager));
        _course_catalog.add(new Course("ECE401", "Bayesian Machine Learning", 2, "Prof. Satish Kumar", "10:00 AM to 11:00 AM", new ArrayList<>(), "Syllabus Default", 4, "Room 405",feedbackManager));
        _course_catalog.add(new Course("ECE402", "Digital Image Processing", 2, "Prof. Tammam Tilo", "10:00 AM to 11:00 AM", new ArrayList<>(), "Syllabus Default", 4, "Room 406",feedbackManager));

        // Semester 5
        _course_catalog.add(new Course("CSE501", "Computer Networks", 4, "Prof. Vivek Bohra", "2:00 PM to 3:00 PM", new ArrayList<>(), "Syllabus Default", 5, "Room 501",feedbackManager));
        _course_catalog.add(new Course("CSE502", "Embedded Logic Design", 4, "Prof. Uttumalai Maravar", "10:00 AM to 11:00 AM", new ArrayList<>(Arrays.asList(findCourseByCode("ECE201"))), "Syllabus Default", 5, "Room 502",feedbackManager));
        _course_catalog.add(new Course("MTH501", "Math 4", 4, "Prof. Shubhajit Goswami", "3:00 PM to 4:00 PM", new ArrayList<>(), "Syllabus Default", 5, "Room 503",feedbackManager));
        _course_catalog.add(new Course("MTH502", "Number Theory", 2, "Prof. Jatin Kumar", "11:00 AM to 12:00 PM", new ArrayList<>(), "Syllabus Default", 5, "Room 504",feedbackManager));
        _course_catalog.add(new Course("ECO501", "Game Theory", 2, "Prof. Kabir Sharma", "10:00 AM to 11:00 AM", new ArrayList<>(), "Syllabus Default", 5, "Room 505",feedbackManager));
        _course_catalog.add(new Course("SSH502", "Contract Theory", 2, "Prof. Ahaana Kotaraya", "10:00 AM to 11:00 AM", new ArrayList<>(), "Syllabus Default", 5, "Room 506",feedbackManager));
    }

    private static void handle_student_sign_up(Scanner scanner,String email){
        User existingUser = User.getUserByEmail(email);
        Student student;

        if(existingUser != null){
            System.out.println("An account for this Email already exists. Please login or use another mail-id");
        }
        else {
            student = new Student(email, course_catalog,complaintCatalog,feedbackManager);
            System.out.println("Enter password: ");
            String password = scanner.nextLine();
            student.sign_up(password);
        }
    }
    private static void handle_student_session(Scanner scanner, String email) throws InvalidLoginException, CourseFullException{

        User existingUser = User.getUserByEmail(email);
        Student student;

        if(existingUser != null){
            student = (Student) existingUser; //If the user alr exists we dont want it to instantiate a new object that is empty but rather cast it to the existing one
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        try {
            if (student.login(password)) {
                boolean session_active = true;
                while (session_active) {
                    System.out.println("Student Menu:");
                    System.out.println("1. View Courses");
                    System.out.println("2. Register for Courses");
                    System.out.println("3. View Schedule");
                    System.out.println("4. Track Academic Progress");
                    System.out.println("5. Drop Course");
                    System.out.println("6. Manage Complaints");
                    System.out.println("7. Reset Password");
                    System.out.println("8. Manage TA-ship");
                    System.out.println("9. Submit Feedback");
                    System.out.println("10. Logout");
                    String option = scanner.nextLine();
                    switch (option) {
                        case "1":
                            //VIEW COURSES
                            System.out.println("View Courses Menu");
                            System.out.println("1. View Current Courses");
                            System.out.println("2. View Available Course");
                            String internal_option_A = scanner.nextLine();
                            switch (internal_option_A) {
                                case "1":
                                    student.view_current_courses();
                                    break;
                                case "2":
                                    student.view_available_courses();
                                    break;
                            }
                            break;
                        case "2":
                            //REGISTRATION OF COURSES
                            System.out.println("Registering Courses Menu: ");
                            System.out.println("Enter course code to register: ");

                            String course_code_A = scanner.nextLine();
                            try {
                                Course course_A = findCourseByCode(course_code_A);

                                if (course_A == null) {
                                    throw new Exception("Course not found. Please enter a valid course code.");
                                } //Null error

                                student.add_course(course_A); //might throw CourseFullException

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "3":
                            // VIEW SCHEDULE
                            System.out.print("Your Schedule: ");
                            student.view_schedule();
                            break;

                        case "4":
                            // TRACK ACADEMIC PROGRESS
                            System.out.println("Track Academic Progress Menu: ");
                            System.out.println("1. View CGPA");
                            System.out.println("2. View SGPA for a Specific Semester");
                            System.out.println("3. View Grade for a Specific Course: ");
                            System.out.println("4. View Grade for Courses of a Specific Semester");
                            System.out.println("5. View Grade for All Courses");
                            String internal_option_B = scanner.nextLine();
                            switch (internal_option_B) {
                                case "1":
                                    student.view_CGPA();
                                    break;
                                case "2":
                                    System.out.println("Enter which semesters grade you wish to see: ");
                                    int semester = scanner.nextInt();
                                    student.view_SGPA(semester);
                                    break;
                                case "3":
                                    System.out.println("Enter Course Code to see grade: ");
                                    String Course_Code_C = scanner.nextLine();
                                    Course course_c = findCourseByCode(Course_Code_C);
                                    student.view_grade_for_course(course_c);
                                    break;
                                case "4":
                                    System.out.println("Enter Semester to see it's courses codes: ");
                                    int semester_2 = scanner.nextInt();
                                    student.view_semester_wise_grades(semester_2);
                                    break;
                                case "5":
                                    System.out.println("Grades of all courses: ");
                                    student.view_all_grades();
                                    break;
                            }
                            break;
                        case "5":
                            try {
                                if (isDropDeadlinePassed()) {
                                    throw new DropDeadlinePassedException("Cannot drop course. The drop deadline has passed.");
                                }
                                System.out.println("Enter Course Code to drop: ");
                                String Course_Code_D = scanner.nextLine();
                                Course course_d = findCourseByCode(Course_Code_D);
                                student.drop_course(course_d); }
                            catch (DropDeadlinePassedException e){
                                System.out.println(e.getMessage());
                            }
                            break;
                        case "6":
                            System.out.println("Manage Complaints Menu: ");
                            System.out.println("1. Issue a Complaint");
                            System.out.println("2. View Complaint Status");
                            String option_internal = scanner.nextLine();
                            switch (option_internal) {
                                case "1":
                                    System.out.println("Describe your complaint: ");
                                    String description = scanner.nextLine();
                                    student.issueComplaint(description);

                                    System.out.println("Complaint issued successfully.");
                                    break;

                                case "2":
                                    System.out.println("Enter UUID of your complaint: ");
                                    String complaintIdInput = scanner.nextLine();

                                    try {
                                        UUID complaintIdToView = UUID.fromString(complaintIdInput);
                                        student.viewComplaint(complaintIdToView);
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("Invalid UUID format. Please try again.");
                                    }
                                    break;
                            }
                            break;
                        case "7":
                            System.out.println("Reset Password Menu");
                            System.out.println("Enter Old Password: ");
                            String old_pass = scanner.nextLine();
                            System.out.println("Enter New Password: ");
                            String new_pass = scanner.nextLine();
                            student.reset_password(old_pass, new_pass);
                            break;
                        case "8":
                            if (student.get_isAlreadyTA()){
                                System.out.println("Mange TA-ship menu");
                                System.out.println("1. View your TA course ");
                                System.out.println("2. View Grades of a student: ");
                                System.out.println("3. Assign grades to a Student");
                                System.out.println("4. Remove your TA-ship: ");
                                String option_internal_W = scanner.nextLine();

                                switch(option_internal_W){
                                    case "1":
                                        student.getTA().getTA_courses().print_course_details();
                                        break;
                                    case "2":
                                        System.out.println("Enter course code to manage: ");
                                        String course_code_W = scanner.nextLine();
                                        Course course_W = findCourseByCode(course_code_W);
                                        System.out.println("Enter email of student to manage: ");
                                        String student_name_W = scanner.nextLine();
                                        Student student_W = findStudentByEmail(student_name_W);
                                        student.getTA().view_grade(student_W,course_W);
                                        break;
                                    case "3":
                                        System.out.println("Enter course code to manage: ");
                                        String course_code_U = scanner.nextLine();
                                        Course course_U = findCourseByCode(course_code_U);
                                        System.out.println("Enter email of student to manage: ");
                                        String student_name_U = scanner.nextLine();
                                        Student student_U = findStudentByEmail(student_name_U);
                                        System.out.println("Enter new grade: ");
                                        float grade = scanner.nextFloat();
                                        student.getTA().assign_grade(student_U,course_U,grade);
                                        break;
                                    case "4":
                                        student.remove_TA(student,student.getTA().getTA_courses());
                                        break;
                                    default:
                                        System.out.println("Invalid choice, please select from 1 to 4");
                                }
                            }
                            else{
                                System.out.println("You are not a TA to any course");
                            }
                            break;
                        case "9":
                            submitFeedbackCLI(student,scanner);
                            break;
                        case "10":
                            student.logout();
                            session_active = false;
                            break;
                        default:
                            System.out.println("Invalid option. Try again.");
                    }
                }
            }
        }
        catch (InvalidLoginException e){
            System.out.println(e.getMessage());
        }
        }
        else {
            System.out.println("Please Sign - Up first");
        }
    }

    private static void handle_professor_sign_up(Scanner scanner, String email){
        User existingUser = User.getUserByEmail(email);
        Professor professor;
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        if(existingUser != null){
            System.out.println("Email already registered. Kindly Login / Use a different mail ID");
        }
        else {
            professor = new Professor(email, course_catalog,complaintCatalog,feedbackManager);
            professor.sign_up(password); //I have implemented that user signs in automatically
        }
    }

    private static void handle_professor_session(Scanner scanner, String email) throws InvalidLoginException{

        User existingUser = User.getUserByEmail(email);

        if(existingUser != null){
            System.out.println("Enter password: ");
            String password = scanner.nextLine();
            Professor professor = (Professor) existingUser; //If the user alr exists we don't want it to instantiate a new object that is empty but rather cast it to the existing one

        try {
            if (professor.login(password)) {
                boolean session_active = true;
                while (session_active) {
                    System.out.println("Professor Menu:");
                    System.out.println("1. View your Courses");
                    System.out.println("2. Change Course Syllabus");
                    System.out.println("3. Change Course's Class timings");
                    System.out.println("4. Change Course's Credits");
                    System.out.println("5. Manage Course's Prerequisites");
                    System.out.println("6. Manage Course's Enrollment limits");
                    System.out.println("7. Change Office hours");
                    System.out.println("8. View Course's Enrolled Students");
                    System.out.println("9. Manage TA's");
                    System.out.println("10. Manage Feebacks");
                    System.out.println("11. Reset Password");
                    System.out.println("12. Logout");
                    String option = scanner.nextLine();
                    switch (option) {
                        case "1":
                            professor.view_courses();
                            break;
                        case "2":
                            System.out.println("Enter course code to change new syllabus to: ");
                            String course_code_2 = scanner.nextLine();
                            System.out.println("Enter the new syllabus: ");
                            String _new_syllabus = scanner.nextLine();
                            Course course_2 = findCourseByCode(course_code_2);
                            if (course_2 != null) {
                                professor.change_course_syllabus(course_2, _new_syllabus);
                            } else {
                                System.out.println("Course not found.");
                            }
                            break;
                        case "3":
                            System.out.println("Enter course code: ");
                            String course_code_3 = scanner.nextLine();
                            System.out.println("Enter the new timings: ");
                            String _new_timing = scanner.nextLine();
                            Course course_3 = findCourseByCode(course_code_3);
                            if (course_3 != null) {
                                professor.change_course_timings(course_3, _new_timing);
                            } else {
                                System.out.println("Course not found.");
                            }
                            break;

                        case "4":
                            System.out.println("Enter course code: ");
                            String course_code = scanner.nextLine();
                            System.out.println("Enter the new credits: ");
                            int _new_credits = scanner.nextInt();
                            Course course = findCourseByCode(course_code);
                            if (course != null) {
                                professor.change_course_credits(course, _new_credits);
                            } else {
                                System.out.println("Course not found.");
                            }
                            break;
                        case "5":
                            System.out.println("Enter Course Code of which pre-requisites you wish to manage: ");
                            String course_code_5 = scanner.nextLine();
                            Course course_5 = findCourseByCode(course_code_5);
                            if (course_5 != null) {
                                professor.view_prerequisites(course_5);
                            }
                            System.out.println("Do you wish to: \n1.Add a prerequisite \n2.Drop a prerequisite");
                            String option_internal_1 = scanner.nextLine();
                            System.out.println("Which course is to be Added/Dropped: ");
                            String changed_course_code = scanner.nextLine();
                            Course changed_course = findCourseByCode(changed_course_code);
                            if (course_5 != null) {
                                switch (option_internal_1) {
                                    case "1":
                                        professor.add_prerequisites(course_5, changed_course);
                                        break;
                                    case "2":
                                        professor.drop_prerequisites(course_5, changed_course);
                                        break;
                                    default:
                                        System.out.println("Please Add a valid option");
                                        break;
                                }
                            } else {
                                System.out.println("Course not found.");
                            }
                            break;
                        case "6":
                            System.out.println("Enter course code to manage enrollment_limit to: ");
                            String course_code_6 = scanner.nextLine();
                            Course course_6 = findCourseByCode(course_code_6);
                            System.out.print("Do you wish to: \n1.Add a new enrollment limit \n2.Drop any enrollment limit");
                            String option_internal_2 = scanner.nextLine();
                            System.out.println("Enter the new enrollment limit: ");
                            int enrollment_limit = scanner.nextInt();
                            if (course_6 != null) {
                                switch (option_internal_2) {
                                    case "1":
                                        professor.change_enrollment_limits(course_6, enrollment_limit);
                                        break;
                                    case "2":
                                        professor.drop_enrollment_limits(course_6);
                                        break;
                                    default:
                                        System.out.println("Please Add a valid option");
                                        break;
                                }
                            } else {
                                System.out.println("Course not found.");
                            }

                            break;
                        case "7":
                            System.out.println("Enter the new office hours: ");
                            String new_office_hour = scanner.nextLine();
                            professor.change_office_hours(new_office_hour);
                            break;
                        case "8":
                            System.out.println("Enter course code whose enrolled students you wish to view: ");
                            String course_code_8 = scanner.nextLine();
                            Course course_8 = findCourseByCode(course_code_8);
                            if (course_8 != null) {
                                professor.view_enrolled_students(course_8);
                            } else {
                                System.out.println("Course not found.");
                            }
                            break;
                        case "9":
                            System.out.println("Manage TAs Menu: ");
                            System.out.println("1. View Course TAs");
                            System.out.println("2. Assign TA to a course");
                            System.out.println("3. Remove TA from a course");
                            System.out.println("4. Go back");
                            String option_internal = scanner.nextLine();
                            switch (option_internal){
                                case "1":
                                    System.out.println("Enter course code to view TAs of: ");
                                    String course_code_Z = scanner.nextLine();
                                    Course course_Z = findCourseByCode(course_code_Z);
                                    professor.view_TAs(course_Z);
                                    break;
                                case "2":
                                    System.out.println("Enter course code to assign TA to: ");
                                    String course_code_Y = scanner.nextLine();
                                    Course course_Y = findCourseByCode(course_code_Y);
                                    System.out.println("Enter email of student to assign TA: ");
                                    String student_name_Y = scanner.nextLine();
                                    Student student_Y = findStudentByEmail(student_name_Y);
                                    professor.assign_TA(student_Y,course_Y);
                                    break;
                                case "3":
                                    System.out.println("Enter course code to remove TA from: ");
                                    String course_code_X = scanner.nextLine();
                                    Course course_X = findCourseByCode(course_code_X);
                                    System.out.println("Enter email of student to remove TA: ");
                                    String student_name_X = scanner.nextLine();
                                    Student student_X = findStudentByEmail(student_name_X);
                                    professor.remove_TA(student_X, course_X);
                                    break;
                                case "4":
                                    break;
                                default:
                                    System.out.println("Invalid choice, please choose an integer between 1 to 4");
                            }
                            break;
                        case "10":
                            System.out.println("Manage Feedbacks Menu: ");
                            System.out.println("1. Change Visibility of Feedbacks");
                            System.out.println("2. View Feedbacks");
                            System.out.println("3. Go back");
                            String option_internal_4 = scanner.nextLine();
                            System.out.println("Enter the course code to manage feedback for:");
                            String courseCode = scanner.nextLine();
                            // Find the course by code
                            Course course_U = findCourseByCode(courseCode);
                            switch (option_internal_4){
                                case "1":
                                    System.out.println("You want visibility of feedbacks to be: ");
                                    System.out.println("1. Anonymous");
                                    System.out.println("2. Visible");
                                    String option_to_internal = scanner.nextLine();
                                    switch (option_to_internal){
                                        case "1":
                                            professor.change_anonymity(course_U, true);
                                            break;
                                        case "2":
                                            professor.change_anonymity(course_U, false);
                                            break;
                                    }
                                    
                                case "2":
                                    professor.viewFeedback(course_U);
                                    break;
                                case "3":
                                    break;
                                default:
                                    System.out.println("Invalid choice, kindly choose integer from 1 to 3");
                            }
                            break;
                        case "11":
                            System.out.println("Reset Password Menu");
                            System.out.println("Enter Old Password: ");
                            String old_pass = scanner.nextLine();
                            System.out.println("Enter New Password: ");
                            String new_pass = scanner.nextLine();
                            professor.reset_password(old_pass, new_pass);
                            break;
                        case "12":
                            professor.logout();
                            session_active = false;
                            break;
                        default:
                            System.out.println("Invalid option. Try again.");
                            break;
                    }
                }
            }
        } catch (InvalidLoginException e) {
            System.out.println(e.getMessage()); //Prints Invalid Login Message
        }
        }
        else {
            System.out.println("Kindly Sign-Up first");
        }
    }

    private static void handle_administrator_sign_up(Scanner scanner, String email){
        User existingUser = User.getUserByEmail(email);
        if(existingUser != null){
            System.out.println("Email already registered. Kindly Login / Use a different mail ID");
        }
        else {
            Administrator administrator = new Administrator(email, course_catalog, complaintCatalog);
            administrator.sign_up(); //We don't need to ask a password or have any other sign up mechanism we just need to add it in the administrator's (here, user's list)
        }
    }

    private static void handle_administrator_session(Scanner scanner, String email) throws InvalidLoginException, CourseFullException  {
            User existingUser = User.getUserByEmail(email);
            if (existingUser != null) {
                Administrator administrator = (Administrator) existingUser; //If the user alr exists we don't want it to instantiate a new object that is empty but rather cast it to the existing one
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                try {
                    if (administrator.login(password)) {
                        boolean session_active = true;
                        while (session_active) {
                            System.out.println("Admin's Menu:");
                            System.out.println("1. Manage Course Catalog");
                            System.out.println("2. Manage Courses");
                            System.out.println("3. Manage Student Records");
                            System.out.println("4. Change Professors to Courses");
                            System.out.println("5. Manage Complaints");
                            System.out.println("6. Set Drop Deadline");
                            System.out.println("7. Logout");
                            String option = scanner.nextLine();
                            switch (option) {
                                case "1":
                                    System.out.println("Manage Course Catalog Menu: ");
                                    System.out.println("1. View Course Catalog");
                                    System.out.println("2. Add a Course to the Catalog");
                                    System.out.println("3. Delete a Course from the Catalog");
                                    System.out.println("4. Go back");
                                    String option_internal_AA = scanner.nextLine();
                                    switch (option_internal_AA) {
                                        case "1":
                                            administrator.view_catalog();
                                            break;
                                        case "2":
                                            System.out.println("Enter Course Code of the new course:");
                                            String courseCode = scanner.nextLine();
                                            System.out.println("Enter Course Title:");
                                            String courseTitle = scanner.nextLine();
                                            System.out.println("Enter Credits:");
                                            int credits = Integer.parseInt(scanner.nextLine());
                                            System.out.println("Enter Professor Email:");
                                            String professorEmail = scanner.nextLine();
                                            System.out.println("Enter Timings (e.g., 4:30 PM to 6:00 PM):");
                                            String timings = scanner.nextLine();
                                            System.out.println("Enter Prerequisites (comma separated course codes):");
                                            String prerequisitesInput = scanner.nextLine();
                                            ArrayList<Course> prerequisites = new ArrayList<>();
                                            for (String prereqCode : prerequisitesInput.split(",")) {
                                                Course prereq = findCourseByCode(prereqCode.trim());
                                                if (prereq != null) {
                                                    prerequisites.add(prereq);
                                                }
                                            }
                                            System.out.println("Enter Syllabus:");
                                            String syllabus = scanner.nextLine();
                                            System.out.println("Enter Semester:");
                                            int semester = Integer.parseInt(scanner.nextLine());
                                            System.out.println("Enter Location:");
                                            String location = scanner.nextLine();
                                            System.out.println("Enter Enrollment Limit (0 if no limit):");
                                            int enrollmentLimit = Integer.parseInt(scanner.nextLine());

                                            Course newCourse;
                                            if (enrollmentLimit > 0) {
                                                newCourse = new Course(courseCode, courseTitle, credits, professorEmail, timings, prerequisites, syllabus, semester, location, enrollmentLimit, feedbackManager);
                                            } else {
                                                newCourse = new Course(courseCode, courseTitle, credits, professorEmail, timings, prerequisites, syllabus, semester, location, feedbackManager);
                                            }
                                            administrator.add_course(newCourse);
                                            break;
                                        case "3":
                                            System.out.println("Enter Course Code of the Course to be deleted:");
                                            String course_code_AB = scanner.nextLine();
                                            Course course_AB = findCourseByCode(course_code_AB);
                                            administrator.delete_course(course_AB);
                                            break;
                                        case "4":
                                            break;
                                        default:
                                            System.out.println("Kindly Enter a Valid option");
                                            break;
                                    }
                                    break;
                                case "2":
                                    System.out.println("Enter Course Code to Manage: ");
                                    String course_code_AC = scanner.nextLine();
                                    Course course_AC = findCourseByCode(course_code_AC);
                                    System.out.println("Course Manager Menu: ");
                                    System.out.println("1. Change Course Syllabus");
                                    System.out.println("2. Change Course Timings");
                                    System.out.println("3. Change Course Credits");
                                    System.out.println("4. Manage Course Pre-requisites");
                                    System.out.println("5. Manage Course Enrollment - Limits");
                                    System.out.println("6. Go back");
                                    String option_internal_AD = scanner.nextLine();
                                    if (course_AC != null) {
                                        switch (option_internal_AD) {
                                            case "1":
                                                System.out.println("Enter the new syllabus:");
                                                String new_syllabus = scanner.nextLine();
                                                administrator.change_course_syllabus(course_AC, new_syllabus);
                                                break;
                                            case "2":
                                                System.out.println("Enter the new timings:");
                                                String new_timings = scanner.nextLine();
                                                administrator.change_course_timings(course_AC, new_timings);
                                                break;
                                            case "3":
                                                System.out.println("Enter the new credits:");
                                                int new_credits = scanner.nextInt();
                                                administrator.change_course_credits(course_AC, new_credits);
                                                break;
                                            case "4":
                                                System.out.println("Managing Pre-requisites Menu: ");
                                                System.out.println("1. View Pre-requisites");
                                                System.out.println("2. Add Pre-requisites");
                                                System.out.println("3. Drop Pre-requisites");
                                                String option_internal_AF = scanner.nextLine();
                                                switch (option_internal_AF) {
                                                    case "1":
                                                        administrator.view_prerequisites(course_AC);
                                                        break;
                                                    case "2":
                                                        System.out.println("Enter Course Code of the Course to be Added:");
                                                        String course_code_AB = scanner.nextLine();
                                                        Course course_AB = findCourseByCode(course_code_AB);
                                                        administrator.add_prerequisites(course_AC, course_AB);
                                                        break;
                                                    case "3":
                                                        System.out.println("Enter Course Code of the Course to be Deleted:");
                                                        String course_code_AD = scanner.nextLine();
                                                        Course course_AD = findCourseByCode(course_code_AD);
                                                        administrator.drop_prerequisites(course_AC, course_AD);
                                                        break;
                                                    default:
                                                        System.out.println("Kindly Enter a Valid Option");
                                                        break;
                                                }
                                                break;
                                            case "5":
                                                System.out.println("Managing Enrollment_Limits Menu: ");
                                                System.out.println("1. Add New Enrollment Limit");
                                                System.out.println("2. Drop Enrollment Limit");
                                                String option_internal_AG = scanner.nextLine();
                                                switch (option_internal_AG) {
                                                    case "1":
                                                        System.out.println("Enter new Enrollment Limit: ");
                                                        int new_limit = scanner.nextInt();
                                                        administrator.change_enrollment_limits(course_AC, new_limit);
                                                        break;
                                                    case "2":
                                                        administrator.drop_enrollment_limits(course_AC);
                                                        break;
                                                    default:
                                                        System.out.println("Kindly Enter a Valid Option");
                                                        break;
                                                }
                                            case "6":
                                                break;
                                            default:
                                                System.out.println("Invalid choice");
                                        }
                                    } else {
                                        System.out.println("Course not found.");
                                    }
                                    break;
                                case "3":
                                    System.out.println("Enter Student Email:");
                                    String studentEmail = scanner.nextLine();
                                    Student student = findStudentByEmail(studentEmail);

                                    if (student == null) return;

                                    while (true) {
                                        System.out.println("Manage Student Records Menu: ");
                                        System.out.println("1. View Current Courses");
                                        System.out.println("2. Add a Course");
                                        System.out.println("3. Drop a Course");
                                        System.out.println("4. View CGPA");
                                        System.out.println("5. View SGPA");
                                        System.out.println("6. View Grade for Course");
                                        System.out.println("7. View Grade for Semester");
                                        System.out.println("8. View All Grades");
                                        System.out.println("9. View Credits");
                                        System.out.println("10. View Semester");
                                        System.out.println("11. View Name");
                                        System.out.println("12. Change Name");
                                        System.out.println("13. Complete Semester");
                                        System.out.println("14. Go back");

                                        String option_BA = scanner.nextLine();

                                        switch (option_BA) {
                                            case "1":
                                                administrator.view_current_courses(student);
                                                break;
                                            case "2":
                                                System.out.println("Enter Course Code to Add:");
                                                String addCourseCode = scanner.nextLine();
                                                Course addCourse = findCourseByCode(addCourseCode);
                                                administrator.add_courses(student, addCourse);
                                                break;
                                            case "3":
                                                System.out.println("Enter Course Code to Drop:");
                                                String dropCourseCode = scanner.nextLine();
                                                Course dropCourse = findCourseByCode(dropCourseCode);
                                                administrator.drop_course(student, dropCourse);
                                                break;
                                            case "4":
                                                administrator.view_CGPA(student);
                                                break;
                                            case "5":
                                                System.out.println("Enter Semester:");
                                                int semester = scanner.nextInt();
                                                administrator.view_SGPA(student, semester);
                                                scanner.nextLine();
                                                break;
                                            case "6":
                                                System.out.println("Enter Course Code:");
                                                String gradeCourseCode = scanner.nextLine();
                                                Course gradeCourse = findCourseByCode(gradeCourseCode);
                                                administrator.view_grade_for_course(student, gradeCourse);
                                                break;
                                            case "7":
                                                System.out.println("Enter Semester:");
                                                int sem = scanner.nextInt();
                                                administrator.view_grade_for_sem(student, sem);
                                                scanner.nextLine(); // Consume newline
                                                break;
                                            case "8":
                                                administrator.view_all_grades(student);
                                                break;
                                            case "9":
                                                System.out.println("Credits: " + administrator.view_credits(student));
                                                break;
                                            case "10":
                                                System.out.println("Semester: " + administrator.view_semester(student));
                                                break;
                                            case "11":
                                                System.out.println("Name: " + administrator.view_name(student));
                                                break;
                                            case "12":
                                                System.out.println("Enter New Name:");
                                                String newName = scanner.nextLine();
                                                administrator.change_name(student, newName);
                                                break;
                                            case "13":
                                                administrator.semester_completion(student);
                                                break;
                                            case "14":
                                                return;
                                            default:
                                                System.out.println("Invalid option. Please try again.");
                                        }
                                        break;
                                    }
                                case "4":
                                    System.out.println("Enter Course Code of the Course to be Assigned:");
                                    String course_code_AD = scanner.nextLine();
                                    Course course_AD = findCourseByCode(course_code_AD);
                                    System.out.println("Enter Professor mail id: ");
                                    String professor1 = scanner.nextLine();
                                    Professor professor = find_professor_by_email(professor1);
                                    administrator.assign_professor(professor, course_AD);
                                    break;
                                case "5":
                                    System.out.println("Manage Complaints Menu: ");
                                    System.out.println("1. View all complaints");
                                    System.out.println("2. Change Status of a Complaint");
                                    System.out.println("3. Go back");
                                    String option_internal = scanner.nextLine();
                                    switch (option_internal) {
                                        case "1":
                                            administrator.viewAllComplaints();
                                            break;

                                        case "2":
                                            System.out.println("Enter UUID of the complaint you want to update: ");
                                            String complaintIdInput = scanner.nextLine();

                                            try {
                                                UUID complaintId = UUID.fromString(complaintIdInput);

                                                System.out.println("Enter new status (e.g., Open, In Progress, Closed): ");
                                                String newStatus = scanner.nextLine();

                                                administrator.changeComplaintStatus(complaintId, newStatus);

                                            } catch (IllegalArgumentException e) {
                                                System.out.println("Invalid UUID format. Please try again.");
                                            }
                                            break;
                                        case "3":
                                            break;
                                        default:
                                            System.out.println("Invalid Option");
                                            break;
                                    }
                                    break;
                                case "6":
                                    try {
                                        System.out.println("Enter new drop deadline in format (dd MMM yyyy, hh:mm a): ");
                                        String newDeadlineStr = scanner.nextLine();
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
                                        dropDeadline = sdf.parse(newDeadlineStr);
                                        System.out.println("Drop deadline successfully updated to: " + sdf.format(dropDeadline));
                                    } catch (Exception e) {
                                        System.out.println("Invalid date format. Please try again.");
                                    }
                                    break;
                                case "7":
                                    administrator.logout();
                                    session_active = false;
                                    break;
                                default:
                                    System.out.println("Invalid option. Try again.");
                            }
                        }
                    }
                } catch (InvalidLoginException e) {
                    System.out.println(e.getMessage()); //Prints Invalid Login Message
                }
            } else {
            System.out.println("Please Sign - Up first");
        }

    }
    private static Course findCourseByCode(String course_code) {
        for (Course course : course_catalog.get_courses()) {
            if (course.get_course_code().equals(course_code)) {
                return course;
            }
        }
        return null;
    }

    private static Student findStudentByEmail(String email) {
        for (User user : User.userList) {
            if (user instanceof Student && user.get_email().equals(email)) {
                return (Student) user; // Downcasting
            }
        }

        // If student not found, create a new one
        System.out.println("Student not found. Creating new student...");
        Student newStudent = new Student(email, course_catalog,complaintCatalog,feedbackManager);
        newStudent.sign_up("XYZ");
        return newStudent;
    }


    private static Professor find_professor_by_email(String email) {
        for (User user : User.userList) {
            if (user instanceof Professor professor) {
                // Down-casting
                if (professor.get_email().equals(email)) {
                    return professor;
                }
            }
        }

        // If professor not found, create a new one
        System.out.println("Professor not found. Creating new professor...");
        Professor newProfessor = new Professor(email, course_catalog,complaintCatalog,feedbackManager);
        newProfessor.sign_up("XYZ");
        return newProfessor;
    }

    private static boolean isDropDeadlinePassed() {
        Date currentDate = new Date();
        return currentDate.after(dropDeadline);
    }

    private static void submitFeedbackCLI(Student student, Scanner scanner) {
        System.out.println("Enter the course code you want to submit feedback for:");
        String courseCode = scanner.nextLine();

        // Find the course by code
        Course course = findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        if (course.isAnonymous_Feedback()){
            System.out.println("Feedback is Anonymous for this Course");
        }
        else{
            System.out.println("Feedback is not Anonymous for this Course");
        }
        // Collect Numeric Feedback
        System.out.println("Enter your numeric rating (1-5, or press Enter to skip):");
        String ratingInput = scanner.nextLine();
        Integer rating = null;  // Numeric rating, can be null if skipped

        // If the user provided a rating, parse it
        if (!ratingInput.trim().isEmpty()) {
            try {
                rating = Integer.parseInt(ratingInput);
                if (rating < 1 || rating > 5) {
                    System.out.println("Invalid rating. Must be between 1 and 5.");
                    rating = null;  // Invalidate the rating if out of range
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for rating. Skipping numeric rating.");
            }
        }

        // Collect Textual Feedback
        System.out.println("Enter your textual feedback (or press Enter to skip):");
        String comment = scanner.nextLine();

        // Submit feedback if either rating or comment is provided
        if (rating != null) {
            student.submitFeedback(course, rating);  // Submit numeric feedback
            System.out.println("Rating submitted successfully.");
        }

        if (!comment.trim().isEmpty()) {
            student.submitFeedback(course, comment);  // Submit text feedback
            System.out.println("Text feedback submitted successfully.");
        }

        if (rating == null && comment.trim().isEmpty()) {
            System.out.println("No feedback provided.");
        }
    }

}
