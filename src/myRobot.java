
/*
 * Exemple de robot simple
 */

/**
 * @author Llorenç
 */
class myRobot extends Robot  {
    
    //No modificar el constructor
    myRobot(double x,double y, Escenari e){
        //Inicialitzem la superclasse
        super(x, y, e);
    }
    
    @Override
    public void mourerobot(){
        Position target = algorithm.moure(this);
        System.out.println(target);
        setPos(getX()+anarX(target.x),getY()+anarY(target.y));
        apaga();
        emplena();
    }

    @Override
    public Offer getOffer(Foc foc, BidMethod method) {
        return new Offer(this, foc, new FuzzyBidder(escena).getBid(this, foc));
    }

}
