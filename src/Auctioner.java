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


        // Acumulem els calers. En aquest cas de moneda utilitzem el nombre de càrregues d'aigua que tenen.
        HashMap<Robot, Double> carregues = new HashMap<>();
        for(Robot r : escenari.Robots){
            carregues.put(r, r.getPle());
        }

        // Subhastem tots els focs a tots els robots.
        for (Foc f : escenari.Focs) {
            Voyage winner = null;


















































            
            TreeSet<Voyage> offers = new TreeSet<>();
            for (Robot r : escenari.Robots) {
                offers.add(r.getOffer(f));
            }
            // Afegeixo el viatge al robot guanyador.
            winner.getRobot().update(winner);
        }

        for(Arbre a : escenari.Arbres){
            Voyage winner  = null;
            TreeSet<Voyage> offers = new TreeSet<>();
            for(Robot r : escenari.Robots){
                offers.add(r.getOffer(a));
            }
            winner.getRobot().update(winner);
        }

        TreeSet<Voyage> offers = new TreeSet<>();
        for(Robot r : escenari.Robots){
            offers.add(r.getOffer(escenari.Diposit));
        }

    }
}
