package ERPSytem;



public class UserSession {
    public static int userId = -1;
    public static String username = null;
    public static String role = null;

    public static void clear() {
        userId = -1;
        username = null;
        role = null;
    }
}


