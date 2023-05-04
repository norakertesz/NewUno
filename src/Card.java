public class Card {

    private String color; // Kartenfarben als String angegeben
    private final String sign;//Kartenzeichen als finale String angegeben/kann nicht geändert werden

    public Card( String sign, String color) {
        this.color = color;
        this.sign = sign;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSign() { //Da meine Kartenzeichen final sind, brauchen wir keine Setter-Methode
        return sign;
    }

    @Override
    public String toString() {
        return
                 color + sign  ;
    }


}
