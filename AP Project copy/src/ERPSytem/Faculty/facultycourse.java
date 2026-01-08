package ERPSytem.Faculty;

import ERPSytem.Loginpage.Frame;
import ERPSytem.UserSession;
import ERPSytem.Databaseconnector.database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class facultycourse extends JFrame {
    private int userId;  //store who is logged in
    private JPanel Nframe;


    facultycourse() {
        this.userId = UserSession.userId;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel upperpanel = new JPanel();
        upperpanel.setBackground(Color.white);
        upperpanel.setBounds(0, 0, screenSize.width, 150);
        upperpanel.setLayout(null);

        ImageIcon image = new ImageIcon("/home/parv-jain/Documents/AP Project/src/ERPSytem/iiitd.png");
        Image scaled = image.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaled);

        JPanel sidepanel = new JPanel();
        sidepanel.setBackground(Color.BLUE);
        sidepanel.setBounds(0, 150, 500, screenSize.height - 150);
        sidepanel.setLayout(null);

        JButton hp = new JButton(image);
        hp.setBackground(Color.white);
        hp.setBounds(20, 40, 250, 100);
        hp.setBorderPainted(false);
        hp.setContentAreaFilled(false);
        hp.setFocusPainted(false);
        hp.addActionListener(e->{
            this.dispose();
            new fhomepage();
        });
        // Create center area
        Nframe = new JPanel();
        Nframe.setLayout(null);
        Nframe.setBackground(Color.WHITE);
        int panelWidth = screenSize.width-500, panelHeight = 1500;
        int x = (screenSize.width+500 - panelWidth) / 2;
        int y = (screenSize.height - panelHeight) / 2;
        Nframe.setBounds(x, y, panelWidth, panelHeight);
        Nframe.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));


        JButton tt = new JButton();
        tt.setText("My Courses");
        tt.setBackground(Color.blue);
        tt.setBorderPainted(false);
        tt.setContentAreaFilled(false);
        tt.setFocusPainted(false);
        tt.setFont(new Font("Times New Roman", Font.BOLD, 40));
        tt.setForeground(Color.white); tt.setBackground(Color.white);
        tt.setBounds(0, 400, 500, 200);
        tt.addActionListener(e->{
            new facultycourse();
            this.dispose();
        });
        JButton course = new JButton();
        course.setText("Class Stats");
        course.setBackground(Color.blue);
        course.setBorderPainted(false);
        course.setContentAreaFilled(false);
        course.setFocusPainted(false);
        course.setFont(new Font("Times New Roman", Font.BOLD, 40));
        course.setForeground(Color.white);
        course.setBackground(Color.white);
        course.setBounds(0, 600, 500, 200);
        course.addActionListener(e->{
            new ClassStats();
            this.dispose();
        });

        JButton g = new JButton();
        g.setText("Add Grades");
        g.setBackground(Color.blue);
        g.setBorderPainted(false);
        g.setContentAreaFilled(false);
        g.setFocusPainted(false);
        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g.setForeground(Color.white);
        g.setBackground(Color.white);
        g.setBounds(0, 800, 500, 200);
        g.addActionListener(e->{
            new Addgrade();
            this.dispose();
        });
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

        // --- Add everything ---
        this.setTitle("Faculty");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(true);
        this.setSize(screenSize.width, screenSize.height);
        this.setVisible(true);

        upperpanel.add(hp);
        this.add(upperpanel);
        this.add(Nframe);
        sidepanel.add(course);
        sidepanel.add(tt);
        sidepanel.add(g);


        this.add(sidepanel);
        loadCourseCatalog();
    }
    private void loadCourseCatalog() {

        Nframe.removeAll();  // clear previous content
        Nframe.repaint();
        Nframe.revalidate();

        JLabel title = new JLabel("My Courses", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 36));
        title.setBounds(0, 20, Nframe.getWidth(), 50);
        Nframe.add(title);

        String[] columns = {"Course Code", "Course Name", "Credits"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table.setRowHeight(45);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 100, Nframe.getWidth() - 200, 1500);

        Nframe.add(scrollPane);

        // Load from DB
        String sql = """
    SELECT c.course_code, c.course_name, c.credits
    FROM courses c
    JOIN faculty f ON c.faculty_id = f.faculty_id
    WHERE f.user_id = ?
""";



        try (Connection conn = database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading courses:\n" + e.getMessage());
        }

        Nframe.repaint();
        Nframe.revalidate();
    }


}
