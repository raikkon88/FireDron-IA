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

        List<Robot> robotsPerAssignar = new ArrayList<>(escenari.Robots);
        List<Foc> focsAssignats = new ArrayList<>();
        HashMap<Robot, TreeSet<Voyage>> subhasta = new HashMap<>();
        // Mentre no estiguin tots assignats.
        while(robotsPerAssignar.size() > 0){
            // Subhastem tots els focs a tots els robots.
            for (Foc f : escenari.Focs) {
                for (Robot r : escenari.Robots) {

                }
            }
            // Mirem els guanyadors


            // TODO : Si queden drons i no queden focs els diem als drons que quedin que vagin a apagar els focs que quedin.

        }
    }
}
