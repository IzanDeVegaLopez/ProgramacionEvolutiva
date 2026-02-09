package Graphic;
import javax.swing.*;
import java.awt.*;
import javax.swing.BoxLayout;
import java.lang.*;
import java.awt.event.*;

public class NumericField extends MyPanel{
    int x=50, y=50;
    public NumericField(int startValue){
        super(255,255,255);
        JTextField textField = new JTextField("0", 10);
        JButton incrementButton = new JButton("+");
        JButton decrementButton = new JButton("-");

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
        aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
        incrementButton.setMaximumSize(new Dimension(x,y/2));
        decrementButton.setMaximumSize(new Dimension(x,y/2));
        incrementButton.setMinimumSize(new Dimension(x,y/2));
        decrementButton.setMinimumSize(new Dimension(x,y/2));

        aux.add(incrementButton);
        aux.add(decrementButton);
        aux.setMaximumSize(new Dimension(x,y));
        aux.setMinimumSize(new Dimension(x,y));
        add(aux);
    }
}
