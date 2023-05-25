import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    private final ArrayList<Card> cards = new ArrayList<>();

    public CardDeck() { //Default constructor
        createCards();
    }

    public void createCards() {
        for (int i = 0; i < 2; i++) {
            cards.add(new Card(" reverse","blue"));
            cards.add(new Card("+2","blue"));
            cards.add(new Card(" stop","blue"));
            for (int j = 1; j < 10; j++) {
                cards.add(new Card(Integer.toString(j), "blue"));
            }
        }
        for (int i = 0; i < 2; i++) {
            cards.add(new Card(" reverse","green"));
            cards.add(new Card("+2","green"));
            cards.add(new Card(" stop","green"));
            for (int j = 1; j < 10; j++) {
                cards.add(new Card(Integer.toString(j), "green"));
            }
        }
        for (int i = 0; i < 2; i++) {
            cards.add(new Card(" reverse","red"));
            cards.add(new Card("+2","red"));
            cards.add(new Card(" stop","red"));
            for (int j = 1; j < 10; j++) {
                cards.add(new Card(Integer.toString(j), "red"));
            }
        }
        for (int i = 0; i < 2; i++) {
            cards.add(new Card(" reverse","yellow"));
            cards.add(new Card("+2","yellow"));
            cards.add(new Card(" stop","yellow"));
            for (int j = 1; j < 10; j++) {
                cards.add(new Card(Integer.toString(j), "yellow"));
            }
        }


        for (int i = 0; i < 1; i++) {
            cards.add(new Card(Integer.toString(0), "blue"));
            cards.add(new Card(Integer.toString(0), "green"));
            cards.add(new Card(Integer.toString(0), "red"));
            cards.add(new Card(Integer.toString(0), "yellow"));
        }

        for (int b = 0; b < 4; b++) {
            cards.add(new Card("+4", "black"));
            cards.add(new Card("color change", "black "));
        }


        Collections.shuffle(cards);
    }

    public Card drawCard(){
        return cards.remove(cards.size()-1);
    }


    public void addToCards(Card playerDropCard) {
        cards.add(playerDropCard);
    }

    public Card getDropCard(){
        return cards.get(cards.size()-1);
    }

    @Override
    public String toString() {
        return "CardDeck:" + cards ;
    }


}




