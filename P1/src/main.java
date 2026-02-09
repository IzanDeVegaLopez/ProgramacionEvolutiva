
import javax.swing.*;
import org.math.plot.*;
import java.awt.*;
import Graphic.*;


public class main {
 static void main() {
  // define your data
  double[] x = { 1, 2, 3, 4, 5, 6 };
  double[] y = { 45, 89, 6, 32, 63, 12 };

  //Example p = new Example();
  /*
  javax.swing.SwingUtilities.invokeLater(new Runnable() {
   public void run() {
    Example.createAndShowGUI();
   }
  });
  */

  MainMenu mm = new MainMenu(800,600);
  /*
  MyFrame mainFrame = new MyFrame(800,600);

  // create your PlotPanel (you can use it as a JPanel)
  Plot2DPanel plot = new Plot2DPanel();
  // define the legend position
  plot.addLegend("SOUTH");
  // add a line plot to the PlotPanel
  plot.addLinePlot("my plot", x, y);


  FlowLayout fl1 = new FlowLayout(FlowLayout.RIGHT);

  JLabel label = new JLabel("Hello, GUI World!", SwingConstants.CENTER);
  mainFrame.add(label, BorderLayout.NORTH);
  mainFrame.add(plot, BorderLayout.CENTER);
*/
 }
}