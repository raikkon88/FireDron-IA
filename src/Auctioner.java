import com.sun.deploy.util.ArrayUtil;
import com.sun.org.apache.xerces.internal.xni.Augmentations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Subhastador.
 */
public class Auctioner {

    Escenari escenari;

    public Auctioner(Escenari escenari){
        this.escenari = escenari;
    }

    public void startAuction(){

        // Subhastem tots els focs a tots els robots.
        for (Foc f : escenari.Focs) {
            Voyage winner = null;
            for (Robot r : escenari.Robots) {
                Voyage tmpVoyage = new Voyage(r, f);
                // Recullo la millor oferta, si la oferta és la mateixa no s'ha millorat i per tant no es canvia.
                if(winner == null || winner.compareTo(tmpVoyage) < 0)
                    winner = tmpVoyage;
            }
            // Afegeixo el viatge al robot guanyador.
            winner.getRobot().update(winner);
        }
        // Tots els robots tenen els viatges cap als focs assignats. Per tant el què faran serà ordenar-los tots per l'heurístic i miraran d'apagar el màxim de focs possibles començant per els més propers.



        // TODO : Si queden drons i no queden focs els diem als drons que quedin que vagin a apagar els focs que quedin.


    }
}
