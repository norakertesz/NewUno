import java.util.ArrayList;
import java.util.List;

public class Game {

    //Method zum Karte zu zaehlen
    private CardDeck cardDeck= new CardDeck();


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

    public void start() {
        shareCards();

    }

}
