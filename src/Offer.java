public class Offer extends Voyage {

    private double water;

    public Offer(Robot from, Position target, double water) {
        super(from, target);
        this.water = water;
    }

    public double getWater(){
        return water;
    }
}
