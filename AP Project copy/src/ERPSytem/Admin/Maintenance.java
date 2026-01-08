package ERPSytem.Admin;
import ERPSytem.Loginpage.Frame;
import ERPSytem.Databaseconnector.database;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class Maintenance extends JFrame {
    private JPanel Nframe;
    private int ismaintenancepanel;

    Maintenance() {

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
        JButton mo = new JButton();
        mo.setText("Set under maintenance");
        mo.setBackground(Color.blue);
        mo.setBorderPainted(false);
        mo.setFocusPainted(false);
        mo.setFont(new Font("Times New Roman", Font.BOLD, 40));
        mo.setForeground(Color.white);
        mo.setBounds(100, 500, 500, 200);
        mo.addActionListener(e->{
            setMaintenance(true);
        });

        JButton mf = new JButton();
        mf.setText("Maintenance off");
        mf.setBackground(Color.blue);
        mf.setBorderPainted(false);
        mf.setFocusPainted(false);
        mf.setFont(new Font("Times New Roman", Font.BOLD, 40));
        mf.setForeground(Color.white);
        mf.setBounds(900, 500, 500, 200);
        mf.addActionListener(e->{
            setMaintenance(false);
        });




        // --- Add everything ---
        this.setTitle("Maintenance");
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
        Nframe.add(mo);
        Nframe.add(mf);

        this.add(sidepanel);
    }
    private void setMaintenance(boolean enable) {
        try (Connection conn = database.getConnection()) {
            String sql = "UPDATE maintenance_settings SET is_maintenance = ? WHERE id = 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            int value;
            if (enable) {
                value = 1;
            }else {
                value = 0;
            }

            ps.setInt(1, value);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    enable ? "Website is now under maintenance!" : "Maintenance disabled.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }



    }
