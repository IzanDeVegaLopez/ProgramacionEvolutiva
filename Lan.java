// Driver Class
// Java Swing Program to Add Checkbox
// in the Frame
import java.awt.*;
class Lan {
    // Main Function
    Lan()
    {
        // Frame Created
        Frame f = new Frame();

        Label l1 = new Label("Select known Languages");

        l1.setBounds(100, 50, 120, 80);
        f.add(l1);

        // CheckBox created
        Checkbox c2 = new Checkbox("Hindi");
        c2.setBounds(100, 150, 50, 50);
        f.add(c2);

        // CheckBox created
        Checkbox c3 = new Checkbox("English");
        c3.setBounds(100, 200, 80, 50);
        f.add(c3);

        // CheckBox created
        Checkbox c4 = new Checkbox("marathi");
        c4.setBounds(100, 250, 80, 50);
        f.add(c4);

        f.setSize(500, 500);
        f.setLayout(null);
        f.setVisible(true);
    }

    public static void main(String ar[]) { new Lan(); }
}