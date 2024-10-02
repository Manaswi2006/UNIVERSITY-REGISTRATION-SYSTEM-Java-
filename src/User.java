import java.util.ArrayList; // We will use this to store the mail and password database

public class User  {
    protected final String email; // final because changing mail I am assuming isn't allowed (it will break down my code as email is what identifies it in the database)
    protected String password;
    protected Course_Catalog course_catalog;
    protected Complaint_Catalog complaintCatalog;
    // ArrayList to simulate the database of users (email, password)
    protected static ArrayList<User> userList = new ArrayList<>();

    // Constructor initializes email only
    public User(String email,Course_Catalog _course_catalog, Complaint_Catalog complaintCatalog) {
        this.email = email;
        this.course_catalog = _course_catalog;
        this.complaintCatalog = complaintCatalog;
    }

    //GETTERS AND SETTERS
    public String get_email() {
        return email;
    }
    //no set email as email is unique and final

    protected String get_password(){
        return this.password;
    }
    //Protected as anyone can't access the password

    protected void set_password(String new_password){
        this.password = new_password;
    }

    public Course_Catalog get_course_catalog(){
        return this.course_catalog;
    }

    public void set_course_catalog(Course_Catalog _course_catalog){
        this.course_catalog = _course_catalog;
    }

    public Complaint_Catalog get_complaint_catalog(){
        return this.complaintCatalog;
    }

    public void set_complaint_catalog(Complaint_Catalog complaintCatalog) {
        this.complaintCatalog = complaintCatalog;
    }

    public ArrayList<User> get_userList(){
        return User.userList;
    }

    public void set_userList(ArrayList<User> _userList){
        User.userList = _userList;
    }

    // Login method
    public boolean login(String password) throws InvalidLoginException{
        // Iterate over the list of users
        for (User user : get_userList()) {
            // Check if the email matches and the password is correct
            if (user.get_email().equals(get_email())) {
                if (user.get_password().equals(password)) {
                    System.out.println("Login successful! You can now use your account.");
                    return true;
                } else {
                    throw new InvalidLoginException("Incorrect password. Please try again.");
                }
            }
        }

        // If no user is found with the given email, assume it's a new user
        // For Administrators, no sign-up is needed as the password is preset
        if (this instanceof Administrator) {
            if (password.equals("XYZ")){
                return true;
            }
            throw new InvalidLoginException("Incorrect password. Please try again.");
        }

        // For other users, sign them up with the provided password
        set_password(password); //first set up its password
        userList.add(this); //then add it to the userlist
        System.out.println("User signed up successfully! You can now use your account.");
        return true;
    }


    // Logout method (Exit to home page)
    public void logout() {
        System.out.println("Logged out. Returning to home page...");
    }


    public static User getUserByEmail(String email) {
        for (User user : userList) {
            if (user.get_email().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
