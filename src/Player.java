import java.util.ArrayList;
import java.util.List;

public class Player{

    private int playersNumber = 4;
    private String name;
    private final List<Card> cardsInHand = new ArrayList<>();

    public Player(int playersNumber, String name) {
        this.playersNumber = playersNumber;
        this.name = name;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void giveCard(Card card) {
        cardsInHand.add(card);
    }

    public void dropCard(Card card) {
        cardsInHand.remove(card);
    }

    public int countMyCards() {
        return cardsInHand.size();
    }

    @Override
    public String toString() {
        return
                playersNumber + " " +
                        name+cardsInHand;
    }


}
