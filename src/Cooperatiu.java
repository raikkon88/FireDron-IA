
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marc
 */
public class Cooperatiu implements Algorithm{

    @Override
    public Position moure(Robot dron) {
        Position finalPosition = null;
        
        // Instanciem tots els observers
        for(Robot robot : dron.escena.Robots){
            if(!robot.equals(this)){
                dron.observers.add(robot);
            }
        }
        
        if(dron.ple == 0){
            //si no aigua anem al diposit a carregar   
            finalPosition = new Position(dron.escena.Diposit.x, dron.escena.Diposit.y);
        }
        else{
            // obtinc els possibles viatges que pot fer el robot ordenats per importància.
            List<Voyage> possiblesViatges = dron.getFiresOrderedByEuclidianDistance();
            
            for(Voyage v : possiblesViatges){
                System.out.println(v);
            }
            
            Voyage target = null;
            // Miro que no hi vagi un altre robot.
            while(!possiblesViatges.isEmpty() && dron.viatges.contains(possiblesViatges.get(0))){
                possiblesViatges.remove(possiblesViatges.get(0));
            }
            if(possiblesViatges.isEmpty()){
                // NO queden focs on no hi vagi ningú
                if(!dron.viatges.isEmpty()){
                    // si ja està tot pillat ves a cardar-li la feina a un altre si en queda
                    target = dron.viatges.first();
                }
                else{
                    // No em moc de lloc. Ja no queden focs per apagar. 
                    target = new Voyage(dron, new Position(dron.x, dron.y));
                }
            }
            else {
                // Queden focs on no hi va ningú.
                target = possiblesViatges.get(0);
            }
            
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
            
            finalPosition = new Position(target.getXTarget(), target.getYTarget());
            dron.notify(target);
        }
        dron.observers = new ArrayList<>();
        dron.viatges = new TreeSet<>();
        return finalPosition;
    }
    
}
