package ERPSytem.Admin;

import ERPSytem.Databaseconnector.database;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class addFaculty extends JFrame {
    int userid;
    int facultyid;
    public addFaculty(int userid) {
        this.userid = userid;

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle("Add Faculty");
        this.setSize(900, 850);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);

        // ---------- TITLE ----------
        JLabel title = new JLabel("Add Faculty Details");
        title.setFont(new Font("Times New Roman", Font.BOLD, 50));
        title.setBounds(150, 20, 700, 70);
        add(title);

        // Fonts
        Font labelFont = new Font("Times New Roman", Font.BOLD, 32);
        Font fieldFont = new Font("Times New Roman", Font.PLAIN, 28);

        // ---------- FIELDS ----------
        JLabel empL = new JLabel("Employee ID:");
        empL.setFont(labelFont);
        empL.setBounds(100, 120, 250, 40);

        JTextField empField = new JTextField();
        empField.setFont(fieldFont);
        empField.setBounds(380, 120, 350, 45);

        JLabel nameL = new JLabel("Name:");
        nameL.setFont(labelFont);
        nameL.setBounds(100, 190, 250, 40);

        JTextField nameField = new JTextField();
        nameField.setFont(fieldFont);
        nameField.setBounds(380, 190, 350, 45);

        JLabel depL = new JLabel("Department:");
        depL.setFont(labelFont);
        depL.setBounds(100, 260, 250, 40);

        JTextField depField = new JTextField();
        depField.setFont(fieldFont);
        depField.setBounds(380, 260, 350, 45);

        JLabel emailL = new JLabel("Email:");
        emailL.setFont(labelFont);
        emailL.setBounds(100, 330, 250, 40);

        JTextField emailField = new JTextField();
        emailField.setFont(fieldFont);
        emailField.setBounds(380, 330, 350, 45);

        JLabel phoneL = new JLabel("Phone:");
        phoneL.setFont(labelFont);
        phoneL.setBounds(100, 400, 250, 40);

        JTextField phoneField = new JTextField();
        phoneField.setFont(fieldFont);
        phoneField.setBounds(380, 400, 350, 45);


        // ADD components
        add(empL); add(empField);
        add(nameL); add(nameField);
        add(depL); add(depField);
        add(emailL); add(emailField);
        add(phoneL); add(phoneField);

        // ---------- BUTTONS ----------
        JButton saveBtn = new JButton("Add Faculty");
        saveBtn.setFont(new Font("Times New Roman", Font.BOLD, 36));
        saveBtn.setBounds(150, 560, 300, 80);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Times New Roman", Font.BOLD, 36));
        backBtn.setBounds(500, 560, 200, 80);

        add(saveBtn);
        add(backBtn);

        // ---------- BACK BUTTON ----------
        backBtn.addActionListener(e -> {
            new adduser();
            this.dispose();
        });

        // ---------- SAVE BUTTON ----------
        saveBtn.addActionListener(e -> {

            String emp_id = empField.getText();
            String name = nameField.getText();
            String dept = depField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();


            if (emp_id.isEmpty() || name.isEmpty() || dept.isEmpty()
                    || email.isEmpty() || phone.isEmpty() ) {

                JOptionPane.showMessageDialog(this,
                        "Please fill ALL fields!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            addFacultyToDB(emp_id, name, dept, email, phone, userid);
        });

        setVisible(true);
    }

    // Insert into faculty table
    private void addFacultyToDB(String empId, String name, String dept, String email, String phone, int userId) {
        try (Connection conn = database.getConnection()) {

            String sql = """
                    INSERT INTO faculty (emp_id, name, department, email, phone, user_id)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, empId);
            ps.setString(2, name);
            ps.setString(3, dept);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setInt(6, userId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this,
                        "Faculty added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                new ahomepage();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Something went wrong!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "SQL ERROR:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
