public class Card {

    //card klasse mit eigenschaften
    private final String sign;   // Kartenzeichen als String angegeben
    private String color;       //Kartenfarben als finale String angegeben/kann nicht geändert werden
    private int value;          //value zum punkte sammeln

    public Card(String sign, String color, int value) {
        this.sign = sign;
        this.color = color;
        this.value = value;
    }

    public String getSign() {
        return sign;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return color + ' ' + sign;
    }
}
