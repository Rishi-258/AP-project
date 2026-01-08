package ERPSytem.Faculty;

import ERPSytem.Loginpage.Frame;
import ERPSytem.UserSession;
import ERPSytem.Databaseconnector.database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ClassStats extends JFrame {
    private int userId;
    private JPanel Nframe;

    public ClassStats() {

        this.userId = UserSession.userId;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // ---------- TOP PANEL ----------
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
            new fhomepage();
            this.dispose();
        });

        upperpanel.add(hp);

        // ---------- LEFT PANEL ----------
        JPanel sidepanel = new JPanel();
        sidepanel.setBackground(Color.BLUE);
        sidepanel.setBounds(0, 150, 500, screenSize.height - 150);
        sidepanel.setLayout(null);

        JButton btn1 = new JButton("My Courses");
        btn1.setBounds(0, 400, 500, 200);
        btn1.setForeground(Color.WHITE);
        btn1.setBorderPainted(false);
        btn1.setContentAreaFilled(false);
        btn1.setFocusPainted(false);
        btn1.setFont(new Font("Times New Roman", Font.BOLD, 40));
        btn1.addActionListener(e -> {
            new facultycourse();
            this.dispose();
        });
        sidepanel.add(btn1);

        JButton btn2 = new JButton("Class stats");
        btn2.setBounds(0, 600, 500, 200);
        btn2.setForeground(Color.WHITE);
        btn2.setBorderPainted(false);
        btn2.setContentAreaFilled(false);
        btn2.setFocusPainted(false);
        btn2.setFont(new Font("Times New Roman", Font.BOLD, 40));
        sidepanel.add(btn2);
        btn2.addActionListener(e->{
            new ClassStats();
            this.dispose();
        });

        JButton btn3 = new JButton("Add Grades");
        btn3.setBounds(0, 800, 500, 200);
        btn3.setForeground(Color.WHITE);
        btn3.setBorderPainted(false);
        btn3.setContentAreaFilled(false);
        btn3.setFocusPainted(false);
        btn3.setFont(new Font("Times New Roman", Font.BOLD, 40));
        btn3.addActionListener(e -> {
            new Addgrade();
            this.dispose();
        });
        sidepanel.add(btn3);


        JButton logout = new JButton("Log out");
        logout.setBounds(0, 1400, 500, 200);
        logout.setForeground(Color.WHITE);
        logout.setBorderPainted(false);
        logout.setContentAreaFilled(false);
        logout.setFocusPainted(false);
        logout.setFont(new Font("Times New Roman", Font.BOLD, 40));
        logout.addActionListener(e -> {
            new Frame();
            this.dispose();
        });
        sidepanel.add(logout);

        // ---------- CENTER PANEL ----------
        Nframe = new JPanel();
        Nframe.setLayout(null);
        Nframe.setBackground(Color.WHITE);
        int panelWidth = 1500, panelHeight = 1500;
        int x = (screenSize.width - panelWidth) / 2;
        int y = (screenSize.height - panelHeight) / 2;
        Nframe.setBounds(x, y, panelWidth, panelHeight);
        Nframe.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Title
        JLabel title = new JLabel("Average Class Statistics", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 60));
        title.setBounds(0, 50, panelWidth, 80);
        Nframe.add(title);

        // Create table model
        String[] columns = {
                "Course Code", "Course Name",
                "Avg Quiz", "Avg Midterm",
                "Avg Endsem", "Avg Assignment",
                "Overall Avg"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        table.setRowHeight(40);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 200, 1400, 1200);
        Nframe.add(scrollPane);

        loadClassStats(model);

        // ---------- FRAME SETTINGS ----------
        setTitle("Class Statistics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(screenSize.width, screenSize.height);
        setVisible(true);

        add(upperpanel);
        add(sidepanel);
        add(Nframe);
    }

    private void loadClassStats(DefaultTableModel model) {

        String sql = """
            SELECT 
                c.course_code,
                c.course_name,
                AVG(g.quiz_marks) AS avg_quiz,
                AVG(g.midterm_marks) AS avg_mid,
                AVG(g.endsem_marks) AS avg_end,
                AVG(g.assignment_marks) AS avg_assign,
                AVG(g.total_marks) AS avg_total
            FROM grades g
            JOIN courses c ON g.course_id = c.course_id
            WHERE c.faculty_id = (
                SELECT faculty_id FROM faculty WHERE user_id = ?
            )
            GROUP BY c.course_id;
        """;

        try (Connection conn = database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getDouble("avg_quiz"),
                        rs.getDouble("avg_mid"),
                        rs.getDouble("avg_end"),
                        rs.getDouble("avg_assign"),
                        rs.getDouble("avg_total")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading class stats:\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ClassStats();
    }
}
