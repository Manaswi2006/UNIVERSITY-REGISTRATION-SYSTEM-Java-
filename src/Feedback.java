public class Feedback<T> {
    private final T feedback;  // Generic type to handle both numeric and textual feedback
    private final Student student;  // To track which student gave this feedback
    private final Course course;

    public Feedback(T feedback, Student student, Course course) {
        this.feedback = feedback;
        this.student = student;
        this.course = course;
    }

    public T getFeedback() { return this.feedback; }

    public Student getStudent(){ return this.student; }

    public String getStudentName() { return getStudent().get_name(); }

    public Course getCourse() { return this.course; }

    // Display feedback in a readable format

    //CAN VIEW WITH NAMES OR ANONYMOUSLY (PROFESSOR CAN SET IT TO ANONYMOUS/VISIBLE)
    public String displayFeedback() {
        if (getCourse().isAnonymous_Feedback()) {
            return this.getFeedback().toString();
        }
        else{
            return "Feedback by " + this.getStudentName() + ": " + this.getFeedback().toString();
        }
    }

}
