

import java.io.PrintStream;
import java.util.*;

public class Game {

    //ATTRIBUTEN
    private static Scanner input = new Scanner(System.in);
    private static PrintStream output = new PrintStream(System.out);
    private static ArrayList<Player> playersInGame;    //players in game list
    protected static CardDeck drawPile;   //ziehstapel
    private static boolean endOfRound = false;
    public static final String SUNNY = "\u001B[38;2;102;153;204m";
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[38;2;0;255;0m";
    public static final String PINK = "\u001B[38;2;255;192;203m";
    public static final String RED = "\u001B[38;2;255;0;0m";
    public static final String YELLOW = "\u001B[38;2;255;255;0m";
    public int round = 1;
    private boolean clockweis = true;//spielrichtung
    protected static Player winner;
    private static String newColor;
    protected Player currentPlayer;
    private static int currentPlayerNumber;
    private static CardDeck discardPile;  //ablegestapel
    private Help help = new Help();


//GETTER AND SETTER

    public static void setDiscardPile(CardDeck discardPile) {
        Game.discardPile = discardPile;
    }

    public static void setEndOfRound(boolean endOfRound) {
        endOfRound = endOfRound;
    }

    public static void setWinner(Player winner) {
        Game.winner = winner;
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

    public static boolean isEndOfRound() {
        return true;
    }

    //Constructor
    public Game() {
        playersInGame = new ArrayList<>();
        drawPile = new CardDeck();
        discardPile = new CardDeck();
    }


    //HAUPTMETHODE
    public void run() {
        help.printHelp();  //help am anfang anzeigen
        drawPile.createCards();
        discardPile.showAllCards();
        drawPile.shuffle();
        addPlayers();

        do {
            gameFlow();
        } while (!endOfTournament());
        System.out.println("GAME OVER");
    }


    //1 ROUND
    public void resetValuesForNewRound() {
        setWinner(null);
        setNewColor(null);

        for (Player p : playersInGame) {
            if (p.getCardsInHand().size() > 0 && p != winner) {
                List<Card> cardsToRemove = new ArrayList<>(p.getCardsInHand());
                for (Card c : cardsToRemove) {
                    discardPile.addToPile(c);
                    p.getCardsInHand().remove(c);
                }
            }
        }

        List<Card> cardsToAddToDrawPile = new ArrayList<>(getDiscardPile().getCards());
        for (Card c : cardsToAddToDrawPile) {
            drawPile.addToPile(c);
            getDiscardPile().getCards().remove(c);
        }

        drawPile.shuffle();
    }


    //Karte austeilen
    public void shareCards() {
        //karten austeilen - 7karte
        for (Player p : playersInGame) {
            for (int i = 0; i < 7; i++) {
                p.giveCard(drawPile.drawCard());  //eine karte von deck zu spieler
            }
        }
    }


    //First card
    public Card layStartCard() {
        Card card;
        do {
            card = drawPile.getTopCard(drawPile);
        } while (!isNumberCard(card) && !isSpecialCard(card));

        output.println(SUNNY + "First card is: " + card + RESET);
        discardPile.addToPile(card);
        return card;
    }


    //Players in game
    public Player chooseInitialPlayer() {
        //wählt die erste spieler random
        Random initialPlayer = new Random();
        int randomIndex = initialPlayer.nextInt(3);
        setCurrentPlayerNumber(randomIndex);
        Player first = playersInGame.get(randomIndex);

        return first;
    }

    public static Player currentPlayer() {
        Player currentPlayer = playersInGame.get(getCurrentPlayerNumber());
        return currentPlayer;
    }

    public void addPlayers() {
        System.out.println(YELLOW+"How many human players should play ?"+RESET);
        System.out.println(PINK+"Please enter number of players: "+RESET);
        int num;

        while (true) {
            String inputStr = input.nextLine();

            try {
                num = Integer.parseInt(inputStr);

                if (num >= 1 && num <= 4) {
                    break; // Wenn die Eingabe gültig ist, die Schleife verlassen
                } else {
                    System.out.println("Max 4 players are allowed. Please choose between 1 and 4");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a valid NUMBER between 1 and 4!");
            }
        }
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
            Player bot = new Bot(name, getCurrentPlayerNumber());
            playersInGame.add(bot);
            System.out.println(name + " will be joining you as well");
        }
    }


    //Gameplay
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
                output.println(SUNNY + "Card on Table: " + discardPile.getTopCard(discardPile) + RESET);
                didUsayUno();

            } else {
                output.println("Yout still don't have a card to play");
                output.println(SUNNY + "\nCard on Table: " + discardPile.getTopCard(discardPile) + "\n" + RESET);


            }
            checkNextTurn();
            newColor = null;
            Game.winner();
            if (currentPlayer == Game.winner) {
                Game.calculateWinnerPoints();
                System.out.println(currentPlayer.getName() + " is the winner of this round!");
            }

            if (playersInGame.get(currentPlayerNumber).getCardsInHand() == null) {
                System.out.println("Start new round");
                startNewRound();
            }
        }

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
            output.println(SUNNY + "Card on Table: " + discardDeckCard + RESET);
            output.println("Gespielte Karte: " + cardOnTheTable);

        }
        penalty();


        return false;
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
            System.out.println(YELLOW+"Switch direction"+RESET);
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
            System.out.println(RED+"Player 3 spielt stop für 4. aus, kommt wieder die erste Player."+RESET);
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


    //UNO
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
            }
            System.out.println("You did not say uno");
        }
    }

    public boolean checkUno() {
        return currentPlayer.getCardsInHand().size() == 1;
    }

    public void startNewRound() {
        round++;
        int sum = 0;
        System.out.println("Current player has no cards left. This round is over. Let´s start new round!");
        for (int i = 0; i < playersInGame.size(); i++) { // um die Punkte zusammenzuzählen
            if (playersInGame.get(i).getCardsInHand() == null) { // der Spieler hat die Runde gewonnen
                System.out.println("Player: " + i + " has no more card.");
                System.out.println(playersInGame.get(i) + " has won: " + sum);
            } else {
                sum = sum + playersInGame.get(i).getHandCardPoints();
                System.out.println(playersInGame.get(i).getHandCardPoints());
            }
        }
    }


    public static boolean winner() {
        boolean isWinner = false;
        for (Player p : playersInGame) {
            if (p.getCardsInHand().size() == 0) {
                winner = p;
                isWinner = true;
                setEndOfRound(true);
            }
        }
        return isWinner;
    }


    public static int calculateWinnerPoints() {
        ArrayList<Card> loserCards = new ArrayList<>();
        for (Player p : playersInGame) {
            if (!p.equals(winner)) {
                for (Card c : p.getCardsInHand()) {
                    loserCards.add(c);
                }
            }
        }

        int winnerPoints = 0;

        for (Card c : loserCards) {
            winnerPoints = winnerPoints + c.getValue();
        }

        winner.setPlayerPoints(winner.getPlayerPoints() + winnerPoints);
        System.out.println();
        System.out.println(PINK + "-*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*-");
        System.out.println("                                 You WIN " + winner.getName());
        System.out.println("-*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*--*/*-" + RESET);
        System.out.println("Your points for this round is: " + winnerPoints);
        return winnerPoints;
    }

    public boolean endOfTournament() {
        resetValuesForNewRound();
        boolean gameOver = false;
        for (Player p : playersInGame) {
            if (p.getPlayerPoints() >= 500) {
                gameOver = true;
                System.out.println(RED+p.getName() + "YOU"+SUNNY+"ARE"+YELLOW+ "THE"+PINK+ "CHAMPION!!!!"+RESET);
            }
        }
        return gameOver;
    }

    public void gameFlow() {
        shareCards();   //karten austeilen
        layStartCard();  //erste karte auf dem tisch
        chooseInitialPlayer();
        do {
            cardChoice();

        } while (winner() == false);
        System.out.println("END OF ROUND");
    }

    @Override
    public String toString() {
        return "Players=" + playersInGame;
    }
}
 