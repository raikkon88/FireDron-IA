public class Offer extends Voyage {

    private Double bid;

    public Offer(Robot from, Position target, Double bid) {
        super(from, target);
        this.bid = bid;
    }

    @Override
    public int compareTo(Voyage o) {
        Offer offer = (Offer)o;
        return bid.compareTo(offer.bid) * -1;
    }

    public Double getBid(){
        return bid;
    }

    @Override
    public String toString() {
        return "BID = " + bid + " -> " +super.toString();
    }
}
