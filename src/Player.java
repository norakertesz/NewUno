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
    private Game game;

    public Player(int playersNumber, String name, Game game) {
        this.name = name;
        this.playersNumber = playersNumber;
        gameOver = false;
        this.game = game;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
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

                System.out.println("Please enter a number between 1 and  " + cardsInHand.size() );
                continue;
            }


            if (choice > 0 && choice <= cardsInHand.size()) {
                if (game.cardValidation(cardsInHand.get(choice - 1))) {
                    return cardsInHand.remove(choice - 1);
                }
            } else {
                System.out.println("Please enter a number between 1 and  " + cardsInHand.size());
            }
        } while (true);
    }

    public void takeCardBack(Card card) {
        cardsInHand.add(card);
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
        return "Player" + playersNumber + ": " + name + " Cards in hand:" + cardsInHand + "\n";
    }
}