import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    Scanner input = new Scanner(System.in);
    private int playersNumber = 4;
    private String name;
    private final List<Card> cardsInHand = new ArrayList<>();
    private ArrayList<Player> players=new ArrayList<>();
    private String winner;
    boolean gameOver;

    public Player(int playersNumber, String name) {
        this.playersNumber = playersNumber;
        this.name = name;
        gameOver = false;
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


    public int countMyCards() {
        return cardsInHand.size();
    }

    public Card playerDropCard() {
        int choice = input.nextInt(); //kann wählen welche karte(wievielte) vom reihe(1-7)

        if (choice > 0 || choice < cardsInHand.size()) {
            return cardsInHand.remove(choice - 1);
        }

        return null;
    }

    public String winner() {
        for (Player p :players) {
            if (p.cardsInHand.size() == 0) {
                winner = p.getName();
                gameOver = true;
            }
        }return winner;
    }

    @Override
    public String toString() {
        return
                playersNumber + " " +
                        name + cardsInHand + "\n";
    }


}
