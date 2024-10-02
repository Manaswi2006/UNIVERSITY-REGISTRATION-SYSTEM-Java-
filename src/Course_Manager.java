public interface Course_Manager {
    public void change_course_syllabus(Course course, String new_syllabus);
    public void change_course_timings(Course course, String new_timings);
    public void change_course_credits(Course course, int new_credits);
    public void add_prerequisites(Course course, Course new_course);
    public void drop_prerequisites(Course course, Course old_prerequisite);
    public void view_prerequisites(Course course);
    public void change_enrollment_limits (Course course, int new_enrollment_limit);
    public void drop_enrollment_limits ( Course course );
}
