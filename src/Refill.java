import javax.swing.ImageIcon;

/**
 *
 * @author Llorenç
 */
public class Refill extends Position{
    
    public Refill(double xin, double yin){
        super(xin, yin);
    }

    public ImageIcon getImatge() {
       String dir = System.getProperty("user.dir")+ "/Imatges/refill.png";
       return new ImageIcon( dir);
    }
}
