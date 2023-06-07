import java.util.Random;

public class Bot extends Player {
    public Bot(String name, int playersNumber) {
        super(name, playersNumber);
    }

    @Override
    public Card playerDropCard() {
        Random random = new Random();
        int choice = random.nextInt(getCardsInHand().size());
        Card selectedCard = CardDeck.drawCard();

        if (choice > 0 && choice <= getCardsInHand().size()) {
            if (Game.cardValidation(getCardsInHand().get(choice - 1))) {
                return getCardsInHand().remove(choice - 1);
            }
        }
        return null;
    }
}
