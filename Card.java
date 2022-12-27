public class Card {
    private String suit ;
    private String number ;
    public Card(String input_suit, String input_number){
        number = input_number;
        suit = input_suit;
    }
    public void setSuit(String input){
        suit = input;
    }
    public void setNumber(String input){
        number = input;
    }
    public String getNumber(){
        return number;
    }
    public String getSuit(){
        return suit;
    }
}
