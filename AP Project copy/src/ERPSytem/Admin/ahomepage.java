package ERPSytem.Admin;

import ERPSytem.Loginpage.Frame;

import javax.swing.*;
import java.awt.*;

public class ahomepage extends JFrame {
    private JPanel Nframe;

    public ahomepage() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel upperpanel = new JPanel();
        upperpanel.setBackground(Color.white);
        upperpanel.setBounds(0, 0, screenSize.width, 150);
        upperpanel.setLayout(null);
        JLabel welcome=new JLabel("--Welcome Admin--");
        welcome.setFont(new Font("Times New Roman", Font.BOLD, 80));
        welcome.setForeground(Color.BLACK);
        welcome.setBounds(0, 0,1500, 750);
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setVerticalAlignment(SwingConstants.CENTER);


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
        fd.setText("Add Course");
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

        // --- Add everything ---
        this.setTitle("Admin Homepage");
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
        Nframe.add(welcome);

        this.add(sidepanel);
    }

  }
