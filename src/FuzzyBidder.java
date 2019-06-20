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

    }


    private List<Dist> fuzzyDist(double value){
        // Calculem la distància màxima que es pot recòrrer en linia recta dins de l'escenari.
        double z = Math.sqrt(Math.pow(escenari.dimx, 2) + Math.pow(escenari.dimy, 2));
        List<Dist> distRules = new ArrayList<>();

        if( 0 <= value && value < (z *0.5)) distRules.add(Dist.PROPER);
    }




}
