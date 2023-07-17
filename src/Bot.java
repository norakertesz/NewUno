import java.io.PrintStream;
import java.util.List;
import java.util.Random;

public class Bot extends Player {
    public Bot(String name, int playersNumber) {
        super(name, playersNumber);
    }

    public Card botCardChoice() {
        Player currentPlayer = Game.currentPlayer();
        Card discardDeckCard = Game.getDiscardPile().getTopCard(Game.getDiscardPile());
        Card cardToPlay = null;
        int choice = 0;
        System.out.println("\nPlayer " + currentPlayer.getName() + " your turn");
        System.out.println("Your cards: " + "\n" + currentPlayer.showMyCards());
        do {
            for (Card card : currentPlayer.getCardsInHand()) {
                if (discardDeckCard.getColor().equals("Black") && card.getColor() == Game.getNewColor()) {
                    cardToPlay = card;
                }
                if (card.getColor().equals("Black")) {
                    Game.setColorIfColorChangeCardForBots(cardToPlay, "Random");
                    cardToPlay = card;
                    break;
                } else if (discardDeckCard.getColor().equals(card.getColor())) {
                    cardToPlay = card;
                    break;
                } else if (discardDeckCard.getSign().equals(card.getSign())) {
                    cardToPlay = card;
                    break;
                }
            }

            if (Game.canPlayerDropACard()) {
                for (int i = 0; i < currentPlayer.getCardsInHand().size(); i++) {
                    if (cardToPlay == currentPlayer.getCardsInHand().get(i) && choice >= 0 && choice <= getCardsInHand().size()) {
                        choice = i;
                    }
                }
            }
            Card removedCard = getCardsInHand().remove(choice);
            Game.getDiscardPile().addToPile(removedCard);
            System.out.println("Card on table: " + removedCard);
            return removedCard;
        } while (true);
    }
}
