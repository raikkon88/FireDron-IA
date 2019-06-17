
import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Llorenç
 */
public class Arbre extends Position{
     public Arbre(double xin, double yin){
        super(xin, yin);
    }
    
    public ImageIcon getImatge() {
       String dir = System.getProperty("user.dir")+ "/Imatges/arbre.jpg";
       return new ImageIcon( dir);
    }
}
