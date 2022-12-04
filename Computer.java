public class Computer {
    private Card[] hand;
    public Computer(){
        hand = new Card[4];
        for(int i =0;i<hand.length;i++){
            hand[i] = null;
        }
    }
    public void addCard(Card input){
        for(int i =0;i<hand.length;i++){
            if(hand[i]==null){
                hand[i]= input;
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
