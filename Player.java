public class Player {
    private Card[] hand;
    private Card[] pocket;
    private int points;
    private int topOfPocket;
    public Player(){
        hand = new Card[52];
        pocket = new Card[52];
        points = 0;
        topOfPocket = 0;
   }
    public Card[] getHand(){
        return hand;
    }
    public Card[] getPocket(){
        return pocket;
    }
    public int getPoint(){
        return points;
    }
    public int getTopOfPocket(){
        return topOfPocket;
    }
    public void incrementPoint(int add){
        points += add;
    }
    public void incrementTopOfPocket(){
        topOfPocket++;
    }
}
