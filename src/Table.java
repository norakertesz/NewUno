public class Table {
    Card cardOnTable;

    public Card getCardOnTable() {
        return cardOnTable;
    }

    public void layCard(Card card) {
        cardOnTable = card;
    }

    public void layStartCard(Card card) {
        cardOnTable = card;
    }

}
