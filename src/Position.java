/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;

/**
 *
 * @author MarcSanchez
 */
public class Position {
    double x;
    double y;
    
     protected Position(double xin, double yin){
        x=xin;
        y=yin;
    }
     
    /**
     * Retorna la distància euclidiana entre p i this
     * @param p posició amb la que es vol realitzar la distància euclidiana.
     */
    protected double getEuclidian(Position p){
        return Math.sqrt(Math.pow(Math.abs(y - p.y),2) + Math.pow(Math.abs(x - p.x), 2));
    }
    
    @Override
    public boolean equals(Object o){
        if(o == null) return this == null;
        else if(!(o instanceof Position)) return false;
        else {
            Position p = (Position)o;
            return p.x == x && p.y == y;
        }
    }

    /**
     * Verifica si la posició està o no ocupada per algun element del llistat elements.
     * @param elements Llistat d'elements que poden ocupar una posició.
     * @return cert si un element a elements està a la posició this
     */
    public boolean ocupada(List<Position> elements){
        for(Position p : elements){
            if(p.x == x && p.y == y){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return "[x,y] -> " + "[" + x + "," + y + "]";
    }
}
