package ERPSytem.Admin;

import ERPSytem.Databaseconnector.database;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class addStudent extends JFrame {
    int userid;
    String facultyno;
    String facultyname;
    addStudent(int Userid) {
        userid=Userid;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle("Add New Student");
        this.setSize(900, 800);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);

        // ---------- TITLE ----------
        JLabel title = new JLabel("Add Student Details");
        title.setFont(new Font("Times New Roman", Font.BOLD, 50));
        title.setBounds(150, 30, 700, 60);
        add(title);

        // FONT sizes
        Font labelFont = new Font("Times New Roman", Font.BOLD, 32);
        Font fieldFont = new Font("Times New Roman", Font.PLAIN, 28);

        // ---------- INPUT LABELS & FIELDS ----------
        JLabel rollL = new JLabel("Roll No:");
        rollL.setFont(labelFont);
        rollL.setBounds(100, 130, 200, 40);

        JTextField rollField = new JTextField();
        rollField.setFont(fieldFont);
        rollField.setBounds(350, 130, 350, 45);

        JLabel nameL = new JLabel("Name:");
        nameL.setFont(labelFont);
        nameL.setBounds(100, 200, 200, 40);

        JTextField nameField = new JTextField();
        nameField.setFont(fieldFont);
        nameField.setBounds(350, 200, 350, 45);

        JLabel progL = new JLabel("Program:");
        progL.setFont(labelFont);
        progL.setBounds(100, 270, 200, 40);

        JTextField progField = new JTextField();
        progField.setFont(fieldFont);
        progField.setBounds(350, 270, 350, 45);

        JLabel yearL = new JLabel("Year:");
        yearL.setFont(labelFont);
        yearL.setBounds(100, 340, 200, 40);

        JTextField yearField = new JTextField();
        yearField.setFont(fieldFont);
        yearField.setBounds(350, 340, 350, 45);

        JLabel emailL = new JLabel("Email:");
        emailL.setFont(labelFont);
        emailL.setBounds(100, 410, 200, 40);

        JTextField emailField = new JTextField();
        emailField.setFont(fieldFont);
        emailField.setBounds(350, 410, 350, 45);

        JLabel phoneL = new JLabel("Phone:");
        phoneL.setFont(labelFont);
        phoneL.setBounds(100, 480, 200, 40);

        JTextField phoneField = new JTextField();
        phoneField.setFont(fieldFont);
        phoneField.setBounds(350, 480, 350, 45);



        // Add components to frame
        add(rollL); add(rollField);
        add(nameL); add(nameField);
        add(progL); add(progField);
        add(yearL); add(yearField);
        add(emailL); add(emailField);
        add(phoneL); add(phoneField);


        // ---------- BUTTONS ----------
        JButton saveBtn = new JButton("Add Student");
        saveBtn.setFont(new Font("Times New Roman", Font.BOLD, 36));
        saveBtn.setBounds(140, 630, 300, 70);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Times New Roman", Font.BOLD, 36));
        backBtn.setBounds(500, 630, 200, 70);

        add(saveBtn);
        add(backBtn);

        // ---------- BACK BUTTON ----------
        backBtn.addActionListener(e -> {
            new adduser();
            this.dispose();
        });

        // ---------- SAVE BUTTON ----------
        saveBtn.addActionListener(e -> {

            String roll = rollField.getText();
            String name = nameField.getText();
            String prog = progField.getText();
            String year = yearField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            if (roll.isEmpty() || name.isEmpty() || prog.isEmpty() || year.isEmpty()
                    || email.isEmpty() || phone.isEmpty() ) {

                JOptionPane.showMessageDialog(this,
                        "Please fill all fields!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            addStudentToDB(roll, name, prog, Integer.parseInt(year), email, phone, userid);
        });

        setVisible(true);
    }

    // Insert student into DB
    private void addStudentToDB(String roll, String name, String program, int year, String email, String phone, int userId) {
        try (Connection conn = database.getConnection()) {

            String sql = "INSERT INTO students (roll_no, name, program, year, email, phone, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, roll);
            ps.setString(2, name);
            ps.setString(3, program);
            ps.setInt(4, year);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setInt(7, userId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this,
                        "Student added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                new ahomepage();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "SQL ERROR:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
