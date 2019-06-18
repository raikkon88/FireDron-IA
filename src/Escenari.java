
import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Llorenç
 */
public class Escenari {
    private boolean competitiu;
    private Auctioner auctioner;
    java.util.List<Robot> Robots;
    java.util.List<Arbre> Arbres;
    int numArbrescremats;
    java.util.List<Foc> Focs;
    Refill Diposit;
    double dimx;
    double dimy;
   
    boolean fi = false;
    
    
    public Escenari(){
        Robots = new ArrayList<Robot>();
        Arbres = new ArrayList<Arbre>();
        Focs = new ArrayList<Foc>();
        Diposit = new Refill(0,0);
        competitiu = true;
        // Activem el mecanisme de subhasta.
        auctioner = new Auctioner(this);
    }
    
    public void Crea(File fitxer) throws FileNotFoundException, IOException{
        //LLegir fitxer d'inicialització i crear l'escenari
        CSVReader reader = new CSVReader(new FileReader(fitxer), ';', '\'');
        List<String []> nextLine;
        nextLine = reader.readAll();
        this.dimx = nextLine.size();
        numArbrescremats=0;
        
        for(int i = 0;nextLine.size()!=i;i++){//x
            for(int j =0;nextLine.get(i).length != j;j++){//y
                this.dimy = nextLine.get(i).length;
                for(int k =0;nextLine.get(i)[j].length() != k;k++){
                   //System.out.println(nextLine.get(i)[j].charAt(k));
                   if(nextLine.get(i)[j].charAt(k) == 'A' || nextLine.get(i)[j].charAt(k) == 'a'){
                       Arbres.add(new Arbre(i,j));
                       
                   }
                   if(nextLine.get(i)[j].charAt(k) == 'F' || nextLine.get(i)[j].charAt(k) == 'f'){
                       Focs.add(new Foc(i,j));
                   }
                   if(nextLine.get(i)[j].charAt(k) == 'R' || nextLine.get(i)[j].charAt(k) == 'r'){
                       Robots.add(new myRobot(i,j,this));
                   }
                   if(nextLine.get(i)[j].charAt(k) == 'O' || nextLine.get(i)[j].charAt(k) == 'o'){
                       Diposit = new Refill(i,j);
                   }
                }
            }
        }
    }


    public void changeMode(){
        competitiu = !competitiu;
        if(competitiu) auctioner = new Auctioner(this);
        else auctioner = null;
    }
    
    public Algorithm getAlgorithm(){
        if(competitiu) return new Competitiu();
        return new Cooperatiu();
    }
    
    public double nombreArbresVoltant(double x, double y){
        double arbres = 0;
        arbres += hiHaArbre(x + 1, y) ? 1 : 0;
        arbres += hiHaArbre(x + 1, y + 1) ? 1 : 0;
        arbres += hiHaArbre(x + 1, y - 1) ? 1 : 0;
        arbres += hiHaArbre(x , y + 1) ? 1 : 0;
        arbres += hiHaArbre(x , y - 1) ? 1 : 0;
        arbres += hiHaArbre(x - 1, y) ? 1 : 0;
        arbres += hiHaArbre(x - 1, y + 1) ? 1 : 0;
        arbres += hiHaArbre(x - 1, y - 1) ? 1 : 0;
        return arbres;
    }
    
    private boolean hiHaArbre(double x, double y){
        boolean hiEs = false;
        for(Arbre a : Arbres){
            hiEs = a.x == x && a.y == y;
            if(hiEs) {
                break;
            }
        }
        return hiEs;
    }
    
    public boolean Step(){

        // Si s'implementa el tipus subhasta (estipulat per defecte a aquesta segona entrega)
        if(auctioner != null){
            auctioner.startAuction();
        }

        //Passar l'step a cada 1 dels robots
        for( Robot r : Robots){
            r.moure();
        }

        // Un cop el robot s'ha mogut buidem les estructures de dades que li pertoquen per què pugui tornar a inicialitzar a la següent volta.
        for( Robot r : Robots){
            r.init();
        }

        
        for( int i =Focs.size()-1; i!= -1; i--){
                
                 if(Focs.get(i).estat == 0){
                     //ni ha un foc apafat
                     Random rand = new Random();
                     int n = rand.nextInt(100);
                     
                     if (n<10){//10% de tornar a encendre
                        Focs.get(i).encen();
                     }
                     if (n>80){//20% de apagar tornar a encendre
                        Focs.remove(i);
                     }
                 }else{
                     Random rand = new Random();
                     int n = rand.nextInt(100);
                     if (n<10){//10% de propagar en arbres
                        double x=Focs.get(i).x;
                        double y=Focs.get(i).y;
                        int j=0;
                        boolean trobat=false;
                        
                        while(j<Arbres.size() & !trobat){
                         trobat= -1<=x-Arbres.get(j).x & x-Arbres.get(j).x<=1 & -1<=y-Arbres.get(j).y & y-Arbres.get(j).y<=1;
                         j=j+1;
                         }
                        
                        if(trobat){
                            x=Arbres.get(j-1).x;
                            y=Arbres.get(j-1).y;
                            Focs.add(new Foc(x,y));
                            Arbres.remove(j-1);
                            numArbrescremats++;
                        }
                     }
                 }
            }
        return Focs.isEmpty();
    }
    
    public double getX(){
        return dimx;
    }
    public double getY(){
        return dimy;
    }
}
