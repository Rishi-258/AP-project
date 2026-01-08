import ERPSytem.Loginpage.AuthService;
import ERPSytem.UserSession;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    AuthService auth = new AuthService();

    @Test
    public void testValidAdminLogin() {
        boolean result = auth.authenticate("admin1", "adminpass");
        assertTrue(result);
        assertEquals("Admin", UserSession.role);
    }

    @Test
    public void testValidStudentLogin() {
        boolean result = auth.authenticate("stu1", "stupass");
        assertTrue(result);
        assertEquals("Student", UserSession.role);
    }

    @Test
    public void testInvalidPassword() {
        boolean result = auth.authenticate("admin1", "wrongpass");
        assertFalse(result);
    }

    @Test
    public void testNonExistentUser() {
        boolean result = auth.authenticate("randomUser", "pass");
        assertFalse(result);
    }

    @Test
    public void testEmptyCredentials() {
        boolean result = auth.authenticate("", "");
        assertFalse(result);
    }
}
