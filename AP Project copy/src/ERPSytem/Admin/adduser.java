package ERPSytem.Admin;

import ERPSytem.Loginpage.Frame;
import ERPSytem.Databaseconnector.database;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class adduser extends JFrame{
    private JPanel Nframe;
    private String username;
    private String pass;
    private JComboBox role;

    adduser() {

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
            new ahomepage();
            this.dispose();
        });
        // Create center area
        Nframe = new JPanel();
        Nframe.setLayout(null);
        Nframe.setBackground(Color.WHITE);
        int panelWidth = 1500, panelHeight = 1500;
        int x = (screenSize.width - panelWidth) / 2;
        int y = (screenSize.height - panelHeight) / 2;
        Nframe.setBounds(x, y, panelWidth, panelHeight);
        Nframe.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));


        // Load and display student details();

        JButton tt = new JButton();
        tt.setText("Maintenance");
        tt.setBackground(Color.blue);
        tt.setBorderPainted(false);
        tt.setContentAreaFilled(false);
        tt.setFocusPainted(false);
        tt.setFont(new Font("Times New Roman", Font.BOLD, 40));
        tt.setForeground(Color.white); tt.setBackground(Color.white);
        tt.setBounds(0, 400, 500, 200);
        tt.addActionListener(e->{
            new Maintenance();
            this.dispose();
        });
        JButton course = new JButton();
        course.setText("Add Users");
        course.setBackground(Color.blue);
        course.setBorderPainted(false);
        course.setContentAreaFilled(false);
        course.setFocusPainted(false);
        course.setFont(new Font("Times New Roman", Font.BOLD, 40));
        course.setForeground(Color.white);
        course.setBackground(Color.white);
        course.setBounds(0, 600, 500, 200);
        course.addActionListener(e->{
            new adduser();
            this.dispose();
        });
        JButton rc = new JButton();
        rc.setText("Section Allotment");
        rc.setBackground(Color.blue);
        rc.setBorderPainted(false);
        rc.setContentAreaFilled(false);
        rc.setFocusPainted(false);
        rc.setFont(new Font("Times New Roman", Font.BOLD, 40));
        rc.setForeground(Color.white);
        rc.setBackground(Color.white);
        rc.setBounds(0, 800, 500, 200);
        rc.addActionListener(e->{
            new sectionallotment();
            this.dispose();
        });
        JButton fd = new JButton();
        fd.setText("Add course");
        fd.setBackground(Color.blue);
        fd.setBorderPainted(false);
        fd.setContentAreaFilled(false);
        fd.setFocusPainted(false);
        fd.setFont(new Font("Times New Roman", Font.BOLD, 40));
        fd.setForeground(Color.white);
        fd.setBackground(Color.white);
        fd.setBounds(0, 1000, 500, 200);
        fd.addActionListener(e->{
            new addcourse();
            this.dispose();
        });
        JButton g = new JButton();
        g.setText("Log Out");
        g.setBackground(Color.blue);
        g.setBorderPainted(false);
        g.setContentAreaFilled(false);
        g.setFocusPainted(false);
        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g.setForeground(Color.white);
        g.setBackground(Color.white);
        g.setBounds(0, 1200, 500, 200);
        g.addActionListener(e->{
            new Frame();
            this.dispose();
        });
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Times New Roman", Font.BOLD, 60));
        userLabel.setBounds(200, 400, 500, 60);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(700, 400, 500, 60);
        usernameField.setFont(new Font("Times New Roman", Font.PLAIN,50 ));
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Times New Roman", Font.BOLD, 60));
        passLabel.setBounds(200, 500, 500, 60);

        JTextField password = new JTextField();
        password.setBounds(700, 500, 500, 60);
        password.setFont(new Font("Times New Roman", Font.PLAIN,50 ));
        password.setBorder(BorderFactory.createLineBorder(Color.GRAY));



        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Times New Roman", Font.BOLD, 60));
        roleLabel.setBounds(200, 600, 500, 60);

        String[] user={"Faculty","Student"};
        role = new JComboBox(user);
        role.setBackground(Color.white);
        role.setBounds(700, 600, 500, 60);
        role.setFont(new Font("Times New Roman", Font.PLAIN, 50));


        JButton adduser = new JButton("Add User");
        adduser.setBorderPainted(false);
        adduser.setFocusPainted(false);
        adduser.setBackground(Color.BLUE);
        adduser.setFont(new Font("Times New Roman", Font.BOLD, 40));
        adduser.setForeground(Color.white);
        adduser.setBounds(500, 800, 500, 60);
        adduser.addActionListener(e -> {

            String u = usernameField.getText();
            String p = password.getText();
            String r = role.getSelectedItem().toString();

            int id = add(u, p, r);

            if(id != -1){
                if(r.equals("Faculty")) new addFaculty(id);
                if(r.equals("Student")) new addStudent(id);

            }
        });

        // --- Add everything ---
        this.setTitle("Add Users");
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
        Nframe.add(usernameField);
        Nframe.add(userLabel);
        Nframe.add(passLabel);
        Nframe.add(password);
        Nframe.add(role);
        Nframe.add(roleLabel);
        Nframe.add(adduser);


        this.add(sidepanel);
    }
    public int add(String username, String pass, String role){
        try (Connection conn = database.getConnection()) {

            String query = "INSERT INTO users_auth (username, password_hash, role) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, username);
            ps.setString(2, pass);
            ps.setString(3, role);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "User Added Successfully!");

            ResultSet rs = ps.getGeneratedKeys();

            int newUserId = -1;
            if (rs.next()) newUserId = rs.getInt(1);

            return newUserId;

        } catch(Exception e){
            JOptionPane.showMessageDialog(this,
                    "Error Adding User: " + e.getMessage());
            return -1;
        }
    }

}

