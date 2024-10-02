import java.util.ArrayList;

public class Course_Catalog extends Registration_system<Course>{
    private ArrayList<Course> courses;

    public Course_Catalog(){
        this.courses = new ArrayList<>();
    }

    //GETTERS AND SETTERS
    public ArrayList<Course> get_courses(){
        return this.courses;
    }

    public void set_courses(ArrayList<Course> courses) {
        if (courses != null) {
            this.courses = courses;
        } else {
            System.out.println("Cannot set a null list of courses");
        }
    }

    // no explicit use of setter function -> I am using add and delete to set up the catalog

    // FUNCTIONALITIES
    @Override
    public void view() {
        if (get_courses().isEmpty()){
            System.out.println("Empty Catalog");
        }
        else{
            for (Course course : courses){
                course.print_course_details();
            }
        }
    }

    // Adding courses to catalog
    @Override
    public void add(Course course){
        if (course ==null){
            System.out.println("Something went wrong while instantiation of course");
        }
        else if ( !get_courses().contains(course) ) {
            courses.add(course);
            System.out.println("Course added: " + course.get_course_title());
        }
        else {
            System.out.println("Course already exists");
        }
    }

    // Delete a course from the catalog
    @Override
    public void delete(Course course){
        if(get_courses().contains(course)){
            courses.remove(course);
            System.out.println("Course removed: " + course.get_course_title() );
        }
        else{
            System.out.println("Course not found");
        }
    }
}
