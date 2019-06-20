public interface Bidder {

    /**
     * Mètode que demana el que està disposat a pagar un dron per apagar un foc.
     * @param foc Posició
     * @return L'oferta
     */
    Offer getOffer(Foc foc, BidMethod method);

    /**
     * Mètode que demana el que està disposat a pagar un dron per protegir un arbre.
     * @param arbre Posició
     * @return L'oferta
     */
    Offer getOffer(Arbre arbre);

    /**
     * Mètode que demana el que està disposat a pagar un dron per emplenar el dipòsit.
     * @param diposit posició
     * @return L'oferta
     */
    Offer getOffer(Refill diposit);
}
