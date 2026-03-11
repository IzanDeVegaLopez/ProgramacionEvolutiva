package Graphic;

import javax.swing.*;

public class ScrollTextField extends JScrollPane {
    JTextArea my_text_area;
    public ScrollTextField(){
        super(my_text_area = new JTextArea(""), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        my_text_area.setLineWrap(true);
    }
    public void cleat_text(){
        my_text_area.setText("");
    }
    public void add_text(String s){
        my_text_area.append(s);
    }
    public void add_text_ln(String s){
        my_text_area.append(s+'\n');
    }
    public void set_text(String s){
        my_text_area.setText(s);
    }
}
