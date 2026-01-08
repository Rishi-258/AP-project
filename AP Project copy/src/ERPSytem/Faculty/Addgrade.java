package ERPSytem.Faculty;

import ERPSytem.*;
import ERPSytem.Databaseconnector.database;
import ERPSytem.Loginpage.Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.*;
public class Addgrade extends JFrame {

    private JPanel Nframe;
    private int userId;       // session user id (auth table)
    private int facultyId;    // faculty table id
    private JComboBox<CourseItem> courseCombo;
    private DefaultTableModel model;
    private JTable table;

    public Addgrade() {
        this.userId = UserSession.userId;

        // --- Frame and panels / same blueprint style ---
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
        hp.addActionListener(e -> {
            new fhomepage();
            this.dispose();
        });

        // Nframe center
        Nframe = new JPanel();
        Nframe.setLayout(null);
        Nframe.setBackground(Color.WHITE);
        int panelWidth = 1200, panelHeight = 1200;
        int x = (screenSize.width - panelWidth) / 2;
        int y = (screenSize.height - panelHeight) / 2;
        Nframe.setBounds(x, y, panelWidth, panelHeight);
        Nframe.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // left buttons (keep same style & positions so other pages unchanged)
        JButton tt = new JButton();
        tt.setText("My Section");
        tt.setBackground(Color.blue);
        tt.setBorderPainted(false);
        tt.setContentAreaFilled(false);
        tt.setFocusPainted(false);
        tt.setFont(new Font("Times New Roman", Font.BOLD, 40));
        tt.setForeground(Color.white);
        tt.setBounds(0, 400, 500, 200);
        tt.addActionListener(e->{
            new facultycourse();
            this.dispose();
        });

        JButton courseBtn = new JButton();
        courseBtn.setText("Class stats");
        courseBtn.setBackground(Color.blue);
        courseBtn.setBorderPainted(false);
        courseBtn.setContentAreaFilled(false);
        courseBtn.setFocusPainted(false);
        courseBtn.setFont(new Font("Times New Roman", Font.BOLD, 40));
        courseBtn.setForeground(Color.white);
        courseBtn.setBounds(0, 600, 500, 200);
        courseBtn.addActionListener(e->{
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
        l.setForeground(Color.white);
        l.setBounds(0, 1400, 500, 200);
        l.addActionListener(e -> {
            new Frame();
            this.dispose();
        });

        // Frame settings
        this.setTitle("Add Grades");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(true);
        this.setSize(screenSize.width, screenSize.height);

        // add
        upperpanel.add(hp);
        this.add(upperpanel);
        this.add(Nframe);
        sidepanel.add(courseBtn);
        sidepanel.add(tt);
        sidepanel.add(g);

        sidepanel.add(l);
        this.add(sidepanel);

        // Build center controls (combo + table)
        buildCenter();

        // find faculty id for this user (required)
        fetchFacultyId();

        // load courses for this faculty
        loadCoursesForFaculty();

        this.setVisible(true);
    }

    private void buildCenter() {
        // Title
        JLabel title = new JLabel("Enter Grades", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 36));
        title.setBounds(0, 10, Nframe.getWidth(), 50);
        Nframe.add(title);

        // Course combobox label
        JLabel courseLabel = new JLabel("Course:", SwingConstants.LEFT);
        courseLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        courseLabel.setBounds(30, 70, 150, 60);
        Nframe.add(courseLabel);

        // Course combobox (CourseItem holds id+code+name)
        courseCombo = new JComboBox<>();
        courseCombo.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        courseCombo.setBounds(160, 70, 800, 40);
        Nframe.add(courseCombo);

        // When course selection changes -> load students
        courseCombo.addActionListener(e -> {
            CourseItem item = (CourseItem) courseCombo.getSelectedItem();
            if (item != null && item.courseId != -1) {
                loadStudentsForCourse(item.courseId);
            } else {
                clearTable();
            }
        });

        // Table columns (hidden last column student_id)
        String[] columns = {"Student Name", "Roll No", "Quiz", "Midterm", "EndSem", "Assignment", "Total", "Letter", "Action", "student_id"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // allow editing quiz/midterm/endsem/assignment -> columns 2..5
                return column >= 2 && column <= 5 || column == 8; // Action cell editable to click button
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        table.setRowHeight(60);

        // Hide the student_id column (last)
        TableColumnModel tcm = table.getColumnModel();

        // Put table into scroll pane
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(30, 130, Nframe.getWidth() - 60, Nframe.getHeight() - 180);
        Nframe.add(sp);

        // Add Save (Action) button renderer/editor
        table.getColumn("Action").setCellRenderer(new SaveButtonRenderer());
        table.getColumn("Action").setCellEditor(new SaveButtonEditor(new JCheckBox(), this.userId));

        // hide last column (student_id)
        tcm.getColumn(9).setMinWidth(0);
        tcm.getColumn(9).setMaxWidth(0);
        tcm.getColumn(9).setWidth(0);
    }

    private void fetchFacultyId() {
        try (Connection conn = database.getConnection()) {
            if (conn == null) return;
            String sql = "SELECT faculty_id FROM faculty WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, this.userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                facultyId = rs.getInt("faculty_id");
            } else {
                facultyId = -1;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching faculty id: " + e.getMessage());
            facultyId = -1;
        }
    }

    private void loadCoursesForFaculty() {
        courseCombo.removeAllItems();
        // default
        courseCombo.addItem(new CourseItem(-1, "-- Select Course --", ""));
        if (facultyId <= 0) return;

        String sql = "SELECT course_id, course_code, course_name FROM courses WHERE faculty_id = ?";
        try (Connection conn = database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, facultyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courseCombo.addItem(new CourseItem(rs.getInt("course_id"), rs.getString("course_code"), rs.getString("course_name")));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage());
        }
    }

    private void clearTable() {
        model.setRowCount(0);
    }

    private void loadStudentsForCourse(int courseId) {
        model.setRowCount(0);

        String sql = """
            SELECT s.student_id, s.name, s.roll_no,
                   g.quiz_marks, g.midterm_marks, g.endsem_marks, g.assignment_marks, g.total_marks, g.letter_grade
            FROM enrollments e
            JOIN students s ON e.student_id = s.student_id
            LEFT JOIN grades g ON g.student_id = s.student_id AND g.course_id = e.course_id
            WHERE e.course_id = ?
        """;

        try (Connection conn = database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Integer quiz = (Integer) nullableInt(rs, "quiz_marks");
                Integer mid = (Integer) nullableInt(rs, "midterm_marks");
                Integer end = (Integer) nullableInt(rs, "endsem_marks");
                Integer asg = (Integer) nullableInt(rs, "assignment_marks");
                Integer tot = (Integer) nullableInt(rs, "total_marks");
                String letter = rs.getString("letter_grade");

                // show empty strings instead of null so fields are editable
                model.addRow(new Object[]{
                        rs.getString("name"),
                        rs.getString("roll_no"),
                        quiz == null ? "" : quiz.toString(),
                        mid == null ? "" : mid.toString(),
                        end == null ? "" : end.toString(),
                        asg == null ? "" : asg.toString(),
                        tot == null ? "" : (tot.toString()) ,
                        letter == null ? "" : letter,
                        "Save",
                        rs.getInt("student_id")
                });
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        }
    }

    // helper to read nullable int from ResultSet
    private Integer nullableInt(ResultSet rs, String col) throws SQLException {
        int v = rs.getInt(col);
        if (rs.wasNull()) return null;
        return v;
    }


    // compute total and letter grade
    private static int computeTotal(int q, int m, int e, int a) {
        return q + m + e + a;
    }

    private static String computeLetter(int total) {
        // Simple scale, adjust as you need
        if (total >= 90) return "A";
        if (total >= 80) return "B";
        if (total >= 70) return "C";
        if (total >= 60) return "D";
        return "F";
    }

    // --------------------------------------------
    // Inner classes: CourseItem, SaveButtonRenderer, SaveButtonEditor
    // --------------------------------------------
    private static class CourseItem {
        int courseId;
        String code;
        String name;

        CourseItem(int courseId, String code, String name) {
            this.courseId = courseId;
            this.code = code;
            this.name = name;
        }

        @Override
        public String toString() {
            return code + " - " + name;
        }
    }

    // Renderer for Save button
    static class SaveButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public SaveButtonRenderer() {
            setOpaque(true);
            setText("Save");
            setBackground(Color.green);
            setForeground(Color.WHITE);
            setFont(new Font("Times New Roman", Font.BOLD, 18));
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return this;
        }
    }

