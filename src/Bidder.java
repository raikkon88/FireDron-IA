public interface Bidder {

    /**
     * Mètode que demana el que està disposat a pagar un dron per apagar un foc.
     * @param foc Posició
     * @return L'oferta
     */
    Offer getOffer(Foc foc, BidMethod method);
}
