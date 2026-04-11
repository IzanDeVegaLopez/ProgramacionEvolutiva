package Graphic;
import javax.swing.*;
import java.awt.*;
import javax.swing.BoxLayout;
import java.lang.*;
import java.awt.event.*;

public class NumericField extends MyPanel{
    public static int x=40, y=60;
    public JTextField textField;
    public NumericField(int startValue){
        super(255,255,255);
        textField = new JTextField("0", 10);
        JButton incrementButton = new JButton("+");
        JButton decrementButton = new JButton("-");
        Font f = new Font("Arial",Font.PLAIN, 10);
        incrementButton.setFont(f);
        decrementButton.setFont(f);
        textField.setMaximumSize(new Dimension(50,y));
        textField.setMinimumSize(new Dimension(50,y));
        textField.setText(String.valueOf(startValue));

        incrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(textField.getText());
                textField.setText(String.valueOf(value + 1));
            }
        });

        decrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(textField.getText());
                textField.setText(String.valueOf(value - 1));
            }
        });
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(textField);

        MyPanel aux = new MyPanel(255,255,255);
        aux.setLayout(new BoxLayout(aux, BoxLayout.X_AXIS));
        incrementButton.setMaximumSize(new Dimension(x,y));
        decrementButton.setMaximumSize(new Dimension(x,y));
        incrementButton.setMinimumSize(new Dimension(x,y));
        decrementButton.setMinimumSize(new Dimension(x,y));

        aux.add(incrementButton);
        aux.add(decrementButton);
        aux.setMaximumSize(new Dimension(2*x,y));
        aux.setMinimumSize(new Dimension(2*x,y));
        add(aux);
    }
}
