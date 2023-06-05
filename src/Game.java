import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private ArrayList<Player> playersInGame = new ArrayList<>();
    private CardDeck cardDeck = new CardDeck();   //original carddeck
    private CardDeck discardPile = new CardDeck();  //ablegestapel
    private Table table = new Table();
    private boolean clockweis = true;      //spielrichtung
    private static int currentPlayerNumber;
    Scanner input = new Scanner(System.in);
    private String newColor;

    public String getNewColor() {
        return newColor;
    }

    public void setNewColor(String newColor) {
        this.newColor = newColor;
    }

    public Game() {
        this.playersInGame = new ArrayList<>();
    }

    public CardDeck getDiscardPile(
    ) {
        return discardPile;
    }

    public Player chooseInitialPlayer() {
        Random initialPlayer = new Random();
        int randomIndex = initialPlayer.nextInt(3);

        setCurrentPlayerNumber(randomIndex);

        Player first = playersInGame.get(randomIndex);
        return first;
    }

    public void setClockweis(boolean clockweis) {
        this.clockweis = clockweis;
    }

    public Player currentPlayer() {
        Player currentPlayer = playersInGame.get(getCurrentPlayerNumber());
        return currentPlayer;
    }


    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public static int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public void setCurrentPlayerNumber(int currentPlayerNumber) {
        this.currentPlayerNumber = currentPlayerNumber;
    }

    public void start() {
        System.out.println("**********************UNO**********************");
        //spieler im main erstellen
        shareCards();   //karten austeilen
        layStartCard();  //erste karte auf dem tisch
        chooseInitialPlayer();
    }

    public void cardChoice() {
        do {
            Player currentPlayer = currentPlayer();
            System.out.println("Player " + currentPlayer.getName() + " your turn");
            penalty();
            if (canPlayerDropACard()) {
                System.out.println("Your cards: " + "\n" + currentPlayer.showMyCards());
                System.out.println("Welche Karte möchten Sie ausspielen?");
                discardPile.addToCards(currentPlayer.playerDropCard());
                System.out.println("Card on Table: " + discardPile.getDropCard());
                colorChange(); //Colorchange wieder auf null setzen!!!!
            } else {

                System.out.println("You still don't have a card to play.");
            }
            checkNextTurn();
        } while (table != null);
    }


    public void addPlayerToPlayerList(Player p) {
        playersInGame.add(p);
    }


    //karten austeilen - 7karte
    public void shareCards() {
        for (Player p : playersInGame) {
            for (int i = 0; i < 7; i++) {
                p.giveCard(cardDeck.drawCard());  //eine karte von deck zu spieler
            }
        }
    }

    //erste karte auf dem tisch
    public void layStartCard() {
        Card card = new Card(null, null);
        card = cardDeck.drawCard();
        discardPile.addToCards(card);

    }

    public boolean cardValidation(Card card) {
        //Falls die Karte auf dem Tisch die gleiche Farbe oder Zeichen haben wird die Karte gelegt
        Card discardDeckCard = getDiscardPile().getDropCard();
        Player currentPlayer = currentPlayer();

        System.out.println("CardValidation: I got card " + card);
        System.out.println("Discardpile dropcard: " + discardDeckCard);
        System.out.println("Color: '" + card.getColor() + "'");
        if (card.getColor().equals("black")) {
            return true;
        } else if (discardDeckCard.getColor().equals(card.getColor()) || card.getColor().equals("black") || discardDeckCard.getColor().equals("black")) {
            return true;
        } else if (discardDeckCard.getSign().equals(card.getSign()) || card.getSign().equals("+4") || discardDeckCard.getSign().equals("+4")) {
            return true;
        } else if (discardDeckCard.getSign().equals(card.getSign()) || card.getSign().equals("color change") || discardDeckCard.getSign().equals("color change")) {

            return true;

        } else {
            System.out.println("Fehler: Wählen Sie die richtige Karte!");
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


    @Override
    public String toString() {
        return "Game: " + "\n" + " First Card: " + discardPile.getDropCard() + "\n" +
                "Players=" + playersInGame;
    }

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
    public int isCardNormal() {
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


    public int isCardIsReverse() { //This method is to decide who has the next turn when the card "<->" is played
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
        } else if (currentPlayerIndex == 1)  {
            if (clockweis) {
                currentPlayerIndex = 3;
            } else {
                currentPlayerIndex = 3;
            }
        }else {
            if (clockweis) {
                currentPlayerIndex = 0;
            } else {
                currentPlayerIndex = 0;
            }
        }

        setCurrentPlayerNumber(currentPlayerIndex);
        return currentPlayerIndex;
    }

    public void drawPenaltyCard() {
        Player currentPlayer = currentPlayer();
        currentPlayer.giveCard(cardDeck.drawCard());

    }

    public void colorChange() {
        Card discardDeckCard = getDiscardPile().getDropCard();
        if (discardDeckCard.getSign().equals("color change")) {
            System.out.println("Give me a new color: ");
            String newColor = input.nextLine();
            setNewColor(newColor);

        }

    }

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
            System.out.println(currentPlayer.showMyCards());
        }

        return hasCard;
    }
}