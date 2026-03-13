package Graphic;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class ScrollTextField extends JScrollPane {
    JTextPane my_text_area;
    public ScrollTextField(){
        super(my_text_area = new JTextPane(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        my_text_area.setText("");
        my_text_area.setFont(new Font("Arial", Font.BOLD, 24));
    }
    public void clear_text(){
        my_text_area.setText("");
    }
    public void add_text(String s){
        add_text(s, Color.BLACK);
    }
    public void add_text(String s, Color c){
        appendToPane(my_text_area, s, c);
    }
    public void add_text_ln(String s){
        add_text_ln(s, Color.BLACK);
    }
    public void add_text_ln(String s, Color c){
        add_text(s+'\n', c);
    }
    public void set_text(String s, Color c){
        clear_text();
        add_text(s, c);
    }
    public void set_text(String s){
        set_text(s, Color.BLACK);
    }
    //src: https://stackoverflow.com/questions/9650992/how-to-change-text-color-in-the-jtextarea
    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}
