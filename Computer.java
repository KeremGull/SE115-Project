import java.util.Random;
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
    public int play(Card[] middle,int topOfMiddle){
        Random rand = new Random();
        if(topOfMiddle!=-1){
            for(int i =0;i<hand.length;i++){
                if(hand[i] != null){
                    if(hand[i].getNumber().equals(middle[topOfMiddle].getNumber())){
                        return i;
                    }
                }
            }
            for(int i =0;i<hand.length;i++){
                if(hand[i] != null){
                    if(hand[i].getNumber().equals("J")){
                        return i;
                    }
                }
            }
        }
        while(true){
            int index = rand.nextInt(0,4);
            if(hand[index]!= null){
                return index;
            }
        } 
    }
}
