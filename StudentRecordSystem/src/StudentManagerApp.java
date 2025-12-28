import java.awt.*;
import javax.swing.*;

class Student {
    String regNo, name, course, rollNo, feeStatus;
    Student next;

    Student(String regNo, String name, String course, String rollNo, String feeStatus) {
        this.regNo = regNo;
        this.name = name;
        this.course = course;
        this.rollNo = rollNo;
        this.feeStatus = feeStatus;
        this.next = null;
    }
}

class StudentRecord {
    Student start;

    void addStudent(String regNo, String name, String course, String rollNo, String feeStatus) {
        Student newStudent = new Student(regNo, name, course, rollNo, feeStatus);
        if (start == null) start = newStudent;
        else {
            Student temp = start;
            while (temp.next != null) temp = temp.next;
            temp.next = newStudent;
        }
    }

    String showAllStudents() {
        if (start == null) return "No student record found!";
        String data = "";
        Student temp = start;
        while (temp != null) {
            data += "Student Information:\n";
            data += "Registration Number: " + temp.regNo + "\n";
            data += "Name: " + temp.name + "\n";
            data += "Course: " + temp.course + "\n";
            data += "Roll Number: " + temp.rollNo + "\n";
            data += "Fee Status: " + temp.feeStatus + "\n\n";
            temp = temp.next;
        }
        return data;
    }

    String searchStudent(String rollNo) {
        Student temp = start;
        while (temp != null) {
            if (temp.rollNo.equals(rollNo)) {
                return "Student Found:\nReg No: " + temp.regNo + "\nName: " + temp.name +
                        "\nCourse: " + temp.course + "\nFee Status: " + temp.feeStatus;
            }
            temp = temp.next;
        }
        return "Student not found!";
    }

    boolean deleteStudent(String rollNo) {
        if (start == null) return false;
        if (start.rollNo.equals(rollNo)) {
            start = start.next;
            return true;
        }
        Student prev = start;
        Student curr = start.next;
        while (curr != null) {
            if (curr.rollNo.equals(rollNo)) {
                prev.next = curr.next;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    int totalStudents() {
        int count = 0;
        Student temp = start;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    void clearAll() {
        start = null;
    }
}

public class StudentManagerApp extends JFrame {
    JTextField regField, nameField, courseField, rollField, feeField, searchBox, deleteBox;
    JTextArea outputArea;
    StudentRecord record = new StudentRecord();

    StudentManagerApp() {
        setTitle(" Student Record Manager");
        setSize(650, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        Color bgColor = new Color(245, 245, 245);
        Color btnColor = new Color(173, 216, 230);
        Color inputColor = Color.WHITE;

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.setBackground(bgColor);

        regField = new JTextField(); nameField = new JTextField();
        courseField = new JTextField(); rollField = new JTextField(); feeField = new JTextField();

        JTextField[] fields = {regField, nameField, courseField, rollField, feeField};
        String[] labels = {"Registration No:", "Name:", "Course:", "Roll No:", "Fee Status:"};

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(font);
            lbl.setForeground(Color.DARK_GRAY);
            fields[i].setFont(font);
            fields[i].setBackground(inputColor);
            inputPanel.add(lbl);
            inputPanel.add(fields[i]);
        }

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBackground(bgColor);

        JButton add = new JButton("Add");
        JButton show = new JButton("Show All");
        JButton search = new JButton("Search");
        JButton delete = new JButton("Delete");
        JButton count = new JButton("Count");
        JButton clear = new JButton("Clear All");

        JButton[] buttons = {add, show, search, delete, count, clear};
        for (JButton btn : buttons) {
            btn.setFont(font);
            btn.setPreferredSize(new Dimension(130, 45));
            btn.setBackground(btnColor);
            buttonPanel.add(btn);
        }

        JPanel searchDeletePanel = new JPanel(new GridLayout(1, 4, 10, 10));
        searchDeletePanel.setBackground(bgColor);

        searchBox = new JTextField(); deleteBox = new JTextField();
        searchBox.setFont(font); deleteBox.setFont(font);
        searchBox.setBackground(inputColor); deleteBox.setBackground(inputColor);

        JLabel sLabel = new JLabel("Search Roll:"); sLabel.setFont(font); sLabel.setForeground(Color.DARK_GRAY);
        JLabel dLabel = new JLabel("Delete Roll:"); dLabel.setFont(font); dLabel.setForeground(Color.DARK_GRAY);

        searchDeletePanel.add(sLabel); searchDeletePanel.add(searchBox);
        searchDeletePanel.add(dLabel); searchDeletePanel.add(deleteBox);

        outputArea = new JTextArea();
        outputArea.setFont(font);
        outputArea.setEditable(false);
        outputArea.setBackground(Color.WHITE);
        outputArea.setForeground(Color.BLACK);
        JScrollPane scroll = new JScrollPane(outputArea);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(bgColor);
        topPanel.add(inputPanel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(searchDeletePanel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(buttonPanel);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

      
        add.addActionListener(e -> {
            String reg = regField.getText();
            String name = nameField.getText();
            String course = courseField.getText();
            String roll = rollField.getText();
            String fee = feeField.getText();
            if (!reg.isEmpty() && !name.isEmpty() && !course.isEmpty() && !roll.isEmpty() && !fee.isEmpty()) {
                record.addStudent(reg, name, course, roll, fee);
                outputArea.setText("Student Added Successfully!");
                regField.setText(""); nameField.setText(""); courseField.setText("");
                rollField.setText(""); feeField.setText("");
            } else {
                outputArea.setText(" Please fill all fields!");
            }
        });

        show.addActionListener(e -> outputArea.setText(record.showAllStudents()));
        search.addActionListener(e -> outputArea.setText(record.searchStudent(searchBox.getText())));
        delete.addActionListener(e -> {
            boolean deleted = record.deleteStudent(deleteBox.getText());
            outputArea.setText(deleted ? " Student Deleted!" : " Student Not Found!");
        });
        count.addActionListener(e -> outputArea.setText(" Total Students: " + record.totalStudents()));
        clear.addActionListener(e -> {
            record.clearAll();
            outputArea.setText(" All Student Records Cleared!");
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new StudentManagerApp();
    }
}
