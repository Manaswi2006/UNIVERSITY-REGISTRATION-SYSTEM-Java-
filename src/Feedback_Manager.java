import java.util.*;

public class Feedback_Manager {
    // Map to store feedback for each course
    private Map<Course, List<Feedback<?>>> courseFeedbackMap = new HashMap<>();

    // Method for submitting feedback (generic type allows both numeric and text feedback)
    public <T> void submitFeedback(Course course, T feedback, Student student) {
        List<Feedback<?>> feedbackList = courseFeedbackMap.getOrDefault(course, new ArrayList<>());
        feedbackList.add(new Feedback<>(feedback, student,course));  // Add feedback to the list
        courseFeedbackMap.put(course, feedbackList);              // Update the map
//        System.out.println("Feedback submitted for course: " + course.get_course_title());
    }

    // Method for viewing feedback for a specific course
    public void viewfeedback(Course course) {
        List<Feedback<?>> feedbackList = courseFeedbackMap.getOrDefault(course, new ArrayList<>());
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback available for this course.");
        } else {
            System.out.println("Feedback for course: " + course.get_course_title());
            for (Feedback<?> feedback : feedbackList) {
                System.out.println(feedback.displayFeedback());
            }
        }
    }
}
