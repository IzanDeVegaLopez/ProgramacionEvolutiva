
import javax.swing.*;

import Mapas.Map;
import Mapas.mapReader;
import org.math.plot.*;
import java.awt.*;
import Graphic.*;


public class main {
 Map m;
 MainMenu mm;
 void main() {
  init();
 }

 void init(){
  MainMenu mm = new MainMenu(800,600, mapReader.SUPERMERCADO);
 }
}