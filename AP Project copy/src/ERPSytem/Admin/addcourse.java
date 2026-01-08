package ERPSytem.Admin;

import ERPSytem.Databaseconnector.database;
import ERPSytem.Loginpage.Frame;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class addcourse extends JFrame {

    private JPanel Nframe;
    private JComboBox<String> facultyBox;

    public addcourse() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        JPanel upperpanel = new JPanel();
        upperpanel.setBackground(Color.white);
        upperpanel.setBounds(0, 0, screenSize.width, 150);
        upperpanel.setLayout(null);

        ImageIcon image = new ImageIcon("/home/parv-jain/Documents/AP Project/src/ERPSytem/iiitd.png");
        Image scaled = image.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaled);

        JButton hp = new JButton(image);
        hp.setBounds(20, 40, 250, 100);
        hp.setBorderPainted(false);
        hp.setFocusPainted(false);
        hp.setContentAreaFilled(false);
        hp.addActionListener(e -> {
            new ahomepage();
            this.dispose();
        });
        upperpanel.add(hp);

        // ---------- LEFT BLUE PANEL ----------
        JPanel sidepanel = new JPanel();
        sidepanel.setBackground(Color.BLUE);
        sidepanel.setBounds(0, 150, 500, screenSize.height - 150);
        sidepanel.setLayout(null);

        JButton btn1 = new JButton("Maintenance");
        btn1.setBounds(0, 400, 500, 200);
        btn1.setForeground(Color.WHITE);
        btn1.setContentAreaFilled(false);
        btn1.setBorderPainted(false);
        btn1.setFocusPainted(false);
        btn1.setFont(new Font("Times New Roman", Font.BOLD, 40));
        btn1.addActionListener(e -> {
            new Maintenance();
            this.dispose();
        });
        sidepanel.add(btn1);

        JButton btn2 = new JButton("Add Users");
        btn2.setBounds(0, 600, 500, 200);
        btn2.setForeground(Color.WHITE);
        btn2.setContentAreaFilled(false);
        btn2.setBorderPainted(false);
        btn2.setFocusPainted(false);
        btn2.setFont(new Font("Times New Roman", Font.BOLD, 40));
        btn2.addActionListener(e -> {
            new adduser();
            this.dispose();
        });
        sidepanel.add(btn2);

        JButton btn3 = new JButton("Section Allotment");
        btn3.setBounds(0, 800, 500, 200);
        btn3.setForeground(Color.WHITE);
        btn3.setContentAreaFilled(false);
        btn3.setBorderPainted(false);
        btn3.setFocusPainted(false);
        btn3.setFont(new Font("Times New Roman", Font.BOLD, 40));
        btn3.addActionListener(e -> {
            new sectionallotment();
            this.dispose();
        });
        sidepanel.add(btn3);

        JButton btn4 = new JButton("Add Courses");
        btn4.setBounds(0, 1000, 500, 200);
        btn4.setForeground(Color.WHITE);
        btn4.setContentAreaFilled(false);
        btn4.setBorderPainted(false);
        btn4.setFocusPainted(false);
        btn4.setFont(new Font("Times New Roman", Font.BOLD, 40));
        btn4.addActionListener(e -> {
            new addcourse();
            this.dispose();
        });
        sidepanel.add(btn4);

        JButton logout = new JButton("Log Out");
        logout.setBounds(0, 1200, 500, 200);
        logout.setForeground(Color.WHITE);
        logout.setContentAreaFilled(false);
        logout.setBorderPainted(false);
        logout.setFocusPainted(false);
        logout.setFont(new Font("Times New Roman", Font.BOLD, 40));
        logout.addActionListener(e -> {
            new Frame();
            this.dispose();
        });
        sidepanel.add(logout);

        // ---------- CENTER PANEL ----------
        Nframe = new JPanel();
        Nframe.setBackground(Color.WHITE);
        Nframe.setLayout(null);

        int panelWidth = 1500, panelHeight = 1500;
        int x = (screenSize.width - panelWidth) / 2;
        int y = (screenSize.height - panelHeight) / 2;

        Nframe.setBounds(x, y, panelWidth, panelHeight);
        Nframe.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // ---------- INPUT FIELDS ----------
        JLabel title = new JLabel("Add New Course", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 70));
        title.setBounds(0, 100, 1500, 80);
        Nframe.add(title);

        JLabel codeLabel = new JLabel("Course Code:");
        codeLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        codeLabel.setBounds(200, 300, 400, 60);
        Nframe.add(codeLabel);

        JTextField codeField = new JTextField();
        codeField.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        codeField.setBounds(700, 300, 500, 60);
        Nframe.add(codeField);

        JLabel nameLabel = new JLabel("Course Name:");
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        nameLabel.setBounds(200, 400, 400, 60);
        Nframe.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        nameField.setBounds(700, 400, 500, 60);
        Nframe.add(nameField);

        JLabel creditsLabel = new JLabel("Credits:");
        creditsLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        creditsLabel.setBounds(200, 500, 400, 60);
        Nframe.add(creditsLabel);

        JTextField creditsField = new JTextField();
        creditsField.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        creditsField.setBounds(700, 500, 500, 60);
        Nframe.add(creditsField);

        JLabel capacityLabel = new JLabel("Capacity:");
        capacityLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        capacityLabel.setBounds(200, 600, 400, 60);
        Nframe.add(capacityLabel);

        JTextField capacityField = new JTextField();
        capacityField.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        capacityField.setBounds(700, 600, 500, 60);
        Nframe.add(capacityField);

        // ---------- FACULTY DROPDOWN ----------
        JLabel facultyLabel = new JLabel("Faculty:");
        facultyLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        facultyLabel.setBounds(200, 700, 400, 60);
        Nframe.add(facultyLabel);

        facultyBox = new JComboBox<>();
        facultyBox.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        facultyBox.setBounds(700, 700, 500, 60);
        facultyBox.setBackground(Color.WHITE);
        Nframe.add(facultyBox);

        loadFacultyNames(); // <--- Load names from DB

        // ---------- ADD button ----------
        JButton addBtn = new JButton("Add Course");
        addBtn.setFont(new Font("Times New Roman", Font.BOLD, 50));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(Color.BLUE);
        addBtn.setBounds(500, 850, 500, 80);

        addBtn.addActionListener(e -> {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            String credits = creditsField.getText().trim();
            String capacity = capacityField.getText().trim();
            String faculty = facultyBox.getSelectedItem().toString();

            if (code.isEmpty() || name.isEmpty() || credits.isEmpty() || capacity.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            addCourse(code, name, credits, capacity, faculty);
        });

        Nframe.add(addBtn);

        // ---------- FRAME ----------
        setTitle("Add Course");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(screenSize.width, screenSize.height);
        setVisible(true);

        add(upperpanel);
        add(sidepanel);
        add(Nframe);
    }

    // ---------- LOAD FACULTY ----------
    private void loadFacultyNames() {
        facultyBox.addItem("None");
        try (Connection conn = database.getConnection()) {
            String sql = "SELECT name FROM faculty";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                facultyBox.addItem(rs.getString("name"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading faculty:\n" + e.getMessage());
        }
    }

    // ---------- GET faculty_id ----------
    private Integer getFacultyId(String name) {
        if (name.equals("None")) return null;

        try (Connection conn = database.getConnection()) {
            String sql = "SELECT faculty_id FROM faculty WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("faculty_id");
        } catch (Exception ignored) {}
        return null;
    }

    // ---------- ADD COURSE INTO DATABASE ----------
    private void addCourse(String code, String name, String credits, String capacity, String facultyName) {

        try (Connection conn = database.getConnection()) {

            String sql = """
                INSERT INTO courses (course_code, course_name, credits, capacity, enrolled, faculty_id)
                VALUES (?, ?, ?, ?, 0, ?)
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            ps.setString(2, name);
            ps.setInt(3, Integer.parseInt(credits));
            ps.setInt(4, Integer.parseInt(capacity));

            Integer fid = getFacultyId(facultyName);
            if (fid == null) ps.setNull(5, java.sql.Types.INTEGER);
            else ps.setInt(5, fid);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Course Added Successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error adding course:\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new addcourse();
    }
}
