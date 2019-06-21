
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FuzzyBidder implements BidMethod {

    private  class BaseFuzzi{

        protected String name;
        protected Double value;

        BaseFuzzi(String name){
            this.name = name;
        }

        BaseFuzzi (String name, Double value){
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString(){
            return name;
        }
    }

    private class Dist extends BaseFuzzi {

        public static final String PROPER = "PROPER";
        public static final String ARRIBABLE = "ARRIBABLE";
        public static final String LLUNY = "LLUNY";

        Dist(String value) {
            super(value);
        }

        Dist(String name, Double value) {
            super(name, value);
        }
    }

    private class Aigua extends BaseFuzzi {
        public static final String POCA = "POCA";
        public static final String BE = "BE";
        public static final String PLE = "PLE";

        Aigua(String name) {
            super(name);
        }
        Aigua(String name, Double value) {
            super(name, value);
        }
    }

    private class Focs extends BaseFuzzi {
        public static final String CAP = "CAP";
        public static final String ALGUN = "ALGUN";
        public static final String QUANTS = "QUANTS";
        public static final String MOLTS = "MOLTS";


        Focs(String name) {
            super(name);
        }
        Focs(String name, Double value) {
            super(name, value);
        }
    }


    private class Aposta extends BaseFuzzi{
        public static final String RES = "RES";
        public static final String SUAU = "SUAU";
        public static final String MIG = "MIG";
        public static final String FORT = "FORT";


        Aposta(String name) {
            super(name);
            this.value = 0.0;
        }
        Aposta(String name, Double value) {
            super(name, value);

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


        @Override
        public boolean equals(Object o) {
            if(o == null) return  false;
            else if(!(o instanceof Regla)) return false;
            else {
                Regla r = (Regla)o;
                return this.dist.name.equals(r.dist.name) && this.aigua.name.equals(r.aigua.name) && this.focs.name.equals(r.focs.name);
            }
        }

        public Aposta getAposta(){
            return aposta;
        }
        public void setAposta(Aposta a){
            a.value = Math.min(Math.min(dist.value, focs.value), aigua.value);
            this.aposta = a;
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
        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.CAP),   new Aigua(Aigua.POCA), new Aposta(Aposta.RES)));
        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.ALGUN), new Aigua(Aigua.POCA), new Aposta(Aposta.RES)));
        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.QUANTS),new Aigua(Aigua.POCA), new Aposta(Aposta.RES)));
        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.MOLTS), new Aigua(Aigua.POCA), new Aposta(Aposta.RES)));

        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.CAP),    new Aigua(Aigua.BE), new Aposta(Aposta.RES)));
        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.ALGUN),  new Aigua(Aigua.BE), new Aposta(Aposta.SUAU)));
        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.QUANTS), new Aigua(Aigua.BE), new Aposta(Aposta.SUAU)));
        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.MOLTS),  new Aigua(Aigua.BE), new Aposta(Aposta.MIG)));

        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.CAP),    new Aigua(Aigua.PLE), new Aposta(Aposta.RES)));
        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.ALGUN),  new Aigua(Aigua.PLE), new Aposta(Aposta.MIG)));
        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.QUANTS), new Aigua(Aigua.PLE), new Aposta(Aposta.MIG)));
        regles.add(new Regla(new Dist(Dist.LLUNY), new Focs(Focs.MOLTS),  new Aigua(Aigua.PLE), new Aposta(Aposta.FORT)));


        regles.add(new Regla(new Dist(Dist.ARRIBABLE),new Focs(Focs.CAP),     new Aigua(Aigua.POCA),new Aposta(Aposta.RES)));
        regles.add(new Regla(new Dist(Dist.ARRIBABLE),new Focs(Focs.ALGUN),   new Aigua(Aigua.POCA),new Aposta(Aposta.SUAU)));
        regles.add(new Regla(new Dist(Dist.ARRIBABLE),new Focs(Focs.QUANTS),  new Aigua(Aigua.POCA),new Aposta(Aposta.SUAU)));
        regles.add(new Regla(new Dist(Dist.ARRIBABLE),new Focs(Focs.MOLTS),   new Aigua(Aigua.POCA),new Aposta(Aposta.RES)));

        regles.add(new Regla(new Dist(Dist.ARRIBABLE), new Focs(Focs.CAP),    new Aigua(Aigua.POCA), new Aposta(Aposta.RES)));
        regles.add(new Regla(new Dist(Dist.ARRIBABLE), new Focs(Focs.ALGUN),  new Aigua(Aigua.POCA), new Aposta(Aposta.SUAU)));
        regles.add(new Regla(new Dist(Dist.ARRIBABLE), new Focs(Focs.QUANTS), new Aigua(Aigua.POCA), new Aposta(Aposta.SUAU)));
        regles.add(new Regla(new Dist(Dist.ARRIBABLE), new Focs(Focs.MOLTS),  new Aigua(Aigua.POCA), new Aposta(Aposta.RES)));

        regles.add(new Regla(new Dist(Dist.ARRIBABLE), new Focs(Focs.CAP),    new Aigua(Aigua.PLE), new Aposta(Aposta.RES)));
        regles.add(new Regla(new Dist(Dist.ARRIBABLE), new Focs(Focs.ALGUN),  new Aigua(Aigua.PLE), new Aposta(Aposta.SUAU)));
        regles.add(new Regla(new Dist(Dist.ARRIBABLE), new Focs(Focs.QUANTS), new Aigua(Aigua.PLE), new Aposta(Aposta.SUAU)));
        regles.add(new Regla(new Dist(Dist.ARRIBABLE), new Focs(Focs.MOLTS),  new Aigua(Aigua.PLE), new Aposta(Aposta.MIG)));


        regles.add(new Regla(new Dist(Dist.PROPER),new Focs(Focs.CAP),    new Aigua(Aigua.POCA), new Aposta(Aposta.RES)));
        regles.add(new Regla(new Dist(Dist.PROPER),new Focs(Focs.ALGUN),  new Aigua(Aigua.POCA), new Aposta(Aposta.FORT)));
        regles.add(new Regla(new Dist(Dist.PROPER),new Focs(Focs.QUANTS), new Aigua(Aigua.POCA), new Aposta(Aposta.SUAU)));
        regles.add(new Regla(new Dist(Dist.PROPER),new Focs(Focs.MOLTS),  new Aigua(Aigua.POCA), new Aposta(Aposta.RES)));

        regles.add(new Regla(new Dist(Dist.PROPER), new Focs(Focs.CAP),   new Aigua(Aigua.BE), new Aposta(Aposta.RES)));
        regles.add(new Regla(new Dist(Dist.PROPER), new Focs(Focs.ALGUN), new Aigua(Aigua.BE), new Aposta(Aposta.FORT)));
        regles.add(new Regla(new Dist(Dist.PROPER), new Focs(Focs.QUANTS),new Aigua(Aigua.BE), new Aposta(Aposta.FORT)));
        regles.add(new Regla(new Dist(Dist.PROPER), new Focs(Focs.MOLTS), new Aigua(Aigua.BE), new Aposta(Aposta.MIG)));

        regles.add(new Regla(new Dist(Dist.PROPER), new Focs(Focs.CAP),   new Aigua(Aigua.PLE), new Aposta(Aposta.RES)));
        regles.add(new Regla(new Dist(Dist.PROPER), new Focs(Focs.ALGUN), new Aigua(Aigua.PLE), new Aposta(Aposta.MIG)));
        regles.add(new Regla(new Dist(Dist.PROPER), new Focs(Focs.QUANTS),new Aigua(Aigua.PLE), new Aposta(Aposta.FORT)));
        regles.add(new Regla(new Dist(Dist.PROPER), new Focs(Focs.MOLTS), new Aigua(Aigua.PLE), new Aposta(Aposta.FORT)));

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
        List<Dist> distRules = fuzzyDist(robot.getEuclidian(target));
        List<Aigua> aiguaRules = fuzzyAigua(robot.getPle());
        List<Focs> focsRules = fuzzyFocs(escenari.focsVoltant(target, 1));


        /**
         * Tinc tots els possibles valors fuzificats i els percentatges que han evaluat.
         */
        HashMap<String, Regla> reglesDisparades = new HashMap<>();
        for (Dist dist : distRules) {
            for (Aigua aigues : aiguaRules) {
                for (Focs focs : focsRules) {
                    Regla r = new Regla(dist, focs, aigues);
                    Aposta a = regles.get(regles.indexOf(r)).getAposta();
                    r.setAposta(new Aposta(a.name, a.value));
                    if (reglesDisparades.containsKey(r.getAposta().name)) {
                        if (reglesDisparades.get(r.getAposta().name).aposta.value < r.getAposta().value) {
                            reglesDisparades.put(r.getAposta().name, r);
                        }
                    } else
                        reglesDisparades.put(r.getAposta().name, r);

                }
            }
        }

        List<Double> valorsVAlids = new ArrayList<>();
        double res = reglesDisparades.containsKey(Aposta.RES) ? reglesDisparades.get(Aposta.RES).aposta.value : 0.0;
        double suau = reglesDisparades.containsKey(Aposta.SUAU) ? reglesDisparades.get(Aposta.SUAU).aposta.value : 0.0;
        double mig = reglesDisparades.containsKey(Aposta.MIG) ? reglesDisparades.get(Aposta.MIG).aposta.value : 0.0;
        double fort = reglesDisparades.containsKey(Aposta.FORT) ? reglesDisparades.get(Aposta.FORT).aposta.value : 0.0;

        for (int i = 0; i < 11; i++) {
            if (i < 4) {
                // Miro regles per res i per suau
                if(i == 0) {
                    valorsVAlids.add(getDefuzzi(res, suau, 1, 0));
                } else if (i == 1) {
                    valorsVAlids.add(getDefuzzi(res, suau, 0.7, 0.3));
                } else if (i == 2) {
                    valorsVAlids.add(getDefuzzi(res, suau, 0.4, 0.6));
                } else { // i== 3
                    valorsVAlids.add(getDefuzzi(res, suau, 0.1, 0.9));
                }
            } else if (i >= 4 && i < 7) {
                // Miro regles per suau i per mig
                if (i == 4) {
                    valorsVAlids.add(getDefuzzi(suau, mig, 0.7, 0.3));
                } else if (i == 5) {
                    valorsVAlids.add(getDefuzzi(suau, mig, 0.4, 0.6));
                } else { // i == 6
                    valorsVAlids.add(getDefuzzi(suau, mig, 0.1, 0.9));
                }
            } else {
                if (i == 7) {
                    valorsVAlids.add(getDefuzzi(mig, fort, 0.7, 0.3));
                } else if (i == 8) {
                    valorsVAlids.add(getDefuzzi(mig, fort, 0.4, 0.6));
                } else if (i == 9) {
                    valorsVAlids.add(getDefuzzi(mig, fort, 0.1, 0.9));
                } else { // i == 10
                    valorsVAlids.add(getDefuzzi(mig, fort, 0, 1));
                }
            }
        }


        int i = 0;
        double num = 0;
        double den = 0;

        for (Double d : valorsVAlids){
            num += d*i;
            den += d;
            i++;
        }

        double result = den == 0 ? 0.0 : num / den;
        if(result > robot.money)
            return robot.money;
        else{
            return result;
        }
    }

    public double getDefuzzi(double a, double b, double c, double d){
        if(a == 0 && b == 0){
            return 0;
        }
        else {
            if (a > b) {
                if (a < c) {
                    return a;
                } else {
                    return c;
                }
            } else {
                if (b <= d) {
                    return b;
                } else {
                    return d;
                }
            }
        }
    }

        /*

        Aposta res = new Aposta(Aposta.RES, 0.0);
        Aposta suau = new Aposta(Aposta.SUAU, 0.0);
        Aposta mig = new Aposta(Aposta.MIG, 0.0);
        Aposta fort = new Aposta(Aposta.FORT, 0.0);

        /**
         * Miro els màxims que s'han disparat totes les sortides.
         *
        for(Regla r : reglesDisparades){
            if(r.aposta.name.equals(Aposta.RES)){
                if(res.value < r.aposta.value){
                    res = r.aposta;
                }
            }
            else if(r.aposta.name.equals(Aposta.SUAU)){
                if(suau.value < r.aposta.value){
                    suau = r.aposta;
                }
            }
            else if(r.aposta.name.equals(Aposta.MIG)){
                if(mig.value < r.aposta.value){
                    mig = r.aposta;
                }
            }
            else {
                if (fort.value < r.aposta.value) {
                    fort = r.aposta;
                }
            }
        }*/
        /**
         * - Obtenir el conjunt difús segons :
         *
         * RES -> 0 - 2.5
         * SUAU -> 0 - 7.5
         * MIG -> 2.5 - 10
         * FORT -> 7.5 - 10
         *
         */

        /*// TODO : S'ha de fer bé, no està bé. S'ha de passar a % d'activació en funció de la funció.
        List<Double> conjunt = new ArrayList<>();
        if(res.value <= suau.value){
            conjunt.add(suau.value); // El valor per el punt 1
            conjunt.add(suau.value); // El valor per el punt 2
        }
        else {
            conjunt.add(res.value); // El valor per el punt 1
            conjunt.add(res.value); // El valor per el punt 2
        }

        if(suau.value <= mig.value){
            conjunt.add(mig.value);
            conjunt.add(mig.value);
            conjunt.add(mig.value);
            conjunt.add(mig.value);
            conjunt.add(mig.value);
        }
        else {
            conjunt.add(suau.value);
            conjunt.add(suau.value);
            conjunt.add(suau.value);
            conjunt.add(suau.value);
            conjunt.add(suau.value);
        }


        if(mig.value <= fort.value){
            conjunt.add(fort.value);
            conjunt.add(fort.value);
            conjunt.add(fort.value);
        }
        else{
            conjunt.add(mig.value);
            conjunt.add(mig.value);
            conjunt.add(mig.value);
        }

        /**
         * - Desfuzifiquem mitjançant el mètode del centre de gravetat.
         */




    /**
     * Fuzzifiquem les càrregues d'aigua.
     * @param aigua el nombre de càrregues d'aigua que li queden
     * @return Les diferents  regles d'aigua que es dispararan.
     */
    private List<Aigua> fuzzyAigua(double aigua){
        List<Aigua> aiguaRules = new ArrayList<>();
        if(0 <= aigua && aigua < 3) aiguaRules.add(new Aigua(Aigua.POCA, (1 - (aigua/ 2.0))));
        if(0 < aigua && aigua < 5) aiguaRules.add(new Aigua(Aigua.BE, (aigua == 2 || aigua == 3 ? 0.5 : 0.0)));
        if(3 <= aigua && aigua < 6) aiguaRules.add(new Aigua(Aigua.PLE, ((aigua - 3)/ 2.0)));
        return aiguaRules;
    }

    /**
     * Fuzzifiquem el nombre de focs
     * @param focs Nombre de focs que té el target al voltant (contant-lo a ell)
     * @return Les diferents regles de focs que es dispararan.
     */
    private List<Focs> fuzzyFocs (double focs){
        List<Focs> focsRules = new ArrayList<>();
        if(0 <= focs && focs < 1) focsRules.add(new Focs(Focs.CAP, focs));
        if(1 <= focs && focs < 4) focsRules.add(new Focs(Focs.ALGUN, focs == 1 || 1 == 4 ? 0 : 0.5));
        if(2 <= focs && focs <= 6) focsRules.add(new Focs(Focs.QUANTS, focs == 2 || focs == 6 ? 0 : (focs == 4 ? 1.0 : 0.5)));
        if(4 <= focs && focs < 10) focsRules.add(new Focs(Focs.MOLTS, focs < 5 ? 0 : 1.0));
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
    private List<Dist> fuzzyDist(double value){
        // Calculem la distància màxima que es pot recòrrer en linia recta dins de l'escenari.
        double z = Math.sqrt(Math.pow(escenari.dimx, 2) + Math.pow(escenari.dimy, 2));
        List<Dist> distRules = new ArrayList<>();
        if(0 <= value && value < (z *0.5)) distRules.add(new Dist(Dist.PROPER, (1 - (value / (z/2)))));
        if(z * 0.25 <= value && value < z * 0.75) distRules.add(new Dist(Dist.ARRIBABLE, value == z*0.5 ? 1 : value < z*0.5 ? ((value - z*0.25)/ (z * 0.25)) : (1-((value - z*0.5) / (z * 0.25)))));
        if(z * 0.5 <= value && value < 1) distRules.add(new Dist(Dist.LLUNY, ((value-z*0.5)) / (z*0.5)));

        return distRules;
    }




}
