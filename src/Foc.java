import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Llorenç
 */
public class Foc extends Position {
    int estat;
    
    public Foc(double xin, double yin){
        super(xin, yin);
        estat=1;
    }
    public void apaga(){
        estat=0;
    }
    public void encen(){
        estat=1;
    }
    
    public ImageIcon getImatge() {
      String dir = System.getProperty("user.dir")+ "/Imatges/fire"+Integer.toString(estat)+".png";
      return new ImageIcon( dir);
    }
}
