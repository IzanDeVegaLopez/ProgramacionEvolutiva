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

/*
Component	A Component is the Abstract base class for about the non-menu user-interface controls of Java SWING. Components are representing an object with a graphical representation.
Container	A Container is a component that can container Java SWING Components
JComponent	A JComponent is a base class for all swing UI Components In order to use a swing component that inherits from JComponent, the component must be in a containment hierarchy whose root is a top-level Java Swing container.
JLabel	A JLabel is an object component for placing text in a container.
JButton	This class creates a labeled button.
JColorChooser                  	A JColorChooser provides a pane of controls designed to allow the user to manipulate and select a color.
JCheckBox	A JCheckBox is a graphical (GUI) component that can be in either an on-(true) or off-(false) state.
JRadioButton	The JRadioButton class is a graphical (GUI) component that can be in either an on-(true) or off-(false) state. in the group
JList	A JList component represents the user with the scrolling list of text items.
JComboBox	A JComboBox component is Presents the User with a show up Menu of choices.
JTextField	A JTextField object is a text component that will allow for the editing of a single line of text. 
JPasswordField                  	A JPasswordField object it is a text component specialized for password entry.
JTextArea	A JTextArea object is a text component that allows for the editing of multiple lines of text.
Imagelcon	A ImageIcon control is an implementation of the Icon interface that paints Icons from Images
JScrollbar	A JScrollbar control represents a scroll bar component in order to enable users to Select from range values.
JOptionPane    	JOptionPane provides set of standard dialog boxes that prompt users for a value or Something.
JFileChooser  	A JFileChooser it Controls represents a dialog window from which the user can select a file.
JProgressBar               	As the task progresses towards completion, the progress bar displays the tasks percentage on its completion.
JSlider 	A JSlider this class is letting the user graphically (GUI) select by using a value by sliding a knob within a bounded interval.
JSpinner 	A JSpinner this class is a single line input where the field that lets the user select by using a number or an object value from an ordered sequence.
*/