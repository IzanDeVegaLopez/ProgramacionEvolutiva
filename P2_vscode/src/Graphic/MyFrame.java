package Graphic;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame(int x, int y){
        setSize(x,y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    //Important Methods
    /*
    setLocation(x,y)
    setBounds(posX,posY, sizeX, sizeY)
    setRisezable(bool)
    setExtendedState(Frame.MAXIMIZED_BOTH); //hay más opciones
    setTitle("nombreMarco");
    setIconImage(Image image);
    add(JPanel panel); //Hay q crearla primero con "JPanel p = new JPanel()" o usando el MyPanel


    Related---------------
    Toolkit miPantalla = Toolkit.getDefaultKit()

    #include java.awt.*;
    Dimension tamanoPantalla = miPantalla.getScreenSize()
    tamanoPantalla.height
                  .width

    Image miIcono = miPantalla.getImage("icono.gif"); //recibe el path a la imagen desde P1
    */
}

/* Inside JFrame
-------------------
    JPanel lamina = new JPanel();
    FlowLayout disposición  = new FlowLayout(FlowLayout.LEFT);
    lamina.setLayout(disposición);
    add(lamina);
*/

/* Zonas JFrame
//x y ponen separación entre las zonas en los ejes
setLayout(new BorderLayout(x,y));
add(new JButton("a"), BorderLayour.NORTH);
add(new JButton("b"), BorderLayour.SOUTH);
add(new JButton("c"), BorderLayour.WEST);
add(new JButton("d"), BorderLayour.EAST);
add(new JButton("e"), BorderLayour.CENTER);
 */

/* Layout

BorderLayout
FlowLayout

//Solo puede haber un panel cada vez. AL hacer add(panel1), add(panel2) solo se vería el segundo
Pero podemos poner como segundo Argumento add(panel1, BorderLayout.North), add(panel2, BorderLayout.South) y así se ven ambos
 */