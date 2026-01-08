package ERPSytem.Student;

import ERPSytem.Loginpage.Frame;
import ERPSytem.UserSession;
import ERPSytem.Databaseconnector.database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class registercourse extends JFrame {

    private int userId;

    public registercourse() {
        this.userId = UserSession.userId;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon image = new ImageIcon("/home/parv-jain/Documents/AP Project/src/ERPSytem/iiitd.png");
        Image scaled = image.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaled);





        JPanel upperpanel = new JPanel();
        upperpanel.setBackground(Color.white);
        upperpanel.setBounds(0, 0, screen.width, 150);
        upperpanel.setLayout(null);

        JButton hp = new JButton(image);
        hp.setBackground(Color.white);
        hp.setBounds(20, 40, 250, 100);
        hp.setBorderPainted(false);
        hp.setContentAreaFilled(false);
        hp.setFocusPainted(false);
        hp.addActionListener(e->{
            new homepage();
            this.dispose();
        });
        this.add(upperpanel);
        upperpanel.add(hp);

        // ---------- LEFT BLUE PANEL ----------
        JPanel sidepanel = new JPanel();
        sidepanel.setBackground(Color.BLUE);
        sidepanel.setBounds(0, 150, 500, screen.height-150);
        sidepanel.setLayout(null);

        JButton tt = new JButton();
        tt.setText("Time Table");
        tt.setBackground(Color.blue);
        tt.setBorderPainted(false);
        tt.setContentAreaFilled(false);
        tt.setFocusPainted(false);
        tt.setFont(new Font("Times New Roman", Font.BOLD, 40));
        tt.setForeground(Color.white); tt.setBackground(Color.white);
        tt.setBounds(0, 400, 500, 200);
        tt.addActionListener(e->{
            new timetable();
            this.dispose();
        });

        sidepanel.add(tt);
        JButton course = new JButton();
        course.setText("Course Catalog");
        course.setBackground(Color.blue);
        course.setBorderPainted(false);
        course.setContentAreaFilled(false);
        course.setFocusPainted(false);
        course.setFont(new Font("Times New Roman", Font.BOLD, 40));
        course.setForeground(Color.white);
        course.setBackground(Color.white);
        course.setBounds(0, 600, 500, 200);
        course.addActionListener(e->{
            new coursecatalog();
            this.dispose();
        });
        sidepanel.add(course);

        JButton rc = new JButton();
        rc.setText("Register for courses");
        rc.setBackground(Color.blue);
        rc.setBorderPainted(false);
        rc.setContentAreaFilled(false);
        rc.setFocusPainted(false);
        rc.setFont(new Font("Times New Roman", Font.BOLD, 40));
        rc.setForeground(Color.white);
        rc.setBackground(Color.white);
        rc.setBounds(0, 800, 500, 200);
        rc.addActionListener(e->{
            new registercourse();
            this.dispose();
        });
        sidepanel.add(rc);

        JButton fd = new JButton(); fd.setText("My Courses");
        fd.setBackground(Color.blue); fd.setBorderPainted(false);
        fd.setContentAreaFilled(false); fd.setFocusPainted(false);
        fd.setFont(new Font("Times New Roman", Font.BOLD, 40));
        fd.setForeground(Color.white);
        fd.setBackground(Color.white);
        fd.setBounds(0, 1000, 500, 200);
        sidepanel.add(fd);
        fd.addActionListener(e->{
            new myfaculty();
            this.dispose();
        });
        JButton g = new JButton();
        g.setText("Grades");
        g.setBackground(Color.blue);
        g.setBorderPainted(false);
        g.setContentAreaFilled(false);
        g.setFocusPainted(false);
        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g.setForeground(Color.white);
        g.setBackground(Color.white);
        g.setBounds(0, 1200, 500, 200);
        g.addActionListener(e -> {
            new mygrade();
            this.dispose();
        });
        sidepanel.add(g);
        JButton l = new JButton();
        l.setText("Log out");
        l.setBackground(Color.blue);
        l.setBorderPainted(false);
        l.setContentAreaFilled(false);
        l.setFocusPainted(false);
        l.setFont(new Font("Times New Roman", Font.BOLD, 40));
        l.setForeground(Color.white); tt.setBackground(Color.white);
        l.setBounds(0, 1400, 500, 200);
        l.addActionListener(e->{
            new Frame();
            this.dispose();
        });
        sidepanel.add(l);

        // ---------- MAIN PANEL ----------
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(null);
        int panelWidth = screen.width-500, panelHeight = 1500;
        int x = (screen.width+500 - panelWidth) / 2;
        int y = (screen.height - panelHeight) / 2;
        mainPanel.setBounds(x, y, panelWidth, panelHeight);

        JLabel title = new JLabel("Available Courses", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 40));
        title.setBounds(0, 20, mainPanel.getWidth(), 50);

        mainPanel.add(title);

        // ---------- TABLE STRUCTURE ----------
        String[] columns = {"Course Code", "Course Name", "Credits", "Faculty", "Action"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;  // Only "Action" column is editable (button)
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table.setRowHeight(45);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 100, mainPanel.getWidth() - 200, 1500);
        this.setTitle("Register Course");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(true);
        this.setSize(screen.width, screen.height);
        this.setVisible(true);

        mainPanel.add(scrollPane);

        // Load courses
        loadCourses(model, table);

        // ---------- ADD TO FRAME ----------
        add(sidepanel);
        add(mainPanel);
        setVisible(true);
    }

    // ---------- LOAD COURSE LIST ----------
    private void loadCourses(DefaultTableModel model, JTable table) {
        String sql = """
                SELECT c.course_id, c.course_code, c.course_name, 
                       c.credits, f.name AS faculty_name
                FROM courses c
                LEFT JOIN faculty f ON c.faculty_id = f.faculty_id
            """;

        try (Connection conn = database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int courseId = rs.getInt("course_id");

                // Add row with button placeholder
                model.addRow(new Object[]{
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                        rs.getString("faculty_name"),
                        "Register"
                });
            }

            // Create button functionality
            table.getColumn("Action").setCellRenderer(new ButtonRenderer());
            table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), userId));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading courses:\n" + ex.getMessage());
        }
    }
}

