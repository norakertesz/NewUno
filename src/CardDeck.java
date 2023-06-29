import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    private ArrayList<Card> cards;
//private static ArrayList<Card> discardPile = new ArrayList<>();

    public CardDeck() {   //default konstruktor
        this.cards = new ArrayList<>();
      //  createCards();    //creating carddeck
    }

    //carddeck erstellen mit alle aktionskarten
    public void createCards() {
        //blaue karte erstellen
        for (int i = 0; i < 2; i++) {  //stückzahl
            cards.add(new Card("Reverse", "Blue", 20));
            cards.add(new Card("+2", "Blue", 20));
            cards.add(new Card("Stop", "Blue", 20));
            for (int j = 1; j < 10; j++) {  //zahlen
                cards.add(new Card(Integer.toString(j), "Blue", j));
            }
        }

        for (int i = 0; i < 2; i++) {
            //grüne karte erstellen
            cards.add(new Card("Reverse", "Green", 20));
            cards.add(new Card("+2", "Green", 20));
            cards.add(new Card("Stop", "Green", 20));
            for (int j = 1; j < 10; j++) {
                cards.add(new Card(Integer.toString(j), "Green", j));
            }
        }
        for (int i = 0; i < 2; i++) {
            //rote karte erstellen
            cards.add(new Card("Reverse", "Red", 20));
            cards.add(new Card("+2", "Red", 20));
            cards.add(new Card("Stop", "Red", 20));
            for (int j = 1; j < 10; j++) {
                cards.add(new Card(Integer.toString(j), "Red", j));
            }
        }

        for (int i = 0; i < 2; i++) {
            //gelbe karte erstellen
            cards.add(new Card("Reverse", "Yellow", 20));
            cards.add(new Card("+2", "Yellow", 20));
            cards.add(new Card("Stop", "Yellow", 20));
            for (int j = 1; j < 10; j++) {
                cards.add(new Card(Integer.toString(j), "Yellow" , j));
            }
        }
        for (int b = 0; b < 1; b++) {
            //null karte erstellen
            cards.add(new Card("0", "Blue", 0));
            cards.add(new Card("0", "Green", 0));
            cards.add(new Card("0", "Red", 0));
            cards.add(new Card("0", "Yellow", 0));
        }

        for (int b = 0; b < 4; b++) {
            //color change karten erstellen
            cards.add(new Card("+4", "Black", 50));
            cards.add(new Card("ColorChange", "Black", 50));
        }
       // Collections.shuffle(cards);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void showAllCards() {
        for (Card card: cards) {
            System.out.println(card.toString());
        }
    }

    public Card drawCard() {

        if (cards.isEmpty()) {
            //wenn carddeck ist leer
            //TODO: resetcarddeck
            return null;
        }
        //karten aufheben
//        System.out.println("Hallo von drawCard: es gibt " + cards.size() + " Karten in dem Deck VOR dem Heben.");
        Card tmp_card =  cards.remove(cards.size() - 1);
//        System.out.println("Hallo von drawCard: es gibt " + cards.size() + " Karten in dem Deck NACH dem Heben.");
        return tmp_card;
    }




    public void addToPile(Card playerDropCard) {
        //gespielte karte zum neue stapel
//        System.out.println("Hallo von addtodiscardpile: es gibt " + discardPile.size() + " Karten in dem Deck VOR dem Heben.");
        cards.add(0,playerDropCard);
    }

    public Card getTopCard(CardDeck deck) {
        Card topCard = deck.getCards().get(0);
        return topCard;
    }

    @Override
    public String toString() {
        return "CardDeck{" +
                "cards=" + cards
                ;
    }
}
