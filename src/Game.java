import java.util.ArrayList;

public class Game {

    //Method zum Karte zu zaehlen
    private CardDeck cardDeck = new CardDeck();
    private CardDeck disCardPile = new CardDeck();


    public ArrayList<Player> playersInGame = new ArrayList<>();


    public void addPlayerToPlayerList(Player p) {
        playersInGame.add(p);
    }


    public void shareCards() {
        for (Player p : playersInGame) {
            for (int i = 0; i < 7; i++) {
                p.giveCard(cardDeck.drawCard());
            }
        }
    }

    public Card layStartCard() {
        Card card = new Card(null, null);
        card = cardDeck.drawCard();
        return card;
    }

    public void start() {
        shareCards();
        layStartCard();
    }


    public void cardChoice() {

        for (Player p : playersInGame) {
            System.out.println("Welche Karte moechten Sie ausspielen?");
            disCardPile.addToDiscardPile(p.playerDropCard());

        }
    }


    @Override
    public String toString() {
        return "Game:" + "First card: " + layStartCard() + "\n" + "Players with cards: " + playersInGame;
    }
}
