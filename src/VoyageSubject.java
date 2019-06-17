/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MarcSanchez
 */
public interface VoyageSubject {
    
    public void addObserver(VoyageObserver robot);
    
    public void notify(Voyage voyage);
    
}
