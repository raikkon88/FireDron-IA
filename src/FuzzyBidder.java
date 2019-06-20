import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FuzzyBidder implements BidMethod {

    private enum Dist {

        PROPER("PROPER"),
        ARRIBABLE("ARRIBABLE"),
        LLUNY("LLUNY");


        private String value;

        Dist(String value){
            this.value = value;
        }

        @Override
        public String toString(){
            return value;
        }
    }

    private enum Aigua{
        POCA("POCA"),
        BE("BE"),
        PLE("PLE");

        private String value;

        Aigua(String value){
            this.value = value;
        }

        @Override
        public String toString(){
            return value;
        }

    }

    private enum Focs {
        CAP("CAP"),
        ALGUN("ALGUN"),
        QUANTS("QUANTS"),
        MOLTS("MOLTS");

        private String value;

        Focs(String value){
            this.value = value;
        }

        @Override
        public String toString(){
            return value;
        }
    }


    private enum Aposta {
        RES("RES"),
        SUAU("SUAU"),
        MIG("MIG"),
        FORT("FORT");

        private String value;

        Aposta(String value){
            this.value = value;
        }

        @Override
        public String toString(){
            return value;
        }
    }


    private class Regla {

        Dist dist;
        Focs focs;
        Aigua aigua;
        Aposta aposta;

        public Regla(Dist dist, Focs focs, Aigua aigua){
            this.dist = dist;
            this.focs = focs;
            this.aigua = aigua;
        }

        public Regla(Dist dist, Focs focs, Aigua aigua, Aposta aposta){
            this.dist = dist;
            this.focs  =focs;
            this.aigua = aigua;
            this.aposta = aposta;
        }

        /**
         * Comprova si donada una entrada la regla es dispara o no
         * @param in
         * @return
         */
        public boolean esDispara(Regla in){
            return in.focs == focs || in.aigua == aigua || in.dist == dist;
        }

        @Override
        public boolean equals(Object o) {
            if(o == null) return  false;
            else if(!(o instanceof Regla)) return false;
            else {
                Regla r = (Regla)o;
                return this.dist == r.dist && this.aigua == r.aigua && this.focs == r.focs;
            }
        }

        public Aposta getAposta(){
            return aposta;
        }
    }

    private Escenari escenari;
    private ArrayList<Regla> regles;


    public FuzzyBidder(Escenari esc){

        this.escenari = esc;

        /**
         * S'inicialitza la matriu de regles que es dispararan
         */
        regles = new ArrayList<>();
        regles.add(new Regla(Dist.LLUNY, Focs.CAP, Aigua.POCA, Aposta.RES));
        regles.add(new Regla(Dist.LLUNY, Focs.ALGUN, Aigua.POCA, Aposta.RES));
        regles.add(new Regla(Dist.LLUNY, Focs.QUANTS, Aigua.POCA, Aposta.RES));
        regles.add(new Regla(Dist.LLUNY, Focs.MOLTS, Aigua.POCA, Aposta.RES));

        regles.add(new Regla(Dist.LLUNY, Focs.CAP, Aigua.BE, Aposta.RES));
        regles.add(new Regla(Dist.LLUNY, Focs.ALGUN, Aigua.BE, Aposta.SUAU));
        regles.add(new Regla(Dist.LLUNY, Focs.QUANTS, Aigua.BE, Aposta.SUAU));
        regles.add(new Regla(Dist.LLUNY, Focs.MOLTS, Aigua.BE, Aposta.MIG));

        regles.add(new Regla(Dist.LLUNY, Focs.CAP, Aigua.PLE, Aposta.RES));
        regles.add(new Regla(Dist.LLUNY, Focs.ALGUN, Aigua.PLE, Aposta.MIG));
        regles.add(new Regla(Dist.LLUNY, Focs.QUANTS, Aigua.PLE, Aposta.MIG));
        regles.add(new Regla(Dist.LLUNY, Focs.MOLTS, Aigua.PLE, Aposta.FORT));


        regles.add(new Regla(Dist.ARRIBABLE, Focs.CAP, Aigua.POCA, Aposta.RES));
        regles.add(new Regla(Dist.ARRIBABLE, Focs.ALGUN, Aigua.POCA, Aposta.SUAU));
        regles.add(new Regla(Dist.ARRIBABLE, Focs.QUANTS, Aigua.POCA, Aposta.SUAU));
        regles.add(new Regla(Dist.ARRIBABLE, Focs.MOLTS, Aigua.POCA, Aposta.RES));

        regles.add(new Regla(Dist.ARRIBABLE, Focs.CAP, Aigua.BE, Aposta.RES));
        regles.add(new Regla(Dist.ARRIBABLE, Focs.ALGUN, Aigua.BE, Aposta.SUAU));
        regles.add(new Regla(Dist.ARRIBABLE, Focs.QUANTS, Aigua.BE, Aposta.MIG));
        regles.add(new Regla(Dist.ARRIBABLE, Focs.MOLTS, Aigua.BE, Aposta.SUAU));

        regles.add(new Regla(Dist.ARRIBABLE, Focs.CAP, Aigua.PLE, Aposta.RES));
        regles.add(new Regla(Dist.ARRIBABLE, Focs.ALGUN, Aigua.PLE, Aposta.SUAU));
        regles.add(new Regla(Dist.ARRIBABLE, Focs.QUANTS, Aigua.PLE, Aposta.SUAU));
        regles.add(new Regla(Dist.ARRIBABLE, Focs.MOLTS, Aigua.PLE, Aposta.MIG));


        regles.add(new Regla(Dist.PROPER, Focs.CAP, Aigua.POCA, Aposta.RES));
        regles.add(new Regla(Dist.PROPER, Focs.ALGUN, Aigua.POCA, Aposta.FORT));
        regles.add(new Regla(Dist.PROPER, Focs.QUANTS, Aigua.POCA, Aposta.SUAU));
        regles.add(new Regla(Dist.PROPER, Focs.MOLTS, Aigua.POCA, Aposta.RES));

        regles.add(new Regla(Dist.PROPER, Focs.CAP, Aigua.BE, Aposta.RES));
        regles.add(new Regla(Dist.PROPER, Focs.ALGUN, Aigua.BE, Aposta.FORT));
        regles.add(new Regla(Dist.PROPER, Focs.QUANTS, Aigua.BE, Aposta.FORT));
        regles.add(new Regla(Dist.PROPER, Focs.MOLTS, Aigua.BE, Aposta.MIG));

        regles.add(new Regla(Dist.PROPER, Focs.CAP, Aigua.PLE, Aposta.RES));
        regles.add(new Regla(Dist.PROPER, Focs.ALGUN, Aigua.PLE, Aposta.MIG));
        regles.add(new Regla(Dist.PROPER, Focs.QUANTS, Aigua.PLE, Aposta.FORT));
        regles.add(new Regla(Dist.PROPER, Focs.MOLTS, Aigua.PLE, Aposta.FORT));

    }


    /**
     * Sistema difús per el càlcul de l'aposta. Rebem tots els paràmetres suficients per els càlculs.
     * @param robot Quí ha de fer l'aposta
     * @param target Lloc apostat
     * @return Un número decimel entre 0 - 10 que fa referència a l'aposta.
     */
    @Override
    public double getBid(Robot robot, Position target) {
        /**
         * Entrades del sistema difús :
         * - Distància entre robot i posició.
         * - Nombre de càrregues d'aigua del robot.
         * - Nombre de focs que hi ha al voltant de la posició.
         */
        List<Pair<Dist, Double>> distRules = fuzzyDist(robot.getEuclidian(target));
        List<Pair<Aigua, Double>> aiguaRules = fuzzyAigua(robot.getPle());
        List<Pair<Focs, Double>> focsRules = fuzzyFocs(escenari.focsVoltant(target, 1));



        /**
         * Per cada regla i el que s'ha disparat muntem la funció per desfuzzificar.
         * Utilitzem COG
         */
        double maxDist = 0;
        double maxAigua = 0;
        double maxFocs = 0;
        for (Pair<Dist, Double> dists : distRules) {
            if (maxDist < dists.getValue()) maxDist = dists.getValue();
        }
        for (Pair<Aigua, Double> aigues : aiguaRules) {
            if (maxAigua < aigues.getValue()) maxAigua = aigues.getValue();
        }
        for (Pair<Focs, Double> focs : focsRules) {
            if (maxFocs < focs.getValue()) maxFocs = focs.getValue();
        }

        List<Regla> disparades = new ArrayList<>();
        for(Pair<Dist, Double> dist : distRules){
            for(Pair<Aigua, Double> aigues : aiguaRules){
                for(Pair<Focs, Double> focs : focsRules){
                    disparades.add(new Regla(dist.getKey(), focs.getKey(), aigues.getKey()));
                }
            }
        }

        List<Pair<Aposta, Double>> apostaDisparades = new ArrayList<>();
        // PRimer de tot cerquem el màxim que s'han disparat totes les funcions.
        for(Regla r : regles) {
            for(Regla disparada : disparades){
                if(r.esDispara(disparada)){
                    // TODO : no ho tinc clar...
                }
            }
        }



        /**
         * Sortida del sistema : valor decimal referent a desfuzificar les normes de l'aposta.
         */

    }

    /**
     * Fuzzifiquem les càrregues d'aigua.
     * @param aigua el nombre de càrregues d'aigua que li queden
     * @return Les diferents  regles d'aigua que es dispararan.
     */
    private List<Pair<Aigua,Double>> fuzzyAigua(double aigua){
        List<Pair<Aigua, Double>> aiguaRules = new ArrayList<>();
        if(0 <= aigua && aigua < 3) aiguaRules.add(new Pair<Aigua, Double>(Aigua.POCA, (100 - (aigua * 100 / 2.0)) / 100);
        if(0 < aigua && aigua < 5) aiguaRules.add(new Pair<Aigua, Double>(Aigua.BE, (aigua == 2 || aigua == 3 ? 50 / 100.0 : 0.0)));
        if(3 <= aigua && aigua < 6) aiguaRules.add(new Pair<Aigua, Double>(Aigua.PLE, ((aigua - 3) * 100 / 2.0)/ 100));
        return aiguaRules;
    }

    /**
     * Fuzzifiquem el nombre de focs
     * @param focs Nombre de focs que té el target al voltant (contant-lo a ell)
     * @return Les diferents regles de focs que es dispararan.
     */
    private List<Pair<Focs, Double>> fuzzyFocs (double focs){
        List<Pair<Focs, Double>> focsRules = new ArrayList<>();
        if(0 <= focs && focs < 1) focsRules.add(new Pair(Focs.CAP, focs));
        if(1 <= focs && focs < 4) focsRules.add(new Pair(Focs.ALGUN, focs == 1 || 1 == 4 ? 0 : 0.5));
        if(2 <= focs && focs <= 6) focsRules.add(new Pair(Focs.QUANTS, focs == 2 || focs == 6 ? 0 : (focs == 4 ? 1.0 : 0.5)));
        if(4 <= focs && focs < 10) focsRules.add(new Pair(Focs.MOLTS, focs < 5 ? 0 : 1.0));
        return focsRules;
    }

    /** Fuzzifiquem distància :
     * Contemplem la distància més llarga com la distància que hi ha entre 0 i z, on :
     * 0 -> la casella nº 0
     * z -> la casella max(x)*max(y) on, max(x) = nombre màxim de caselles en eix x, i max(y) = nombre màxim de caselles en eix y.
     *
     * de :
     * 0 - z * 0.5 = proper
     * z * 0.25 - z * 0.75 = arribable
     * z * 0.5 - z = lluny
     */
    private List<Pair<Dist, Double>> fuzzyDist(double value){
        // Calculem la distància màxima que es pot recòrrer en linia recta dins de l'escenari.
        double z = Math.sqrt(Math.pow(escenari.dimx, 2) + Math.pow(escenari.dimy, 2));
        List<Pair<Dist, Double>> distRules = new ArrayList<>();

        if(0 <= value && value < (z *0.5)) distRules.add(new Pair(Dist.PROPER, (1 - ((z/2)*100 / value)) / 100));
        if(z * 0.25 <= value && value < z * 0.75) distRules.add(new Pair(Dist.ARRIBABLE, value == z*0.5? 1 : (z < z/2 ? ((value - z*0.25) * 100 / (z * 0.25)) : (1-((value - z*0.5) * 100 / (z * 0.5))))));
        if(z * 0.5 <= value && value < 1) distRules.add(new Pair(Dist.LLUNY, (((z-(z/2))/2)*100 / (value-z/2)) / 100));

        return distRules;
    }




}
