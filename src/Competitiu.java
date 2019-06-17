
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marc
 */
public class Competitiu implements Algorithm {

    @Override
    public Position moure(Robot dron) {
        Position finalPosition = null;
        if(dron.ple == 0){
            //si no aigua anem al diposit a carregar   
            finalPosition = new Position(dron.escena.Diposit.x, dron.escena.Diposit.y);
        }
        else{
            // obtinc els possibles viatges que pot fer el robot ordenats per importància.
            List<Voyage> possiblesViatges = dron.getFiresOrderedByEuclidianDistance();
            if(possiblesViatges.isEmpty()){
                // Ja s'ha acabat el joc.. 
               finalPosition = new Position(dron.escena.Diposit.x, dron.escena.Diposit.y); 
            }
            else{
                Voyage target = possiblesViatges.get(0);
               
                // Haig de mirar que no xoquin al avançar amb altres mosques.
                List<Position> crashes = new ArrayList<Position>();
                for(Voyage v : dron.viatges){
                    List<Position> derivedCrashes = v.crashes(target);
                    for(Position p : derivedCrashes){
                        crashes.add(p);
                    }
                }
                // Un cop tinc tots els punts on entren en conflictes els avançaments canvio la x o la y en funció de si ho requereix o si no. 
                if(!crashes.isEmpty()){
                    // que em pilli el primer camí més curt cap a l'objectiu que no xoqui amb ningú.
                    target.setNextPosition(crashes);
                }
                // Si no hi ha hagut conflictes al avançar deixo que facin.
                finalPosition = new Position(target.getXTarget(), target.getYTarget());
            }
        }
        
        return finalPosition;
    }
    
}
