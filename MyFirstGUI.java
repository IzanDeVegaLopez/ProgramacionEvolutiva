import javax.swing.*;

public class MyFirstGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My First Window");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello, GUI World!", SwingConstants.CENTER);
        frame.add(label);

        frame.setVisible(true);
    }
}