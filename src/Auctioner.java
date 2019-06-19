import java.util.ArrayList;
import java.util.List;

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
            List<Voyage> offers = new ArrayList<>();
            for (Robot r : escenari.Robots) {

            }
            // Afegeixo el viatge al robot guanyador.
            winner.getRobot().update(winner);
        }

        for(Arbre a : escenari.Arbres){
            Voyage winner  = null;
            List<Voyage> offers = new ArrayList<>();
            for(Robot r : escenari.Robots){

            }
            winner.getRobot().update(winner);
        }

        List<Voyage> offers = new ArrayList<>();
        for(Robot r : escenari.Robots){

        }

        // Tots els robots tenen els viatges cap als focs assignats. Per tant el què faran serà ordenar-los tots per l'heurístic i miraran d'apagar el màxim de focs possibles començant per els més propers.



        // TODO : Si queden drons i no queden focs els diem als drons que quedin que vagin a apagar els focs que quedin.


    }
}
