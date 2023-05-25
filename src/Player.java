import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    Scanner input = new Scanner(System.in);
    protected String name;
    protected int playersNumber = 4;
    private final ArrayList<Card> cardsInHand = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private String winner;
    private boolean gameOver;

    public Player(int playersNumber, String name) {
        this.name = name;
        this.playersNumber = playersNumber;
        gameOver = false;
    }

    //karte zu hand
    public void giveCard(Card card) {
        cardsInHand.add(card);
    }


    //karte auf dem tisch legen
    public Card playerDropCard() {
        int choice;   //kann wählen welche karte(wievielte) vom reihe(1-7)

        do {

            String a = input.nextLine();

            try {
                choice = Integer.parseInt(a);

            } catch (NumberFormatException e) {

                System.out.println("Bitte eine Nummer zwischen 1 und " + cardsInHand.size() + " eingeben:");
                continue;
            }


            if (choice > 0 && choice <= cardsInHand.size()) {
                return cardsInHand.remove(choice - 1);
            } else {
                System.out.println("Bitte eine Nummer zwischen 1 und " + cardsInHand.size() + " eingeben:");
            }
        } while (true);

    }

//    public boolean sayUno() {
//        boolean uno = false;
//        if (cards.size() ==1) {
//            uno = true;
//        } return uno;
//    }

//    public String winner() {
//        for (Player p : players) {
//            if (p.cardsInHand.size() == 0) {
//                winner = p.getName();
//                gameOver = true;
//            }
//        } return winner;
//    }

    public int countMyCards() {   //wie viel karte habe ich
        return cardsInHand.size();
    }

    public String showMyCards() {
        String myCards = "";
        int i = 1;
        for (Card showMyCards : cardsInHand) {
            myCards += i + " -> " + showMyCards.toString() + "\n";
            i++;
        }
        return myCards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }


    @Override
    public String toString() {
        return "Player" + playersNumber + ": " + name + " Karten in Hand: " + cardsInHand + "\n";
    }
}