package ERPSytem.Student;

import ERPSytem.Databaseconnector.database;
import ERPSytem.Loginpage.Frame;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class coursecatalog extends JFrame {

        private JPanel Nframe;
        coursecatalog() {

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
                new homepage();
                this.dispose();
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

            // Load and display student details();

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
            JButton fd = new JButton(); fd.setText("My Courses");
            fd.setBackground(Color.blue); fd.setBorderPainted(false);
            fd.setContentAreaFilled(false); fd.setFocusPainted(false);
            fd.setFont(new Font("Times New Roman", Font.BOLD, 40));
            fd.setForeground(Color.white);
            fd.setBackground(Color.white);
            fd.setBounds(0, 1000, 500, 200);
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
            this.setTitle("Homepage");
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
            sidepanel.add(fd);
            sidepanel.add(rc);
            this.add(sidepanel);
            loadCourseCatalog();
        }

    private void loadCourseCatalog() {

        Nframe.removeAll();  // clear previous content
        Nframe.repaint();
        Nframe.revalidate();

        JLabel title = new JLabel("Course Catalog", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 36));
        title.setBounds(0, 20, Nframe.getWidth(), 50);
        Nframe.add(title);

        String[] columns = {"Course Code", "Course Name", "Credits", "Faculty"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table.setRowHeight(45);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 100, Nframe.getWidth() - 200, 1500);

        Nframe.add(scrollPane);

        // Load from DB
        String sql = """
        SELECT c.course_code, c.course_name, c.credits, f.name AS faculty_name
        FROM courses c
        LEFT JOIN faculty f ON c.faculty_id = f.faculty_id
    """;

        try (Connection conn = database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                        rs.getString("faculty_name")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading courses:\n" + e.getMessage());
        }

        Nframe.repaint();
        Nframe.revalidate();
    }


}
