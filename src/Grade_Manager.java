public interface Grade_Manager {
    public void view_grade(Student student, Course course);
    public void assign_grade(Student student, Course course, float grade);
    public void fail_student(Student student);
}
