import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Game gamePlayers = new Game();
        CardDeck carddeck= new CardDeck();
//
//        Player kata = new Player(1, "Kata",gamePlayers);
//        Player ksenija = new Player(2, "Ksenija",gamePlayers);
//        Player marlies = new Player(3, "Marlies",gamePlayers);
//        Player nora = new Player(4, "Nora",gamePlayers);

//        gamePlayers.addPlayerToPlayerList(kata);
//        gamePlayers.addPlayerToPlayerList(ksenija);
//        gamePlayers.addPlayerToPlayerList(marlies);
//        gamePlayers.addPlayerToPlayerList(nora);
        System.out.println(carddeck);
        System.out.println("********************************************");
        System.out.println("-------------------START------------------");
        gamePlayers.start();
        gamePlayers.cardChoice();





    }
}
