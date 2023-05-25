import java.util.ArrayList;

public class Game {

    private ArrayList<Player> playersInGame = new ArrayList<>();
    private CardDeck cardDeck = new CardDeck();   //original carddeck
    private CardDeck discardPile = new CardDeck();  //ablegestapel
    private Table table = new Table();

    public CardDeck getDiscardPile() {
        return discardPile;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public void start() {
        System.out.println("**********************UNO**********************");
        //spieler im main erstellen
        shareCards();   //karten austeilen
        layStartCard();  //erste karte auf dem tisch

    }

    public void cardChoice() {
        do {
            for (Player p : playersInGame) {
                System.out.println("Player " + p.getName() + " your turn");
                System.out.println("Your cards: " + "\n" + p.showMyCards());
                System.out.println("Welche Karte möchten Sie ausspielen?");
                discardPile.addToCards(p.playerDropCard());
                System.out.println("Card on Table: " + discardPile.getDropCard());
            }
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
        Card discardDeckCard = getDiscardPile().getDropCard();
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
        return false;
    }

    public void cardRegel2or4(Card card) {
        Card discardDeckCard = getDiscardPile().getDropCard();
        for (Player player : playersInGame) {
            if (discardDeckCard.getSign().equals("+2") || discardDeckCard.getSign().equals("+4")) {
                if (discardDeckCard.getSign().equals("+2")) {
                    player.giveCard(card);
                    discardPile.addToCards(card);
                }
            }
        }
    }


    @Override
    public String toString() {
        return "Game: " + "\n" + " First Card: " + discardPile.getDropCard() + "\n" +
                "Players=" + playersInGame;
    }
}