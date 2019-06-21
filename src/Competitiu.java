
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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

    /**
     * De tots els viatges que se li han assignat, és a dir totes les ofertes que ha guanyat a la subhasta
     * Ell mateix decideix quin és el foc que ha de començar apagant.
     * @param dron Ent que ha de decidir quin és el foc que apagarà.
     * @return La posició que **el mateix dron ha decidit apagar**.
     */
    @Override
    public Position moure(Robot dron) {

        // TODO : El cas en què xoquen no està resolt.

        if(dron.getPle() == 0){
            return dron.escena.Diposit;
        }
        if(dron.viatges.size() > 0){
            return dron.viatges.first().getTarget();
        }
        else{
            return new Position(dron.x, dron.y);
        }
        /*
        List<Position> contrincants = new ArrayList<>(dron.escena.Robots);
        contrincants.remove(dron);

        while(dron.viatges.size() > 0 && dron.viatges.first().getTarget().ocupada(contrincants))
            dron.viatges.remove(dron.viatges.first());

        if(dron.viatges.size() == 0){
            if(dron.escena.Focs.size() > 0){
                // Si li queden focs per apagar peró no ha guanyat cap subhasta que vagi al que tingui més a prop i que no estigui ocupat
                TreeSet<Voyage> fires = new TreeSet<>();
                for(Foc f : dron.escena.Focs){
                    Voyage v = new Voyage(dron, f);
                    if(!v.getTarget().ocupada(contrincants)){
                        fires.add(v);
                    }
                }
                if(fires.size() > 0)
                    return fires.first().getTarget();
                else
                    return dron.escena.Diposit;
            }
            else
                return dron.escena.Diposit;
        }
        else
            return dron.viatges.first().getTarget();*/

        /*Position finalPosition = null;
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
        
        return finalPosition;*/
    }
    
}
