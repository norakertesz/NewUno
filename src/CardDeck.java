import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    private final ArrayList<Card> cards;
    private ArrayList<Card> discardPile = new ArrayList<>();

    public CardDeck() {   //default konstruktor
        cards = new ArrayList<>();
        createCards();    //creating carddeck
    }

    @Override
    public String toString() {
        return "CardDeck" + cards;
    }

    //carddeck erstellen mit alle aktionskarten
    public void createCards() {
        //blaue karte erstellen
        for (int i = 0; i < 2; i++) {  //stückzahl
            cards.add(new Card("Reverse", "Blue", 20));
            cards.add(new Card("+2", "Blue", 20));
            cards.add(new Card("Stop", "Blue", 20));
            for (int j = 1; j < 10; j++) {  //zahlen
                cards.add(new Card(Integer.toString(j), "Blue", i));
            }
        }

        for (int i = 0; i < 2; i++) {
            //grüne karte erstellen
            cards.add(new Card("Reverse", "Green", 20));
            cards.add(new Card("+2", "Green", 20));
            cards.add(new Card("Stop", "Green", 20));
            for (int j = 1; j < 10; j++) {
                cards.add(new Card(Integer.toString(j), "Green", i));
            }
        }
        for (int i = 0; i < 2; i++) {
            //rote karte erstellen
            cards.add(new Card("Reverse", "Red", 20));
            cards.add(new Card("+2", "Red", 20));
            cards.add(new Card("Stop", "Red", 20));
            for (int j = 1; j < 10; j++) {
                cards.add(new Card(Integer.toString(j), "Red", i));
            }
        }

        for (int i = 0; i < 2; i++) {
            //gelbe karte erstellen
            cards.add(new Card("Reverse", "Yellow", 20));
            cards.add(new Card("+2", "Yellow", 20));
            cards.add(new Card("Stop", "Yellow", 20));
            for (int j = 1; j < 10; j++) {
                cards.add(new Card(Integer.toString(j), "Yellow" , i));
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
        Collections.shuffle(cards);
    }


    public Card drawCard() {

        if (cards.isEmpty()) {
            //wenn carddeck ist leer
            resetDeck();
        }
        //karten aufheben
        return cards.remove(cards.size() - 1);
    }

    public void resetDeck() {
        //discardpile erstellen von discarded cards
        cards.clear();
        for (Card discardedCard : discardPile) {
            cards.add(discardedCard);
        }
        discardPile.clear();
        Collections.shuffle(cards);
    }

    public void addToDiscardPile(Card playerDropCard) {
        //gespielte karte zum neue stapel
        cards.add(playerDropCard);
    }

    public Card getDropCard() {
        //eine karte ausspielen
        return cards.get(cards.size()-1);
    }


}
