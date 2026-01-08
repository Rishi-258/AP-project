
package ERPSytem.Student;

import ERPSytem.Loginpage.Frame;
import ERPSytem.UserSession;
import ERPSytem.Databaseconnector.database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class mygrade extends JFrame {

    private int userId;
    private JPanel Nframe;

    public mygrade() {

        this.userId = UserSession.userId;  // student user ID

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // ---------- TOP WHITE PANEL ----------
        JPanel upperpanel = new JPanel();
        upperpanel.setBackground(Color.white);
        upperpanel.setBounds(0, 0, screenSize.width, 150);
        upperpanel.setLayout(null);

        ImageIcon image = new ImageIcon("/home/parv-jain/Documents/AP Project/src/ERPSytem/iiitd.png");
        Image scaled = image.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaled);

        JButton hp = new JButton(image);
        hp.setBackground(Color.white);
        hp.setBounds(20, 40, 250, 100);
        hp.setBorderPainted(false);
        hp.setContentAreaFilled(false);
        hp.setFocusPainted(false);
        hp.addActionListener(e -> {
            new homepage();
            this.dispose();
        });
        upperpanel.add(hp);

        // ---------- LEFT BLUE PANEL ----------
        JPanel sidepanel = new JPanel();
        sidepanel.setBackground(Color.BLUE);
        sidepanel.setBounds(0, 150, 500, screenSize.height - 150);
        sidepanel.setLayout(null);

        JButton tt = new JButton("Time Table");
        tt.setBackground(Color.blue);
        tt.setBorderPainted(false);
        tt.setContentAreaFilled(false);
        tt.setFocusPainted(false);
        tt.setFont(new Font("Times New Roman", Font.BOLD, 40));
        tt.setForeground(Color.white);
        tt.setBounds(0, 400, 500, 200);
        sidepanel.add(tt);
        tt.addActionListener(e->{
            new timetable();
            this.dispose();
        });

        JButton course = new JButton("Course Catalog");
        course.setBackground(Color.blue);
        course.setBorderPainted(false);
        course.setContentAreaFilled(false);
        course.setFocusPainted(false);
        course.setFont(new Font("Times New Roman", Font.BOLD, 40));
        course.setForeground(Color.white);
        course.setBounds(0, 600, 500, 200);
        course.addActionListener(e -> {
            new coursecatalog();
            this.dispose();
        });
        sidepanel.add(course);

        JButton rc = new JButton("Register for courses");
        rc.setBackground(Color.blue);
        rc.setBorderPainted(false);
        rc.setContentAreaFilled(false);
        rc.setFocusPainted(false);
        rc.setFont(new Font("Times New Roman", Font.BOLD, 40));
        rc.setForeground(Color.white);
        rc.setBounds(0, 800, 500, 200);
        rc.addActionListener(e -> {
            new registercourse();
            this.dispose();
        });
        sidepanel.add(rc);

        JButton fd = new JButton("My Courses");
        fd.setBackground(Color.blue);
        fd.setBorderPainted(false);
        fd.setContentAreaFilled(false);
        fd.setFocusPainted(false);
        fd.setFont(new Font("Times New Roman", Font.BOLD, 40));
        fd.setForeground(Color.white);
        fd.setBounds(0, 1000, 500, 200);
        fd.addActionListener(e -> {
            new myfaculty();
            this.dispose();
        });
        sidepanel.add(fd);

        JButton g = new JButton("Grades");
        g.setBackground(Color.blue);
        g.setBorderPainted(false);
        g.setContentAreaFilled(false);
        g.setFocusPainted(false);
        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g.setForeground(Color.white);
        g.setBounds(0, 1200, 500, 200);
        sidepanel.add(g);
        g.addActionListener(e -> {
            new mygrade();
            this.dispose();
        });

        JButton l = new JButton("Log out");
        l.setBackground(Color.blue);
        l.setBorderPainted(false);
        l.setContentAreaFilled(false);
        l.setFocusPainted(false);
        l.setFont(new Font("Times New Roman", Font.BOLD, 40));
        l.setForeground(Color.white);
        l.setBounds(0, 1400, 500, 200);
        l.addActionListener(e -> {
            new Frame();
            this.dispose();
        });
        sidepanel.add(l);

        // ---------- CENTER WHITE PANEL ----------
        Nframe = new JPanel();
        Nframe.setBackground(Color.WHITE);
        Nframe.setLayout(null);
        int panelWidth = screenSize.width - 500, panelHeight = 1500;
        int x = (screenSize.width + 500 - panelWidth) / 2;
        int y = (screenSize.height - panelHeight) / 2;
        Nframe.setBounds(x, y, panelWidth, panelHeight);

        JLabel title = new JLabel("My Grades", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 40));
        title.setBounds(0, 20, Nframe.getWidth(), 50);
        Nframe.add(title);

        // ---------- TABLE STRUCTURE ----------
        String[] columns = {"Course Code", "Course Name", "Quiz", "Midterm", "EndSem", "Assignment", "Total", "Grade"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        table.setRowHeight(50);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 100, Nframe.getWidth() - 200, 1400);
        Nframe.add(scrollPane);

        loadGrades(model);

        // ---------- FRAME ----------
        setTitle("My Grades");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(screenSize.width, screenSize.height);
        setResizable(true);
        setVisible(true);

        add(upperpanel);
        add(sidepanel);
        add(Nframe);
    }

    private void loadGrades(DefaultTableModel model) {

        String sql = """
                SELECT c.course_code, c.course_name,
                       g.quiz_marks, g.midterm_marks, g.endsem_marks,
                       g.assignment_marks, g.total_marks, g.letter_grade
                FROM grades g
                JOIN courses c ON g.course_id = c.course_id
                JOIN students s ON g.student_id = s.student_id
                WHERE s.user_id = ?
            """;

        try (Connection conn = database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getInt("quiz_marks"),
                        rs.getInt("midterm_marks"),
                        rs.getInt("endsem_marks"),
                        rs.getInt("assignment_marks"),
                        rs.getInt("total_marks"),
                        rs.getString("letter_grade")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading grades:\n" + e.getMessage());
        }
    }
}

