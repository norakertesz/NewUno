import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Player kata = new Player(1, "Kata");
        Player ksenija = new Player(2, "Ksenija");
        Player marlies = new Player(3, "Marlies");
        Player nora = new Player(4, "Nora");

        Game gamePlayers = new Game();
        gamePlayers.addPlayerToPlayerList(kata);
        gamePlayers.addPlayerToPlayerList(ksenija);
        gamePlayers.addPlayerToPlayerList(marlies);
        gamePlayers.addPlayerToPlayerList(nora);
        gamePlayers.start();


    }
}