// ---------- RENDER BUTTON IN TABLE ----------
class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
        setText("Register");
        setBackground(Color.GREEN);
        setFont(new Font("Times New Roman", Font.BOLD, 18));
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        return this;
    }
}

// ---------- BUTTON EDITOR (HANDLE CLICK) ----------
class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean clicked;
    private int userId;
    private int selectedCourseId;

    public ButtonEditor(JCheckBox checkBox, int userId) {
        super(checkBox);
        this.userId = userId;

        button = new JButton("Register");
        button.setOpaque(true);
        button.setBackground(Color.GREEN);
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));

        button.addActionListener(e -> {
            clicked = true;
            fireEditingStopped();
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {

        label = "Register";
        button.setText(label);

        // Get course_id from hidden column or model index
        selectedCourseId = row + 1; // assuming sequential course_id (you can change)

        clicked = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (clicked) {
            registerCourse(userId, selectedCourseId);
        }
        clicked = false;
        return label;
    }

    private void registerCourse(int userId, int courseId) {
        try (Connection conn = database.getConnection()) {

            // Step 1: Get student_id
            PreparedStatement ps1 = conn.prepareStatement(
                    "SELECT student_id FROM students WHERE user_id = ?"
            );
            ps1.setInt(1, userId);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                JOptionPane.showMessageDialog(null, "Student record not found!");
                return;
            }

            int studentId = rs1.getInt("student_id");

            // Step 2: Check Max Courses = 5
            PreparedStatement psLimit = conn.prepareStatement(
                    "SELECT COUNT(*) AS total FROM enrollments WHERE student_id = ?"
            );
            psLimit.setInt(1, studentId);
            ResultSet rsLimit = psLimit.executeQuery();
            rsLimit.next();

            if (rsLimit.getInt("total") >= 5) {
                JOptionPane.showMessageDialog(null,
                        "You cannot register for more than 5 courses!",
                        "LIMIT REACHED",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // -----------------------------
            // STEP 3: CHECK SEAT CAPACITY
            // -----------------------------
            PreparedStatement psCap = conn.prepareStatement(
                    "SELECT capacity, enrolled FROM courses WHERE course_id = ?"
            );
            psCap.setInt(1, courseId);
            ResultSet rsCap = psCap.executeQuery();
            rsCap.next();

            int capacity = rsCap.getInt("capacity");
            int enrolled = rsCap.getInt("enrolled");

            if (enrolled >= capacity) {
                JOptionPane.showMessageDialog(null,
                        "No seats available! Course is full.",
                        "FULL",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Step 4: Check duplicate registration
            PreparedStatement ps2 = conn.prepareStatement(
                    "SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?"
            );
            ps2.setInt(1, studentId);
            ps2.setInt(2, courseId);
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                JOptionPane.showMessageDialog(null,
                        "Already registered!");
                return;
            }

            // Step 5: Register course
            PreparedStatement ps3 = conn.prepareStatement(
                    "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)"
            );
            ps3.setInt(1, studentId);
            ps3.setInt(2, courseId);
            ps3.executeUpdate();

            // Step 6: Increase enrolled count
            PreparedStatement ps4 = conn.prepareStatement(
                    "UPDATE courses SET enrolled = enrolled + 1 WHERE course_id = ?"
            );
            ps4.setInt(1, courseId);
            ps4.executeUpdate();

            JOptionPane.showMessageDialog(null,
                    "Course Registered Successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage());
        }
    }



}
