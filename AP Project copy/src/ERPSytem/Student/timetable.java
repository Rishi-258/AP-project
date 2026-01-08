package ERPSytem.Student;

import ERPSytem.Loginpage.Frame;
import ERPSytem.UserSession;
import ERPSytem.Databaseconnector.database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class timetable extends JFrame {

    int userId;
    JPanel Nframe;

    public timetable() {

        this.userId = UserSession.userId;

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        // ---------------- TOP PANEL ----------------
        JPanel upperpanel = new JPanel();
        upperpanel.setBackground(Color.white);
        upperpanel.setBounds(0, 0, screen.width, 150);
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
            new homepage();
            this.dispose();
        });
        upperpanel.add(hp);

        // ---------------- LEFT BLUE PANEL ----------------
        JPanel sidepanel = new JPanel();
        sidepanel.setBackground(Color.BLUE);
        sidepanel.setBounds(0, 150, 500, screen.height - 150);
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
        JButton fd = new JButton();
        fd.setText("My Courses");
        fd.setBackground(Color.blue);
        fd.setBorderPainted(false);
        fd.setContentAreaFilled(false);
        fd.setFocusPainted(false);
        fd.setFont(new Font("Times New Roman", Font.BOLD, 40));
        fd.setForeground(Color.white);
        fd.setBackground(Color.white);
        fd.setBounds(0, 1000, 500, 200);
        fd.addActionListener(e->{
            new myfaculty();
            this.dispose();
        });
        sidepanel.add(fd);
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
        g.addActionListener(e->{
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

        // ---------------- MAIN PANEL ----------------
        Nframe = new JPanel();
        Nframe.setLayout(null);
        Nframe.setBackground(Color.WHITE);
        Nframe.setBounds(500, 150, screen.width - 500, screen.height - 150);

        JLabel title = new JLabel("My Time Table", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 50));
        title.setBounds(0, 30, Nframe.getWidth(), 70);
        Nframe.add(title);

        // ---------------- TABLE ----------------
        String[] columns = {"Day", "Course Code", "Course Name", "Start Time", "End Time", "Room"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        table.setRowHeight(70);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 150, Nframe.getWidth() - 200, 1000);
        Nframe.add(scroll);

        loadTimeTable(model);

        // ---------------- FRAME SETTINGS ----------------
        this.setTitle("Time Table");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(true);
        this.setSize(screen.width, screen.height);
        this.setVisible(true);

        add(upperpanel);
        add(sidepanel);
        add(Nframe);
    }

    private void loadTimeTable(DefaultTableModel model) {

        String sql = """
                SELECT 
                    cs.day,
                    c.course_code,
                    c.course_name,
                    cs.start_time,
                    cs.end_time,
                    cs.room
                FROM enrollments e
                JOIN students s ON e.student_id = s.student_id
                JOIN courses_schedule cs ON e.course_id = cs.course_id
                JOIN courses c ON c.course_id = cs.course_id
                WHERE s.user_id = ?
                ORDER BY 
                    FIELD(cs.day, 'Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'),
                    cs.start_time;
        """;

        try (Connection conn = database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("day"),
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("room")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading timetable:\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new timetable();
    }
}

