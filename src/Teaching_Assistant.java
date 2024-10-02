import java.util.ArrayList;

public class Teaching_Assistant extends Student implements Grade_Manager{
    private Course TA_course;

    public Teaching_Assistant(String email, Course_Catalog _course_catalog,Complaint_Catalog complaintCatalog, Feedback_Manager feedbackmanager){
        super(email, _course_catalog,complaintCatalog,feedbackmanager);
    }

    public Course getTA_courses(){
        return this.TA_course;
    }

    public void setTA_courses(Course course){
        this.TA_course = course;
    }


    @Override
    public void view_grade(Student student, Course course) {
        if (course == getTA_courses()){
            if (student.get_completed_courses().containsKey(course)) {
                // Get the grade from the completed courses
                float grade = student.get_completed_courses().get(course);
                System.out.printf("Grade for course is: %.2f%n", grade); // Print with two decimal places
            } else {
                System.out.println("Course hasn't been completed yet");
            }
        }
        else{
            System.out.println("You don't teach this course");
        }
    }

    @Override
    public void assign_grade(Student student, Course course, float grade) {
        if (course == getTA_courses()){
            if (student.get_completed_courses().containsKey(course)) {
                grade = student.get_completed_courses().get(course);
                if (grade < 4.0) {
                    fail_student(student);
                }
            }
            else{
                System.out.println("TAs can only assign grades upon Semester Completion");
            }
        }
        else{
            System.out.println("You don't teach this course");
        }
    }

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
}
