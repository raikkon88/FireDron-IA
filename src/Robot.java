import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.ImageIcon;

/**
 * @author Llorenç
 */
abstract class Robot extends Position implements VoyageSubject, VoyageObserver, Bidder{

    private double energia;
    private double punts;
    protected TreeSet<Voyage> viatges;
    protected List<VoyageObserver> observers;
    protected Algorithm algorithm;
    protected double money;
    
    public Escenari escena;
    
    double ple;
    
    //indica si ja ens hem mogut
    private boolean mogut;

    
    public Robot(double xin,double yin, Escenari esc){
        super(xin, yin);
        this.ple =5;
        this.escena = esc;
        this.energia = 0;
        this.punts = 0;
        viatges = new TreeSet<>();
        observers = new ArrayList<>();
        algorithm = escena.getAlgorithm();
        money = 10;
    }

    public void init(){
        observers = new ArrayList<>();
        viatges = new TreeSet<>();
    }

    public void setPos(double xin,double yin){
        //Fa que el robot es mogui, la posicio no pot estar ocupada perres exepuant la paperera
        //Els robots no poden avançar més de una posicio
        //si la posició que es demana esta ocupada el robot no es moura
        
        //Comprovem si la pos esta ocupada
        if( !ocupada(xin,yin) && !mogut){
            //si intenten sortir de la taula corretjim la posiciò
            if(xin<0){
                xin =0;
            }
            if(yin<0){
                yin =0;
            }
            
            if(xin>escena.dimx-1){
                xin =escena.dimx-1;
            }
            if(yin>escena.dimy-1){
                yin =escena.dimy-1;
            }
            
            //nomes permetem avançar de 1 en 1
            if(x+1==xin || x-1==xin){
              x=xin;  
              mogut = true;
            }
            
            if(y+1==yin || y-1==yin){
              y=yin; 
              mogut = true;
            }
            
            if(mogut){
                //hem gastat energia
                energia++;
            }
            
        }
    }

    public double getMoney() {
        return money;
    }

    public boolean canPay(double d){
        return money >= d;
    }

    public void pay(double d)  {
        money = money - d;

    }

    public void initMoney() {
        this.money = 10;
    }


    public double getPle(){
        return ple;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public boolean ocupada(double xin, double yin){
        //comprova si la posició esta ocupada
        boolean  trobat = false;
        int i = 0;
        while( i!= escena.Robots.size() & ! trobat){

          Robot r = escena.Robots.get(i);
          trobat = (int)r.getX() == xin & (int)r.getY() == yin ;//&  xin >= 0 & yin >= 0 & xin < (int)escena.dimx & yin  < (int)escena.dimy ;
          i++;
        }
        
        return trobat;
    }
    
    public boolean esfoc(double xin, double yin){
        boolean  trobat = false;
        int i = 0;
        while( i!= escena.Focs.size() & ! trobat){
            Foc r = escena.Focs.get(i);
            trobat = (int)r.x == xin & (int)r.y == yin & r.estat==1 ;
            i++;
        }
        return trobat;
    }
    
    public void moure(){
        mogut = false;
        algorithm = escena.getAlgorithm();
        mourerobot();
    }
    
    public void apaga(){
        //Per apagar el foc hi ha d'estar a sobre
        if(ple>0 && esfoc(this.x,this.y)){
            //podem recollir
            boolean  trobat = false;
            int i = 0;
            while( i!= escena.Focs.size() & ! trobat){
                Foc r = escena.Focs.get(i);
                trobat = (int)r.x == this.x & (int)r.y == this.y;
                i++;
            }
            
            //Apaguem el foc
            escena.Focs.get(i-1).apaga();//
            
            //Augmentem en 1 la puntuació
            punts++;
            
            //buidem diposit 1
            ple--;
            
        }
    }
    
    public void emplena(){
        //per emplenar hem de essrat sobre el diposit
        if(x==escena.Diposit.x && y==escena.Diposit.y){
            //estem a sobra el diposit -> emplenem
            ple = 5;
        }
    }
    
    public double getEnergia(){
        //l'energia gastada
        return energia;
    }
    
    public double getPunts(){
        //les basures recollides
        return punts;
    }
    
    public ImageIcon getImatge(){
       String dir = System.getProperty("user.dir")+ "/Imatges/robot" + Integer.toString((int)ple) + ".jpg";
       return new ImageIcon( dir);
    }
    
    public double anarX(double xin){
        double xanar =0;
        if(x < xin){
            xanar = 1;
        }
        if(x > xin){
            xanar = -1;
        }
        return xanar;
    }
    
    public double anarY(double yin){
        double yanar =0;
        if(y < yin){
            yanar = 1;
        }
        if(y > yin){
            yanar = -1;
        }
        return yanar;
    }
    
    /**
     * @return el foc més proper per anar a apagar.
     * // TODO : CAnviar per el foc que té més possibilitats de que sigui apagat (quantificar el foc en funció de l'estat.)
     */
    protected List<Voyage> getFiresOrderedByEuclidianDistance(){
        List<Voyage> viatges = new ArrayList();
        for(Foc f : escena.Focs){
            viatges.add(new Voyage(this, f));
        }
        Collections.sort(viatges);
        return viatges;
    }
    
    @Override
    public boolean equals(Object eq){
        if(eq == null) return this == null;
        else if(!(eq instanceof Robot)) return false;
        else {
            Robot r = (Robot)eq;
            return r.x == x && r.y == y;
        }
    }
    
    @Override
    public void addObserver(VoyageObserver robot) {
        observers.add(robot);
    }

    @Override
    public void update(Voyage voyage) {
        viatges.add(voyage);
    }

    @Override
    public void notify(Voyage voyage) {
        for(VoyageObserver o : observers){
            o.update(voyage);
        }
    }
            
    
    abstract public void mourerobot();
}
