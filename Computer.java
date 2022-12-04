public class Computer {
    private Card[] hand;
    public Computer(){
        hand = new Card[4];
        for(int i =0;i<hand.length;i++){
            hand[i] = new Card("","");
        }
    }
    public void addCard(Card input){
        for(int i =0;i<hand.length;i++){
            if(hand[i].getSuit()==""&&hand[i].getNumber()==""){
                hand[i].setSuit(input.getSuit());
                hand[i].setNumber(input.getNumber());
                break;
            }
        }
    }
    public void setHand(Card[] input){
        hand = input;
    }
    public Card[] getHand(){
        return hand;
    }
}
