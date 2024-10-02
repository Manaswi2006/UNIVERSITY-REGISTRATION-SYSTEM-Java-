# UNIVERSITY-REGISTRATION-SYSTEM
Robust JAVA command line application that handles Student, Professor, Administrator Registration and Functionalities including Courses Management, Complaint Management and Anonymous Feedback Management.

## OVERVIEW
In summary, the ERP System has been designed using the principles fundamental to object-oriented programming in java so as to help with student enrollment management, maintain course schedules, complaints and handle academic records. It also enables students professors and administrators to perform all university related activities on a single platform. In this way, a unified structure is built that harnesses class inheritance, polymorphism among other aspects related to encapsulation, abstraction and generic programming.

## COMPONENTS
### CLASSES
1. Main
2. User
3. Student
4. Professor
5. Administrator
6. Registration System : Abstract Class
7. Courses
8. Course_Catalog
9. Complaints
10. Complaint_Catalog
11. Time Parser
12. Feedback
13. Feedback_Manager
14. Teaching Assistant

### INTERFACES
1. User_action
2. Course_Manager
3. Grade_Manager

### EXCEPTIONS
1. CourseFullException
2. DropDeadlinePassedException
3. InvalidLoginException

## CLASS RELATIONSHIPS

1. User as the Parent Class:
    - The class `User` serves as the parent class from which the ‘Administrator’ , ‘Student’, and ‘Professor’ classes inherit. I created this inheritance because all three classes share common attributes and methods defined in the ‘User’ class that are:
      Login
      Logout
      and are added in a shared list userList that uniquely identifies emails to find the user.

2. User Action Interface:
    - ‘User_Action’ is an interface implemented by the classes ‘Professor’ and ‘Student’. This interface defines actions that these must perform that are :
      Sign - in
      Reset Password

3. Complaint Management:
    - The ‘Complaint_Catalog’ class manages multiple complaints through a one-to-many relationship with the `Complaint` class. Each ‘Complaint_Catalog’ can manage many ‘Complaint’ instances by making an ArrayList of all Complaints, ensuring that complaints raised by students are organized within the catalog. It supports functions view, add, drop.

4. Course Management:
    - The ‘Course_Catalog’ class manages multiple courses through a one-to-many relationship with the ‘Course’ class. Each ‘Course_Catalog’ can manage many `Course` instances by making an ArrayList of all Courses , facilitating the administration and management of academic courses. An interface ‘Course_Manager’ is implemented by Professors and Administrators because the functions of changing course details are declared here which helps us in understanding what utilities are available in both the classes and ensures consistency.

5. Student-Course Relationship:
    - There is a many-to-many relationship between ‘Course’ and ‘Student’. A single course can have many students enrolled, and each student can be enrolled in multiple courses.

6. Professor-Course Relationship:
    - Each professor can teach many courses, but each course is taught by only one professor. This creates a one-to-many relationship from ‘Professor’ to ‘Course’, where professors are responsible for managing their courses.

7. Administrator-Manages Attributes:
    - The ‘Administrator’ class has management control over both ‘Student’ and ‘Professor’ attributes, enabling administrators to update or modify relevant information for both students and professors.
    - Additionally, the `Administrator` class utilizes ‘Time_Parser’ to find compatibility of courses with professors

8. System Catalog Inheritance:
   `Registration_System` class functions as an abstract parent class for both `Complaint_Catalog` and `Course_Catalog`. It exploits generic programming since each subclass handles distinct types; this is evident in the fact that `Complaint_Catalog` manages instances of Complaint whereas `Course_Catalog` takes care of Course objects. The abstract nature of Registration_System thus gives room for it to declare common functions while enabling its subclasses to implement these functions in distinct ways, tailored to their specific needs.

9. Feedback Management:
    Many feedbacks are stored in the Feedback_Manager that stores a Map of course to their feedbacks. It is a one-to-many relationship.
 
## ASSUMPTIONS
1. If student fails a subject, they fail the entire semester and have to repeat it.
2. Professors can teach multiple courses.
3. The courses and their details are in an Arraylist called Course Catalog.
4. We have a function called 'logout' that logs out a specific account, after logout all previous information is retained. However, upon exit of the application, i.e termination of code information is lost.
5. All course codes and email IDs are unique.
6. There is no track of total credits of a student and no system for graduation, after completion of 4th year(8th sem) the student moves to 9th sem as per the current design of code.
7. When a new account is made by administrator, the default password is set to 'XYZ' that the student or professor can change later.
8. For all courses, timing is given in format 'HH:mm AM/PM to HH:mm AM/PM' , and the professor will be teaching this course on every working day of the week at this time.
   
