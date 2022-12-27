public class Player {
    private Card[] hand;
    private Card[] pocket;
    private int points;
    private int topOfPocket;
    private String name;
    public Player(){
        hand = new Card[4];
        pocket = new Card[52];
        points = 0;
        topOfPocket = 0;
        name=" ";
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
    public void setName(String n){
        name = n;
    }
    public String getName(){
        return name;
    }
}
