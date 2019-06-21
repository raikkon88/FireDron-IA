
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
    }
    
}
