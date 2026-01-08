package ERPSytem.Student;
import ERPSytem.*;
import ERPSytem.Databaseconnector.database;
import ERPSytem.Loginpage.Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class myfaculty extends JFrame {
    int userid;

    private JPanel Nframe;
    myfaculty() {
        this.userid = UserSession.userId;
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

        Nframe = new JPanel();
        Nframe.setLayout(null);
        Nframe.setBackground(Color.WHITE);
        int panelWidth = screenSize.width - 500, panelHeight = 400;
        int x = (screenSize.width + 500 - panelWidth) / 2;
        int y = (screenSize.height - panelHeight) / 2;
        Nframe.setBounds(x, y, panelWidth, panelHeight);
        Nframe.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JButton tt = new JButton();
        tt.setText("Time Table");
        tt.setBackground(Color.blue);
        tt.setBorderPainted(false);
        tt.setContentAreaFilled(false);
        tt.setFocusPainted(false);
        tt.setFont(new Font("Times New Roman", Font.BOLD, 40));
        tt.setForeground(Color.white);
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
        l.setForeground(Color.white);
        l.setBounds(0, 1400, 500, 200);
        l.addActionListener(e->{
            new Frame();
            this.dispose();
        });

        this.setTitle("My Courses");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
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
        sidepanel.add(l);

        this.add(sidepanel);

        loadCourseCatalog();
    }

    private void loadCourseCatalog() {

        Nframe.removeAll();
        Nframe.repaint();
        Nframe.revalidate();

        JLabel title = new JLabel("My Courses", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 36));
        title.setBounds(0, 20, Nframe.getWidth(), 50);
        Nframe.add(title);

        String[] columns = {"Course Code", "Course Name", "Credits", "Faculty", "Action", "course_id"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table.setRowHeight(45);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 100, Nframe.getWidth() - 200, 1500);

        Nframe.add(scrollPane);

        String sql = """
            SELECT 
                c.course_code,
                c.course_name,
                c.credits,
                f.name AS faculty_name,
                c.course_id
            FROM enrollments e
            JOIN students s ON e.student_id = s.student_id
            JOIN courses c ON e.course_id = c.course_id
            LEFT JOIN faculty f ON c.faculty_id = f.faculty_id
            WHERE s.user_id = ?
        """;

        try (Connection conn = database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                        rs.getString("faculty_name"),
                        "Drop",
                        rs.getInt("course_id")   // hidden column
                });
            }

            // Hide course_id column
            table.getColumnModel().getColumn(5).setMinWidth(0);
            table.getColumnModel().getColumn(5).setMaxWidth(0);
            table.getColumnModel().getColumn(5).setWidth(0);

            table.getColumn("Action").setCellRenderer(new DropButtonRenderer());
            table.getColumn("Action").setCellEditor(new DropButtonEditor(new JCheckBox(), userid));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading courses:\n" + e.getMessage());
        }

        Nframe.repaint();
        Nframe.revalidate();
    }
}

// BUTTON RENDERER
class DropButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
    public DropButtonRenderer() {
        setOpaque(true);
        setText("Drop");
        setBackground(Color.RED);
        setForeground(Color.WHITE);
        setFont(new Font("Times New Roman", Font.BOLD, 18));
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        return this;
    }
}



// BUTTON EDITOR

class DropButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private boolean clicked;
    private int userId;
    private int courseId;

    public DropButtonEditor(JCheckBox checkBox, int userId) {
        super(checkBox);
        this.userId = userId;

        button = new JButton("Drop");
        button.setOpaque(true);
        button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));

        button.addActionListener(e -> {
            clicked = true;
            fireEditingStopped();
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {

        clicked = true;

        courseId = (int) table.getModel().getValueAt(row, 5); // hidden course_id
        return button;
    }

    public Object getCellEditorValue() {
        if (clicked) {
            dropCourse(userId, courseId);
        }
        clicked = false;
        return "Drop";
    }

    private void dropCourse(int userId, int courseId) {
        try (Connection conn = database.getConnection()) {

            PreparedStatement ps1 = conn.prepareStatement(
                    "SELECT student_id FROM students WHERE user_id=?"
            );
            ps1.setInt(1, userId);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                JOptionPane.showMessageDialog(null, "Student not found!");
                return;
            }

            int studentId = rs1.getInt("student_id");

            // Delete enrollment
            PreparedStatement ps2 = conn.prepareStatement(
                    "DELETE FROM enrollments WHERE student_id=? AND course_id=?"
            );
            ps2.setInt(1, studentId);
            ps2.setInt(2, courseId);
            ps2.executeUpdate();

            // Decrease enrolled count
            PreparedStatement ps3 = conn.prepareStatement(
                    "UPDATE courses SET enrolled = enrolled - 1 WHERE course_id=?"
            );
            ps3.setInt(1, courseId);
            ps3.executeUpdate();

            JOptionPane.showMessageDialog(null, "Course Dropped Successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error dropping course:\n" + ex.getMessage());
        }
    }
}


