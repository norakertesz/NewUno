import java.util.ArrayList;

public class Game {

    private ArrayList<Player> playersInGame = new ArrayList<>();
    private CardDeck cardDeck = new CardDeck();   //original carddeck
    private CardDeck discardPile = new CardDeck();  //ablegestapel
    private Table table = new Table();


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
                System.out.println("Your cards: " +"\n"+ p.showMyCards());
                System.out.println("Welche Karte möchten Sie ausspielen?");
                discardPile.addToDiscardPile(p.playerDropCard());
                System.out.println("Card on Table: "+discardPile.getDropCard());
            }
        } while(table !=  null);
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
    public Card layStartCard() {
        Card card = new Card(null, null);
        card = cardDeck.drawCard();
        return card;
    }
                               // +2 ODER +4
//public Card spielRegel(){
//        for(Player player:playersInGame){
//     if (discardPile.getDropCard().getSign().equals("+2") || discardPile.getDropCard().getSign().equals("+4")) {
//        System.out.println("Du musst " + discardPile.getDropCard().getSign() + " Karten nehmen!");
//        if (discardPile.getDropCard().getSign().equals("+2")) {
//            player.giveCard(cardDeck.());
//            System.out.println("Dir wurde eine neue Karte gegeben: " + karten.get(0));
//            cardDeck.remove(0);
//            player.giveCard(cardDeck.get());
//            System.out.println("Dir wurde eine neue Karte gegeben: " + karten.get(0));
//            karten.remove(0);
//        } else {
//            for (int i = 0; i < 4; i++) {
//                spielKartenLegen.giveCard(karten.get(0));
//                System.out.println("Dir wurde eine neue Karte gegeben: " + karten.get(0));
//                karten.remove(0);
//            }
//        }
//    }}}


    @Override
    public String toString() {
        return "Game: " + "\n" + " First Card: " + layStartCard() + "\n" +
                "Players=" + playersInGame;
    }
}