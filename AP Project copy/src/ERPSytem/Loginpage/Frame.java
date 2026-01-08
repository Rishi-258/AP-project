package ERPSytem.Loginpage;

import ERPSytem.*;
import ERPSytem.Admin.ahomepage;
import ERPSytem.Databaseconnector.database;
import ERPSytem.Faculty.fhomepage;
import ERPSytem.Faculty.mfhomepage;
import ERPSytem.Student.homepage;
import ERPSytem.Student.mhomepage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.*;

public class Frame extends JFrame {
    JPanel Nframe;
    JLabel errorlable;

    public Frame() {
        ImageIcon image = new ImageIcon("/home/parv-jain/Documents/AP Project/src/ERPSytem/iiitd.png");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        // --- Background panel ---
        BackgroundPanel bg = new BackgroundPanel("/home/parv-jain/Documents/AP Project/src/ERPSytem/bg.png");
        bg.setLayout(null);

        // --- Header label ---
        JLabel label = new JLabel("Welcome to IIITD ERP", image, JLabel.CENTER);
        label.setFont(new Font("Times New Roman", Font.BOLD, 60));
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.setBorder(border);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(0, 0, screenSize.width, 250);

        // --- Center panel (Nframe) ---
        Nframe = new JPanel();
        Nframe.setLayout(null);
        Nframe.setBackground(Color.WHITE);
        int panelWidth = 800, panelHeight = 900;
        int x = (screenSize.width - panelWidth) / 2;
        int y = (screenSize.height - panelHeight) / 2;
        Nframe.setBounds(x, y, panelWidth, panelHeight);
        Nframe.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // --- Center alignment base ---
        int fieldWidth = 360;
        int fieldHeight = 60;
        int startX = (panelWidth - fieldWidth) / 2;
        int currentY = 250;

        // --- Username label and field ---
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        userLabel.setBounds(startX - 180, currentY, 160, 50);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(startX, currentY, fieldWidth, fieldHeight);
        usernameField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // --- Password label and field ---
        currentY += 100;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        passLabel.setBounds(startX - 180, currentY, 160, 50);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(startX, currentY, fieldWidth, fieldHeight);
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // --- Login button ---
        currentY += 120;
        Button loginBtn = new Button("Login");
        loginBtn.setBounds((panelWidth - 220) / 2, currentY, 220, 70);
        loginBtn.setBackground(Color.BLUE);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Times New Roman", Font.BOLD, 28));
        loginBtn.setFocusable(false);

        // --- Error label ---
        errorlable = new JLabel("");
        errorlable.setFont(new Font("Times New Roman", Font.BOLD, 22));
        errorlable.setForeground(Color.RED);
        errorlable.setBounds(0, currentY + 90, panelWidth, 40);
        errorlable.setHorizontalAlignment(SwingConstants.CENTER);

        // --- Login button action ---
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean auth = authenticateUser(username, password);

            if (auth) {

                boolean maintenance = isUnderMaintenance();

                String role = UserSession.role;

                //maintenance check
                if (maintenance && !role.equalsIgnoreCase("Admin")) {
                    switch (role) {
                        case "Student":
                            new mhomepage();
                            break;
                        case "Faculty":
                            new mfhomepage();
                            break;
                        default:
                            JOptionPane.showMessageDialog(this, "Unknown role: " + role);
                    }

                    return;
                }


                this.dispose();
                switch (role) {
                    case "Admin":
                        new ahomepage();
                        break;
                    case "Student":
                        new homepage();
                        break;
                    case "Faculty":
                        new fhomepage();
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Unknown role: " + role);
                }
            }
            else {
                errorlable.setText("Invalid username or password!");}

        });


        // --- Add everything ---
        this.setTitle("IIITD ERP");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(screenSize.width, screenSize.height);
        this.setIconImage(image.getImage());
        this.setContentPane(bg);

        bg.add(label);
        bg.add(Nframe);
        Nframe.add(userLabel);
        Nframe.add(usernameField);
        Nframe.add(passLabel);
        Nframe.add(passwordField);
        Nframe.add(loginBtn);
        Nframe.add(errorlable);

        this.setVisible(true);
    }

    // --- Authentication function ---
    private boolean authenticateUser(String username, String password) {

        try (Connection conn = database.getConnection()) {

            String query = "SELECT * FROM users_auth WHERE username = ? AND password_hash = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // SET GLOBAL SESSION
                UserSession.userId = rs.getInt("id");
                UserSession.username = rs.getString("username");
                UserSession.role = rs.getString("role");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean isUnderMaintenance() {
        try (Connection conn = database.getConnection()) {
            String sql = "SELECT is_maintenance FROM maintenance_settings WHERE id = 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("is_maintenance") == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static void main(String[] args) {
        new Frame();
    }



}

// --- Background panel class ---
class BackgroundPanel extends JPanel {
    private Image blurredBg;

    public BackgroundPanel(String path) {
        Image img = new ImageIcon(path).getImage();
        Image small = img.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        blurredBg = small.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(blurredBg, 0, 0, getWidth(), getHeight(), this);
    }



}
