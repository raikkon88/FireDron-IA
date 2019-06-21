import java.util.*;

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

            List<Offer> offers = new ArrayList<>();
            for (Robot r : escenari.Robots) {
                offers.add(r.getOffer(f, new FuzzyBidder(escenari)));
            }

            Collections.sort(offers);

            /** Tinc totes les ofertes ordenades de guanyador a perdedor. */
            while (offers.size() > 0 && !offers.get(0).getRobot().canPay(offers.get(0).getBid())){
                offers.remove(offers.get(0));
            }

            if(offers.size() > 0){
                Robot dron = offers.get(0).getRobot();
                dron.pay(offers.get(0).getBid());
                dron.update(offers.get(0));
            }

        }


    }
}
