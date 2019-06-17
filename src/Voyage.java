
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MarcSanchez
 */
public class Voyage implements Comparable<Voyage>{
    
    private Position target;
    private Robot from;
    private double nextX;
    private double nextY;
    
    public Voyage(Robot from, Position target){
        this.target = target;
        this.from = from;
        
        if(from.x < target.x){
            nextX = 1 + from.x;
        }
        if(from.x > target.x){
            nextX = -1 + from.x;
        }
        
        if(from.y < target.y){
            nextY = 1 + from.y;
        }
        if(from.y > target.y){
            nextY = -1 + from.y;
        }
    }
        
    /***
     * Només comparo amb les euclidianes de moment, la idea és comparar també donant pes al nombre d'arbres que tenen per cremar. 
     * El que té més arbres per cremar al voltant ha d'anar primer que els que tenen menys arbres per cremar. 
     */
    @Override
    public int compareTo(Voyage o) {       
        return (calcul(this)).compareTo(calcul(o));        
    } 
    
    private Double calcul(Voyage o){
        Double dist = o.target.getEuclidian(o.from);
        Double arbres = from.escena.nombreArbresVoltant(o.target.x, o.target.y);
        if(arbres == 0){
            dist += 13;
        }
        else if(arbres > 0 && arbres < 3){
            dist += 3;
        }
        else if(arbres >= 3 && arbres < 5){
            dist += 2;
        }
        return dist;
    }
    
    /** 
     * Una mínima predicció de crash entre 2 drons.
     * mirem : 
     * - Si coincidim en trajectoria
     * Les dues següents estan pensades quan l'un o l'altre estan parats apagant foc.
     * - Si la trajectoria d'un va cap on hi ha un altre
     * - Si la trajectoria de l'altre va cap on n'hi ha un. 

     */
    public List<Position> crashes(Voyage o){
        List<Position> crashes = new ArrayList<Position>();
        if(nextX == o.nextX && nextY == o.nextY) crashes.add(new Position(o.nextX,o.nextY));
        if(nextX == o.from.x && nextY == o.from.y) crashes.add(new Position(o.from.x, o.from.y));
        if(from.x == o.nextX && from.y == o.nextY) crashes.add(new Position(from.x, from.y));
        //if(nextX == o.nextX && nextY == o.nextY) crashes.add(new Position(nextX, nextY));
        return crashes;
    }
    
    public Robot getRobot(){
        return from;
    }
    
    public double getXTarget(){
        return target.x;
    }
    
    public double getYTarget(){
        return target.y;
    }
    
    public double getXStep(){
        return nextX;
    }
    
    public double getYStep(){
        return nextY;
    }
    
    /** 
     * En funció d'una serie de posicions conflictives intentem desviar el dron.
     */
    public void setNextPosition(List<Position> crashes){
        List<Voyage> desviaments = new ArrayList<Voyage>();
        desviaments.add(new Voyage(new myRobot(from.x, from.y+1, this.from.escena), target));
        desviaments.add(new Voyage(new myRobot(from.x, from.y-1, this.from.escena), target));
        desviaments.add(new Voyage(new myRobot(from.x-1, from.y, this.from.escena), target));
        desviaments.add(new Voyage(new myRobot(from.x+1, from.y, this.from.escena), target));
        desviaments.add(new Voyage(new myRobot(from.x+1, from.y+1, this.from.escena), target));
        desviaments.add(new Voyage(new myRobot(from.x+1, from.y-1, this.from.escena), target));
        
        desviaments.add(new Voyage(new myRobot(from.x-1, from.y+1, this.from.escena), target));
        desviaments.add(new Voyage(new myRobot(from.x-1, from.y-1, this.from.escena), target));
        Collections.sort(desviaments);
        Voyage desv = desviaments.get(0);
        while(!desviaments.isEmpty() && crashes.contains(desv.from)){
            desviaments.remove(0);
            desv = desviaments.get(0);
        }
        nextX = desv.from.x;
        nextY = desv.from.y;
    }
     
    public boolean equals(Object o ){
        if(o == null) return this == null;
        else if(!(o instanceof Voyage)) return false;
        else {
            Voyage v = (Voyage)o;
            return v.target.equals(target);
        }
    }
    
    @Override
    public String toString(){
        return "Decidit : " + target.x + ":" + target.y  + " | VALUE : "  + calcul(this);
    }
    
}
