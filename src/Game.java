import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private static ArrayList<Player> playersInGame = new ArrayList<>();
    private CardDeck cardDeck = new CardDeck();   //original carddeck
    private static CardDeck discardPile = new CardDeck();  //ablegestapel
    private Table table = new Table();
    private boolean clockweis = true;      //spielrichtung
    private static int currentPlayerNumber;
    static Scanner input = new Scanner(System.in);
    private String newColor;
    Card firstcard=layStartCard();
    private static PrintStream output = new PrintStream(System.out);
    private Help help = new Help();


    public Game() {
        this.playersInGame = new ArrayList<>();
    }

    //GETTER & SETTER METHODS
    public String getNewColor() {
        return newColor;
    }

    public void setClockweis(boolean clockweis) {
        this.clockweis = clockweis;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public static  setNewColor(String newColor) {
        newColor = newColor;
    }

    public static CardDeck getDiscardPile(
    ) {
        return discardPile;
    }

    public static int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public void setCurrentPlayerNumber(int currentPlayerNumber) {
        this.currentPlayerNumber = currentPlayerNumber;
    }

    // START*****************************************************************************************************
    public void start() {
        help.printHelp();
        //spieler im main erstellen - addPlayerToPlayerList
        shareCards();   //karten austeilen
        layStartCard();  //erste karte auf dem tisch
        System.out.println("First card " + discardPile.getDropCard());
        chooseInitialPlayer();//erste Spieler generieren
        cardChoice();
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
}
    public void shareCards() {
        for (Player p : playersInGame) {
            for (int i = 0; i < 7; i++) {
                p.giveCard(cardDeck.drawCard());  //eine karte von deck zu spieler
            }
        }
    }

    public Card layStartCard() {
        Card card = discardPile.drawCard();
        discardPile.addToCards(card);

        if (card.getSign().equals("ColorChange") || card.getSign().equals("+4")) {
            // Wenn die erste Karte eine Farbwahlkarte ist, wird die Farbe zufällig ausgewählt
            Random random = new Random();
            String[] colors = {"red", "green", "yellow", "blue"};
            int randomIndex = random.nextInt(colors.length);
            String startColor = colors[randomIndex];
            newColor = startColor;
            output.println("First card is: " + card);
            output.println("First color is: " + startColor);
        } else {
            output.println("First card is: " + card);
        }

        return card;
    }
    public Player chooseInitialPlayer() {
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

    // PLAY*************************************************************************************************
    public void cardChoice() {
        do {
            Player currentPlayer = currentPlayer();
            System.out.println("Player " + currentPlayer.getName() + " your turn");
            penalty();
            if (canPlayerDropACard()) {
                System.out.println("Your cards: " + "\n" + currentPlayer.showMyCards());
                System.out.println("Which card do you want to play?");
                discardPile.addToCards(currentPlayer.playerDropCard());
                System.out.println("Card on Table: " + discardPile.getDropCard());
                colorChange(); //Colorchange wieder auf null setzen!!!!
            } else {

                System.out.println("You still don't have a card to play.");
                System.out.println("Card on Table: " + discardPile.getDropCard());
                System.out.println(currentPlayer.showMyCards());
            }
            checkNextTurn();
        } while (table != null);
    }

    //Spielregeln***************************************************************************************************
    //Kartenregeln
    public static boolean cardValidation(Card card) {
        //Falls die Karte auf dem Tisch die gleiche Farbe oder Zeichen haben wird die Karte gelegt
        Card discardDeckCard = getDiscardPile().getDropCard();
        Player currentPlayer = currentPlayer();

        if (card.getColor().equals("black")) {
            return true;
        } else if (discardDeckCard.getColor().equals(card.getColor()) || card.getColor().equals("black") || discardDeckCard.getColor().equals("black")) {
            return true;
        } else if (discardDeckCard.getSign().equals(card.getSign()) || card.getSign().equals("+4") || discardDeckCard.getSign().equals("+4")) {
            return true;
        } else if (discardDeckCard.getSign().equals(card.getSign()) || card.getSign().equals("color change") || discardDeckCard.getSign().equals("color change")) {
            return true;
        } else {
            System.out.println("Error: Choose the right card!");
            System.out.println("Card on Table: " + discardDeckCard);
        }
        if (discardDeckCard.getSign().equals("+2") || discardDeckCard.getSign().equals("+4")) {
            if (discardDeckCard.getSign().equals("+2")) {
                currentPlayer.giveCard(card);
                currentPlayer.giveCard(card);
                discardPile.addToCards(card);
                discardPile.addToCards(card);
            } else if (discardDeckCard.getSign().equals("+4")) {
                currentPlayer.giveCard(card);
                currentPlayer.giveCard(card);
                currentPlayer.giveCard(card);
                currentPlayer.giveCard(card);
                discardPile.addToCards(card);
                discardPile.addToCards(card);
                discardPile.addToCards(card);
                discardPile.addToCards(card);
            }
        }
        return false;
    }

    //Strafe +2/+4
    public void penalty() {
        Player currentPlayer = currentPlayer();
        Card discardDeckCard = getDiscardPile().getDropCard();

        if (discardDeckCard.getSign().equals("+2")) {
            System.out.println("But first you have to take 2 cards!");
            drawPenaltyCard();
            drawPenaltyCard();
            currentPlayer.showMyCards();
        } else if (discardDeckCard.getSign().equals("+4")) {
            System.out.println("But first you have to take 4 cards!");
            drawPenaltyCard();
            drawPenaltyCard();
            drawPenaltyCard();
            drawPenaltyCard();
            currentPlayer.showMyCards();
        } else {
        }

    }

    //Color change
    public void colorChange() {
        Card discardDeckCard = getDiscardPile().getDropCard();
        if (discardDeckCard.getSign().equals("color change")) {
            System.out.println("Give me a new color: ");
            String newColor = input.nextLine();
            setNewColor(newColor);//*

        }

    }

    //wer ist der nächste Spieler?->
    public void checkNextTurn() {
        Card discardDeckCard = getDiscardPile().getDropCard();
        if (discardDeckCard.getSign().equals("reverse")) {
            isCardIsReverse();
        } else if (discardDeckCard.getSign().equals("stop")) {
            isCardStop();
        } else {
            isCardNormal();
        }
    }


    //Spielrichtung
    public int isCardNormal() {//Richtung bleibt
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


    public int isCardIsReverse() { //die Richtung ändert sich
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

    public int isCardStop() { // der nächste Spieler scheidet aus und der nächste Spieler kommt
        int currentPlayerIndex = getCurrentPlayerNumber();

        if (currentPlayerIndex == 0) {
            if (clockweis) {
                currentPlayerIndex = 2;
            } else {
                currentPlayerIndex = 2;
            }
        } else if (currentPlayerIndex == 3) {
            if (clockweis) {
                currentPlayerIndex = 1;
            } else {
                currentPlayerIndex = 1;
            }
        } else if (currentPlayerIndex == 1) {
            if (clockweis) {
                currentPlayerIndex = 3;
            } else {
                currentPlayerIndex = 3;
            }
        } else {
            if (clockweis) {
                currentPlayerIndex = 0;
            } else {
                currentPlayerIndex = 0;
            }
        }

        setCurrentPlayerNumber(currentPlayerIndex);
        return currentPlayerIndex;
    }

    //Kann der Spieler eine Karte ablegen?
    public boolean canPlayerDropACard() {
        Card discardDeckCard = getDiscardPile().getDropCard();
        Player currentPlayer = currentPlayer();
        ArrayList<Card> hand = currentPlayer.getCardsInHand();
        boolean hasCard = false;


        for (Card card : hand) {
            if (discardDeckCard.getColor().equals(card.getColor()) || discardDeckCard.getSign().equals(card.getSign())
                    || card.getColor().equals("black") || card.getColor().equals(getNewColor())) {
                hasCard = true;
                break;
            }
        }

        if (!hasCard) {
            System.out.println("Sorry, you dont have a card to play. You have to draw a card!");
            drawPenaltyCard();
            for (Card card : hand) {
                if (discardDeckCard.getColor().equals(card.getColor()) || discardDeckCard.getSign().equals(card.getSign())
                        || card.getColor().equals("black") || card.getColor().equals(getNewColor())) {
                    hasCard = true;
                    break;

                }
            }
        }

        return hasCard;
    }


    //eine Karte zu ziehen
    public void drawPenaltyCard() {
        Player currentPlayer = currentPlayer();
        currentPlayer.giveCard(cardDeck.drawCard());

    }
    public static Card setColorIfColorChangeCard() {
        String colorWish = input.nextLine();
        System.out.println("Choose a color: Red, Blue, Green, Yellow");

        if (colorWish.equalsIgnoreCase("Red")) {
            System.out.println("I wish red");
            return setNewColor(colorWish);
        } else if (colorWish.equalsIgnoreCase("Blue")) {
            System.out.println("I wish blue");
            return setNewColor(colorWish);
        } else if (colorWish.equalsIgnoreCase("Yellow")) {
            System.out.println("I wish yellow");
            return setNewColor(colorWish);
        } else if (colorWish.equalsIgnoreCase("Green")) {
            System.out.println("I wish green");
            return setNewColor(colorWish);
        } else {
            output.println("This color is not valid!");
            setColorIfColorChangeCard();
        }
        return null;
    }
    @Override
    public String toString() {
        return "Game: " + "\n" + " First Card: " + discardPile.getDropCard() + "\n" +
                "Players=" + playersInGame;
    }
}