    // Editor for Save button: on click perform insert/update
    class SaveButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private boolean clicked;
        private int userIdLocal;

        SaveButtonEditor(JCheckBox checkBox, int userId) {
            super(checkBox);
            this.userIdLocal = userId;
            button = new JButton("Save");
            button.setOpaque(true);
            button.setBackground(Color.green);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Times New Roman", Font.BOLD, 18));

            button.addActionListener(e -> {
                clicked = true;
                fireEditingStopped();
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) {
                int selRow = table.getSelectedRow();
                if (selRow < 0) return "Save";

                try {
                    int studentId = (int) model.getValueAt(selRow, 9); // hidden student_id
                    String quizS = model.getValueAt(selRow, 2).toString().trim();
                    String midS = model.getValueAt(selRow, 3).toString().trim();
                    String endS = model.getValueAt(selRow, 4).toString().trim();
                    String asgS = model.getValueAt(selRow, 5).toString().trim();

                    int q = quizS.isEmpty() ? 0 : Integer.parseInt(quizS);
                    int m = midS.isEmpty() ? 0 : Integer.parseInt(midS);
                    int en = endS.isEmpty() ? 0 : Integer.parseInt(endS);
                    int a = asgS.isEmpty() ? 0 : Integer.parseInt(asgS);

                    int total = computeTotal(q, m, en, a);
                    String letter = computeLetter(total);

                    // update model display
                    model.setValueAt(String.valueOf(total), selRow, 6);
                    model.setValueAt(letter, selRow, 7);

                    // save to DB: if exists update else insert
                    CourseItem ci = (CourseItem) courseCombo.getSelectedItem();
                    if (ci == null || ci.courseId <= 0) {
                        JOptionPane.showMessageDialog(Addgrade.this, "Select a valid course first!");
                        return "Save";
                    }
                    int courseId = ci.courseId;

                    try (Connection conn = database.getConnection()) {
                        // check if grade record exists
                        PreparedStatement psChk = conn.prepareStatement(
                                "SELECT grade_id FROM grades WHERE student_id = ? AND course_id = ?"
                        );
                        psChk.setInt(1, studentId);
                        psChk.setInt(2, courseId);
                        ResultSet rsChk = psChk.executeQuery();

                        if (rsChk.next()) {
                            // update
                            int gradeId = rsChk.getInt("grade_id");
                            PreparedStatement psUpd = conn.prepareStatement(
                                    "UPDATE grades SET quiz_marks=?, midterm_marks=?, endsem_marks=?, assignment_marks=?, total_marks=?, letter_grade=? WHERE grade_id=?"
                            );
                            psUpd.setInt(1, q);
                            psUpd.setInt(2, m);
                            psUpd.setInt(3, en);
                            psUpd.setInt(4, a);
                            psUpd.setInt(5, total);
                            psUpd.setString(6, letter);
                            psUpd.setInt(7, gradeId);
                            psUpd.executeUpdate();
                            psUpd.close();
                        } else {
                            // insert
                            PreparedStatement psIns = conn.prepareStatement(
                                    "INSERT INTO grades (student_id, course_id, quiz_marks, midterm_marks, endsem_marks, assignment_marks, total_marks, letter_grade) VALUES (?,?,?,?,?,?,?,?)"
                            );
                            psIns.setInt(1, studentId);
                            psIns.setInt(2, courseId);
                            psIns.setInt(3, q);
                            psIns.setInt(4, m);
                            psIns.setInt(5, en);
                            psIns.setInt(6, a);
                            psIns.setInt(7, total);
                            psIns.setString(8, letter);
                            psIns.executeUpdate();
                            psIns.close();
                        }
                        rsChk.close();
                        psChk.close();

                        JOptionPane.showMessageDialog(Addgrade.this, "Saved successfully for student ID: " + studentId);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Addgrade.this, "DB error when saving: " + ex.getMessage());
                    }

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(Addgrade.this, "Enter valid numeric marks (integers).");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Addgrade.this, "Error while saving: " + ex.getMessage());
                }

            }
            clicked = false;
            return "Save";
        }
    }

}
