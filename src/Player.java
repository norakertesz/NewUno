

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Player {
    private Scanner input = new Scanner(System.in);
    protected String name;
    protected int playersNumber = 4;
    private final ArrayList<Card> cardsInHand = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    protected static int playerPoints = 0;


    public Player(String name, int playersNumber) {
        this.name = name;
        this.playersNumber = playersNumber;

    }
    public static int getPlayerPoints() {
        return playerPoints;
    }

    public static void setPlayerPoints(int playerPoints) {
        Player.playerPoints = playerPoints;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void giveCard(Card card) {
        //karte zu hand - heben
        cardsInHand.add(card);
    }

    public Card playerDropCard() {
        //karte auf dem tisch legen
        Card card=null;
        int choice;   //kann wählen welche karte(wievielte) vom reihe(1-7)

        do {
            String a = input.nextLine();
            try {
                choice = Integer.parseInt(a);

            } catch (NumberFormatException nfe) {
                System.out.println("Error!...  Please enter a NUMBER between 1 and " + cardsInHand.size());
                continue;
            }
            if (choice > 0 && choice <= cardsInHand.size()) {
                if (Game.cardValidation(cardsInHand.get(choice - 1))) {
                    card = cardsInHand.remove(choice - 1);
                    return card;
                }
            } else {
                System.out.println("Error... Please enter a NUMBER between 1 and " + cardsInHand.size() + " eingeben:");
            }

        }
        while (Game.isEndOfRound());
        return card;
    }

    public String showMyCards() {
        //welche karten hat der spieler
        String myCards = "";
        int i = 1;
        for (Card shoMyCards : cardsInHand) {
            myCards += i + " -> " + shoMyCards.toString() + "\n";
            i++;
        }
        return myCards;
    }

    public String getName() {
        return name;
    }

    public int getHandCardPoints() {
        int points = 0;
        for (Card c : cardsInHand) {
            points += c.getValue();

        }
        return points;
    }

    @Override
    public String toString() {
        return "Player" + playersNumber + ": " + name + " Karten in Hand: " + cardsInHand + "\n";
    }
}
 