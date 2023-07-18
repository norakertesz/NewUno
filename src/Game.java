

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static Scanner input = new Scanner(System.in);
    private static PrintStream output = new PrintStream(System.out);
    private static ArrayList<Player> playersInGame;    //players in game list
    protected static CardDeck drawPile;   //ziehstapel
    public int round = 1;


    public static CardDeck getDrawPile() {
        return drawPile;
    }


    public static void setDiscardPile(CardDeck discardPile) {
        Game.discardPile = discardPile;
    }

    private static CardDeck discardPile;  //ablegestapel
    private Help help = new Help();
    private boolean clockweis = true;      //spielrichtung
    private static int currentPlayerNumber;
    private String winner;
    private boolean gameOver;
    //    private int round = 1;
//    private int session = 1;
//    private boolean exit = false;
    private static String newColor;
    protected Player currentPlayer;

    // ...

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    private void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }


    public static String getNewColor() {
        return newColor;
    }

    public static String setNewColor(String newColor) {
        newColor = newColor;
        return newColor;
    }

    public static CardDeck getDiscardPile() {
        return discardPile;
    }

    public static int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public void setCurrentPlayerNumber(int currentPlayerNumber) {
        this.currentPlayerNumber = currentPlayerNumber;
    }

    public Game() {
        //konstruktor
        playersInGame = new ArrayList<>();
        drawPile = new CardDeck();
        discardPile = new CardDeck();
    }


    public void start() {
        // help.printHelp();  //help am anfang anzeigen

        drawPile.createCards();
        discardPile.showAllCards();
        drawPile.shuffle();
        addPlayers();
        shareCards();   //karten austeilen
        layStartCard();  //erste karte auf dem tisch
        chooseInitialPlayer();

        if (playersInGame.get(currentPlayerNumber).getCardsInHand() == null) {
            System.out.println("Start new round");
            startNewRound();
        }
        do {
            cardChoice();
        } while (gameOver = true);

    }

    public Player chooseInitialPlayer() {
        //wählt die erste spieler random
        Random initialPlayer = new Random();
        int randomIndex = initialPlayer.nextInt(3);

        setCurrentPlayerNumber(randomIndex);

        Player first = playersInGame.get(randomIndex);
        System.out.println("HALLO FROM chooseInitialPlayer()");
        return first;
    }

    public static Player currentPlayer() {
        Player currentPlayer = playersInGame.get(getCurrentPlayerNumber());
        return currentPlayer;
    }

    public void addPlayers() {
        System.out.println("Please enter number of players: ");
        int num = input.nextInt();

        while (num < 1 || num > 4) {
            System.out.println("Max 4 players are allowed. Please choose between 1 and 4");
            num = input.nextInt();
        }
        input.nextLine();
        for (int i = 0; i < num; i++) {
            System.out.println("Enter your name: ");
            String name = input.nextLine();
            Player player = new Player(name, getCurrentPlayerNumber());
            output.println("Hello " + name);
            playersInGame.add(player);
        }
        System.out.println("Number of human players: " + playersInGame.size() + "\n");
        int botSize = 4 - playersInGame.size();
        for (int i = 0; i < botSize; i++) {
            String name = "Bot " + (i + 1);
            Bot bot = new Bot(name, getCurrentPlayerNumber());
            playersInGame.add(bot);
            System.out.println(name + " will be joining you as well");
        }
        System.out.println("HALLO FROM addPlayer();");
    }

    public void cardChoice() {
        currentPlayer = playersInGame.get(currentPlayerNumber);

        if (currentPlayer instanceof Bot) {
            Player currentPlayer = playersInGame.get(currentPlayerNumber);
            ((Bot) currentPlayer).botCardChoice();
            checkNextTurn();
        } else {

            Player currentPlayer = playersInGame.get(currentPlayerNumber);
            ;
            output.println("\nPlayer " + currentPlayer.getName() + " your turn");
            penalty();

            if (canPlayerDropACard()) {
                output.println("Your cards: " + "\n" + currentPlayer.showMyCards());
                output.println("Which card do you want to play?");
                discardPile.addToPile(currentPlayer.playerDropCard());
                //discardPile.addToPile(currentPlayer.playerDropCard());
                output.println("Card on Table: " + discardPile.getTopCard(discardPile));
                 didUsayUno();

            } else {
                output.println("Yout still don't have a card to play");
                output.println("\nCard on Table: " + discardPile.getTopCard(discardPile) + "\n");


            }
            checkNextTurn();
            newColor = null;
        }

    }
    //prüft ob der player kann eine karte legen und welche, wenn er kann nicht, zieht eine karte
    public void didUsayUno() {
        if (!checkUno()) {
            // Spieler hat mehr als eine Karte, daher keine Aktion erforderlich
            return;
        }

        System.out.println("You have only one card left!");

        String userInput = input.nextLine().trim().toLowerCase();
        if (userInput.equals("uno")) {
            System.out.println("You declared Uno!");
        } else {
            // Zwei Karten abheben, da der Spieler "Uno" nicht richtig angegeben hat
            for (int i = 0; i < 2; i++) {
                Game.drawPenaltyCard();
                Game.drawPenaltyCard();
                System.out.println("You did not say uno");
            }
        }
    }
    public boolean checkUno() {
        return currentPlayer.getCardsInHand().size() == 1;
    }

    public void shareCards() {
        System.out.println("Hallo from sharecards()");
        //karten austeilen - 7karte
        for (Player p : playersInGame) {
            for (int i = 0; i < 7; i++) {
                p.giveCard(drawPile.drawCard());  //eine karte von deck zu spieler
            }
        }
    }

    public Card layStartCard() {
        Card card;
        do {
            card = drawPile.getTopCard(drawPile);
        } while (!isNumberCard(card) && !isSpecialCard(card));

        output.println("First card is: " + card);
        discardPile.addToPile(card);
        return card;
    }

    private boolean isNumberCard(Card card) {
        String sign = card.getSign();
        return sign.matches("[0-9]");
    }
    private boolean isSpecialCard(Card card) {
        String sign = card.getSign();
        return sign.equals("ColorChange") || sign.equals("+4") || sign.equals("+2") || sign.equals("Stop") || sign.equals("Reverse");
    }

    public static boolean cardValidation(Card cardOnTheTable) {
        Card discardDeckCard = getDiscardPile().getTopCard(discardPile);


        if (cardOnTheTable.getColor().equals("Black")) {
            if (cardOnTheTable.getSign().equals("+4") || cardOnTheTable.getSign().equals("ColorChange")) {
                setColorIfColorChangeCard(cardOnTheTable);
                System.out.println("cardOnTheTable.getColor(): " + cardOnTheTable.getColor());
                System.out.println("New color: " + newColor);
                return true;
            }
        } else if (cardOnTheTable.getColor().equals(newColor)) {
            return true;
        } else if (cardOnTheTable.getColor().equals(discardDeckCard.getColor())) {
            return true;
        } else if (cardOnTheTable.getSign().equals(discardDeckCard.getSign())) {
            return true;
        } else {
            output.println("Error... Choose another card!");
            output.println("Card on Table: " + discardDeckCard);
            output.println("Gespielte Karte: " + cardOnTheTable);

            System.out.println("Discardpile:");
            discardPile.showAllCards();
        }
        penalty();


        return false;
    }


    public static void penalty() {
        //prüft, wie viel karte muss ein spieler heben
        Player currentPlayer = playersInGame.get(currentPlayerNumber);
        ;
        Card discardDeckCard = getDiscardPile().getTopCard(discardPile);

        if (discardDeckCard.getSign().equals("+2")) {
            output.println("But first you have to take 2 cards!");
            drawPenaltyCard();
            drawPenaltyCard();


        } else if (discardDeckCard.getSign().equals("+4")) {
            output.println("But first you have to take 4 cards!");
            drawPenaltyCard();
            drawPenaltyCard();
            drawPenaltyCard();
            drawPenaltyCard();
        }
    }

    public void checkNextTurn() {
        Player currentPlayer = playersInGame.get(currentPlayerNumber);
        ;
        //prüft, wer ist die nächste beim reverse, stop und beim normale karte
        Card discardDeckCard = getDiscardPile().getTopCard(discardPile);
        if (discardDeckCard.getSign().equals("Reverse")) {
            isCardIsReverse();
            System.out.println("Switch direction");
        } else if (discardDeckCard.getSign().equals("Stop")) {
            isCardStop();
            System.out.println("Out till next turn: " + currentPlayer);
        } else {
            isCardNormal();
        }
    }

    public int isCardNormal() {
        //prüft, ob die karte ist eine normale karte
        int currentPlayerIndex = getCurrentPlayerNumber();

        if (currentPlayerIndex == 0) {
            if (clockweis) {
                currentPlayerIndex++;
            } else {
                currentPlayerIndex = 3;
            }
        } else if (currentPlayerIndex == 3) {
            if (clockweis) {
                currentPlayerIndex = 0;
            } else {
                currentPlayerIndex = 2;
            }
        } else {
            if (clockweis) {
                currentPlayerIndex++;
            } else {
                currentPlayerIndex--;
            }
        }
        setCurrentPlayerNumber(currentPlayerIndex);
        return currentPlayerIndex;
    }

    public int isCardIsReverse() {
        //beim reverse karte prüft wer ist die nächste spieler
        int currentPlayerIndex = getCurrentPlayerNumber();

        if (currentPlayerIndex == 0) {
            if (clockweis) {
                currentPlayerIndex = 3;
                clockweis = false;
            } else {
                currentPlayerIndex = 1;
                clockweis = true;
            }
        } else if (currentPlayerIndex == 3) {
            if (clockweis) {
                currentPlayerIndex = 2;
                clockweis = false;
            } else {
                currentPlayerIndex = 0;
                clockweis = true;
            }
        } else {
            if (clockweis) {
                currentPlayerIndex--;
                clockweis = false;
            } else {
                currentPlayerIndex++;
                clockweis = true;
            }
        }
        setCurrentPlayerNumber(currentPlayerIndex);
        return currentPlayerIndex;
    }

    public int isCardStop() {
        //spielrichtung
        int currentPlayerIndex = getCurrentPlayerNumber();

        if (currentPlayerIndex == 0) {
            if (clockweis) {
                currentPlayerIndex = 2;
            } else {
                currentPlayerIndex = 2;
            }
        } else if (currentPlayerIndex == 3) {

            currentPlayerIndex = 1;

        } else if (currentPlayerIndex == 1) {
            if (clockweis) {
                currentPlayerIndex = 3;
            } else {
                currentPlayerIndex = 3;
            }
        } else if (currentPlayerIndex == 2) {
            System.out.println("Player 3 spielt stop für 4. aus, kommt wieder die erste Player.");
            currentPlayerIndex = 0;

        }
        setCurrentPlayerNumber(currentPlayerIndex);
        return currentPlayerIndex;
    }


    public static void drawPenaltyCard() {
        //wenn ein spieler bekommt ein +2 oder +4 karte, er muss abheben
        Player currentPlayer = playersInGame.get(currentPlayerNumber);

        currentPlayer.giveCard(drawPile.drawCard());
    }


    public static boolean canPlayerDropACard() {
        //automatisch prüft, kann der spieler eine karte legen, oder muss aufheben
        Card discardDeckCard = getDiscardPile().getTopCard(discardPile);
        Player currentPlayer = playersInGame.get(currentPlayerNumber);
        ;
        ArrayList<Card> hand = currentPlayer.getCardsInHand();
        boolean hasCard = false;

        for (Card card : hand) {
            if (((discardDeckCard.getColor().equals(card.getColor()) || discardDeckCard.getSign().equals(card.getSign())
                    || (card.getColor().equals("Black")) || (card.getColor().equals(getNewColor()))) && hand != null)) {
                hasCard = true;
                break;
            }
        }

        if (!hasCard) {
            output.println("Sorry, you dont have a card to play. You have to draw a card!");
            drawPenaltyCard();      //wenn der spieler hat keine richtige karte zum legen, zieht automatisch eine karte
            for (Card card : hand) {
                if (discardDeckCard.getColor().equals(card.getColor()) || discardDeckCard.getSign().equals(card.getSign())
                        || card.getColor().equals("Black") || card.getColor().equals(getNewColor())) {
                    hasCard = true;
                    break;
                }
            }
        }
        return hasCard;
    }

    public void initialize() {
        //Speieler und Karten anlegen - man initialisiert Sachen, die nur einmal intialisert werden müssen
        drawPile.createCards();
        addPlayers();
    }

    public void startNewRound() {
        round++;
        int sum = 0;
        System.out.println("Current player has no cards left. This round is over. Let´s start new round!");
        for (int i = 0; i < playersInGame.size(); i++) { // um die Punkte zusammenzuzählen
            if (playersInGame.get(i).getCardsInHand() == null) { // der Spieler hat die Runde gewonnen
                System.out.println("Spieler: " + i + " hat keine Karten mehr");
                System.out.println(playersInGame.get(i) + " has won: " + sum);
            } else {
                sum = sum + playersInGame.get(i).getHandCardPoints();
                System.out.println(playersInGame.get(i).getHandCardPoints());
            }
        }
    }


    public static void setColorIfColorChangeCard(Card cardOnTheTable) {
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Choose a color: Red, Blue, Green, Yellow");
            String colorWish = input.nextLine();
            if (colorWish.equalsIgnoreCase("Red")) {
                System.out.println("You wish for Red");
                newColor = "Red";
                return;
            } else if (colorWish.equalsIgnoreCase("Blue")) {
                System.out.println("You wish for Blue");
                newColor = "Blue";
                return;
            } else if (colorWish.equalsIgnoreCase("Green")) {
                System.out.println("You wish for Green");
                newColor = "Green";
                return;
            } else if (colorWish.equalsIgnoreCase("Yellow")) {
                System.out.println("You wish for Yellow");
                newColor = "Yellow";
                return;
            } else {
                System.out.println("This color is not valid!");
            }
        } while (true);
    }

    public static void setColorIfColorChangeCardForBots(Card cardOnTheTable, String colorWish) {
        if (colorWish.equalsIgnoreCase("Random")) {
            String[] colors = {"Red", "Blue", "Green", "Yellow"};
            int randomIndex = new Random().nextInt(colors.length);
            newColor = colors[randomIndex];
            System.out.println("You wish for " + newColor);
            return;
        }

        String[] validColors = {"Red", "Blue", "Green", "Yellow"};
        for (String color : validColors) {
            if (colorWish.equalsIgnoreCase(color)) {
                newColor = color;
                System.out.println("You wish for " + newColor);
                return;
            }
        }

        System.out.println("This color is not valid!");
    }

    public boolean winner() {
        for (Player p : playersInGame) {
            if (p.getCardsInHand().size() == 0) {
                winner = p.getName();
                gameOver = true;
            }
        }
        return false;
    }


    private void setGameOver(boolean b) {
        gameOver = true;
    }

    public boolean isGameOver() {
        for (Player player : playersInGame) {
            if (player.getCardsInHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Players=" + playersInGame;
    }
}
 