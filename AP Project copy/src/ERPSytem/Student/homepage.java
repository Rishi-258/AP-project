package ERPSytem.Student;

import ERPSytem.*;
import ERPSytem.Databaseconnector.database;
import ERPSytem.Loginpage.Frame;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class homepage extends JFrame {
    private int userId;  //store who is logged in
    private JPanel Nframe;

    public homepage() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.userId = UserSession.userId;
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
        int panelWidth = 800, panelHeight = 900;
        int x = (screenSize.width - panelWidth) / 2;
        int y = (screenSize.height - panelHeight) / 2;
        Nframe.setBounds(x, y, panelWidth, panelHeight);
        Nframe.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Load and display student details
        showStudentDetails();

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
    }

    // fetch and show student details
    private void showStudentDetails() {
        try (Connection conn = database.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!");
                return;
            }

            String query = """
                SELECT s.roll_no, s.name, s.program, s.year, s.email, s.phone
                FROM students s
                WHERE s.user_id = ?
            """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JLabel nameLabel = new JLabel("Name: " + rs.getString("name"));
                nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
                nameLabel.setBounds(100, 100, 600, 40);

                JLabel rollLabel = new JLabel("Roll No: " + rs.getString("roll_no"));
                rollLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                rollLabel.setBounds(100, 160, 600, 40);

                JLabel programLabel = new JLabel("Program: " + rs.getString("program"));
                programLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                programLabel.setBounds(100, 220, 600, 40);

                JLabel yearLabel = new JLabel("Year: " + rs.getInt("year"));
                yearLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                yearLabel.setBounds(100, 280, 600, 40);

                JLabel emailLabel = new JLabel("Email: " + rs.getString("email"));
                emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                emailLabel.setBounds(100, 340, 600, 40);

                JLabel phoneLabel = new JLabel("Phone: " + rs.getString("phone"));
                phoneLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                phoneLabel.setBounds(100, 400, 600, 40);

                Nframe.add(nameLabel);
                Nframe.add(rollLabel);
                Nframe.add(programLabel);
                Nframe.add(yearLabel);
                Nframe.add(emailLabel);
                Nframe.add(phoneLabel);
            } else {
                JLabel notFound = new JLabel("No student record found for user ID: " + userId);
                notFound.setFont(new Font("Times New Roman", Font.BOLD, 22));
                notFound.setBounds(100, 100, 600, 50);
                Nframe.add(notFound);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching student details: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        new homepage();
    }
}
