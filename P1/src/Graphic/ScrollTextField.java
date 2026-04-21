package Graphic;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class ScrollTextField {
    JTextPane my_text_area;
    JScrollPane my_scroll_pane;
    int width; int height;
    public ScrollTextField(int _width, int _height){
        width = _width; height = _height;
        my_text_area = new JTextPane();
        my_scroll_pane = new JScrollPane(my_text_area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        my_text_area.setText("");
        my_text_area.setFont(new Font("Arial", Font.BOLD, 24));
    }
    private void mantain_size(){
        var dim = new Dimension(width,height);
        my_scroll_pane.setMaximumSize(dim);
        my_scroll_pane.setMinimumSize(dim);
        my_text_area.setMinimumSize(dim);
        my_text_area.setMaximumSize(dim);
        my_scroll_pane.setPreferredSize(dim);
    }
    public void clear_text(){
        my_text_area.setText("");
        mantain_size();
    }
    public void add_text(String s){
        add_text(s, Color.BLACK);
        mantain_size();
    }
    public void add_text(String s, Color c){
        appendToPane(my_text_area, s, c);
        mantain_size();
    }
    public void add_text_ln(String s){
        add_text_ln(s, Color.BLACK);
        mantain_size();
    }
    public void add_text_ln(String s, Color c){
        add_text(s+'\n', c);
        mantain_size();
    }
    public void set_text(String s, Color c){
        clear_text();
        add_text(s, c);
        mantain_size();
    }
    public void set_text(String s){
        set_text(s, Color.BLACK);
        mantain_size();
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
        mantain_size();
    }
}
