public interface User_action {
    void sign_up(String password);// Logs in the user
    void reset_password(String old_password, String new_password);
}
