
/*I'm creating this class because i feel like if i want to add some features
 * outside of the project's requirements, this way would be better.
 * Also it is better for me to practice objects.*/


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
    public void SetNumber(String input){
        number = input;
    }
    public String GetNumber(){
        return number;
    }
    public String getSuit(){
        return suit;
    }
}
