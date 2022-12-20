import java.util.Scanner;
import java.util.Random;

public class Project{
    public static Card[] createDeck(){
        Card[] deck = new Card[52];
        String[] suits= {"♠","♣","♥","♦"};
        String[] numbers = {"A","2","3","4","5","6","7","8","9","10","J","K","Q"};
        int index =0;

        //Initializing the deck
        for(int i=0;i<suits.length;i++){
            for(int j =0;j<numbers.length;j++){
                deck[index] = new Card(suits[i],numbers[j]);
                index++;
            }
        }
        return deck;
    }
    //Shuffling the deck.(I've seen this algorithm in a book called Introduction To Programming In Java by Kevin Wayne and Robert Sedgewick.)
    public static void deckShuffling(Card[] deck){
        Random rand = new Random();
        for(int i =0;i<deck.length;i++){
            Card old = deck[i];
            int someValue =rand.nextInt(i,deck.length);
            deck[i] = deck[someValue];
            deck[someValue] = old;
        }
    }

    public static Card[] deckCut(int cut,Card[] deck){
        Card[] deckCutted= new Card[deck.length];
        //I realized that if i cut at the position x actually every card goes to its "numbers of cards - x" right.
        for(int i =0;i<deck.length;i++){
            deckCutted[ (i+(deck.length-cut)) % deck.length] =deck[i];
        }
        return deckCutted;   
        
    }
    
    public static int dealCards(Card[] deck,int index,Card[] playersHand,Computer comp){
        for(int i =0;i<8;i++){
            if(i%2==0){
                for(int j =0;j<playersHand.length;j++){
                    if(playersHand[j] == null){
                        moveCard(deck,i+index,playersHand,j); 
                        break;
                    }
                }
            }else{
                comp.addCard(deck[i+index]);
                deck[i+index] = null;
            }
        }
        return index +8;
    }

    public static void moveCard(Card[] fromDeck,int fromIndex,Card[] toDeck,int toIndex){
        toDeck[toIndex] = fromDeck[fromIndex];
        fromDeck[fromIndex]= null;
    }

    public static boolean checkHand(Card[] hand){
        for(int i =0;i<4;i++){
            if(hand[i] !=null) return true;
        }
        return false;
    }
    
    public static int checkInput(String input,Card[] deck){
        String[] words = input.split("");
        String number = "";
        String suit = "";
        String[][] suits = {{"♥","H","h"},{"♠","S","s"},{"♣","C","c"},{"♦","D","d"}};
        String[] numbers = {"A","2","3","4","5","6","7","8","9","J","Q","K"};
        boolean isSuitFound= false;
        boolean isNumberFound = false;
        for(int i=0;i<words.length;i++){
            for(int j =0;j<suits.length;j++){   
                for(int k =0;k<suits[j].length;k++){
                    if(words[i].equals(suits[j][k])){
                        if(suit.equals("")) {
                            suit= suits[j][0];
                            isSuitFound = true;
                        }   
                        else {
                            System.out.println("You can't input more than one suit. Please try again.");
                            return -1;
                        }
                    }
                }
            }
            for(int j =0;j<numbers.length;j++){
                if(words[i].equals(numbers[j])){
                    if(number.equals("")){
                        number=numbers[j];
                        isNumberFound = true;
                    }
                    else {
                        System.out.println("You can't input more than one number. Please try again.");
                        return -1;
                    }
                }
            }
            
            if(i>0){
                if(words[i-1].equals("1")&&words[i].equals("0")){
                    if(number==""){
                        number = "10";
                        isNumberFound=true;
                    } 
                    else {
                        System.out.println("You can't input more than one number. Please try again.");
                        return -1;
                    }
                }
            }
            
        }
        if(!isSuitFound) {
            System.out.println("You need to input a suit. Please try again.");
            return -1;
        }
        if(!isNumberFound) {
            System.out.println("You need to input a number. Please try again.");
            return -1;
        }
        for(int i =0;i<deck.length;i++){
            if(deck[i] != null)  if(deck[i].getNumber()==number && deck[i].getSuit()==suit) return i;
        }
        System.out.println("You don't have such a card in your hand!!!. Please try again.");
        return -1;
    }

    public static void showTable(Card[] playersHand,Card[] middle,int topOfMiddle){


        System.out.println("--------------------Your Hand----------------------");
        System.out.println();
        for(int i =0;i<playersHand.length;i++) if( playersHand[i] !=null) System.out.print(playersHand[i].getSuit()+" "+playersHand[i].getNumber()+"   ");
        
        System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();

        System.out.println("------------------Middle---------------------");
        if(topOfMiddle>0) System.out.println(middle[topOfMiddle-1].getSuit()+" "+middle[topOfMiddle-1].getNumber());
        if(topOfMiddle>1) for(int i = topOfMiddle-2;i>-1;i--) System.out.print(middle[i].getSuit()+" "+middle[i].getNumber()+"   ");
        System.out.println();


    }
      
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("Welcome to the Pişti!!!");

        Card[] deck = createDeck();
        deckShuffling(deck);

        //Cutting 
        int cut = rand.nextInt(10,deck.length-10);
        deck = deckCut(cut,deck);
        //players and computers variables
        
        Player player = new Player();
        Computer comp = new Computer(); 
        Card [] playersHand = player.getHand();
        Card[] playersPocket = player.getPocket();  
        int topOfDeck = 0;
        topOfDeck = dealCards(deck, topOfDeck, playersHand, comp); //topOfDeck value has changed and cards are dealed.
        Card[] middle = new Card[deck.length]; //Incase noone ever does a match.
        int topOfMiddle=0;
        
        for(int i =0;i<4;i++){
            moveCard(deck,topOfDeck,middle,topOfMiddle);
            topOfMiddle++;
            topOfDeck++;
        } //Placing 4 cards to middle before game starts.
        
        //Game has started
        String lastWinner = "noone";
        for(int turn =0;turn<48;turn++){
            if(turn%2==0){
                if(!checkHand(comp.getHand())) topOfDeck = dealCards(deck, topOfDeck, playersHand, comp); //If computer's hand is empty then we should deal cards to both sides.
                int output=0;
                while(true){
                    showTable(playersHand, middle, topOfMiddle);
                    String input = sc.nextLine();
                    System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
                    System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
                    System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
                    System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
                    System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
                    System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
                    output = checkInput(input, playersHand);
                    if(output>-1) break;
                    
                }   
                moveCard(playersHand, output, middle, topOfMiddle);
                if(topOfMiddle ==1){
                    if(middle[topOfMiddle].getNumber().equals(middle[topOfMiddle-1].getNumber())){
                        player.incrementPoint(10); // Because of pişti
                        lastWinner = "player"; 
                        for(int i =topOfMiddle;i>-1;i--){
                            moveCard(middle, i, playersPocket, player.getTopOfPocket());
                            player.incrementTopOfPocket();
                        }
                        System.out.println("Piştii!! 10 pts to player.");
                        topOfMiddle = 0;
                    }
                    else if(middle[topOfMiddle].getNumber().equals("J")){
                        for(int i =topOfMiddle;i>-1;i--){
                            moveCard(middle, i, playersPocket, player.getTopOfPocket());
                            player.incrementTopOfPocket();
                        }
                        System.out.println("You've made a match!!");
                        lastWinner = "player";
                        topOfMiddle = 0;
                    }
                    else{
                        topOfMiddle++;
                    }
                        
                }
                else if(topOfMiddle>1){
                    if(middle[topOfMiddle].getNumber().equals(middle[topOfMiddle-1].getNumber()) || middle[topOfMiddle].getNumber().equals("J")){
                        for(int i =topOfMiddle;i>-1;i--){
                            moveCard(middle, i, playersPocket, player.getTopOfPocket());
                            player.incrementTopOfPocket();
                        }
                        System.out.println("You've made a match!!");
                        lastWinner = "player";
                        topOfMiddle = 0;
                    }else{
                        topOfMiddle++;
                    }
                }
                else{
                    topOfMiddle++;
                }

            }
            else{
                moveCard(comp.getHand(), comp.play(middle, topOfMiddle-1), middle, topOfMiddle);
                if(topOfMiddle ==1){
                    if(middle[topOfMiddle].getNumber().equals(middle[topOfMiddle-1].getNumber())){
                        System.out.println("Piştii!! 10 pts to Computer.");
                        lastWinner = "computer";
                        topOfMiddle = 0;
                    }
                    else if(middle[topOfMiddle].getNumber().equals("J")){
                        System.out.println("Computer has made a match!!");
                        lastWinner = "computer";
                        topOfMiddle = 0;
                    }
                    else{
                        topOfMiddle++;
                    }
                        
                }
                else if(topOfMiddle>1){
                    if(middle[topOfMiddle].getNumber().equals(middle[topOfMiddle-1].getNumber()) || middle[topOfMiddle].getNumber().equals("J")){
                        System.out.println("Computer has made a match!!");
                        topOfMiddle = 0;
                    }else{
                        topOfMiddle++;
                    }
                }
                else{
                    topOfMiddle++;
                }
                
            }
        }
        if(topOfMiddle!=0){
            if(lastWinner =="noone"){
                System.out.println("No match or pişti has been made so no winner in this game.");
                System.out.println("You got 0 points.");
            }else{
                if(lastWinner=="player"){
                    System.out.println("Last winner was player so cards on the middle will go your pocket. ");
                    for(int i = topOfMiddle-1;i>-1;i--){
                        moveCard(middle, i, playersPocket, player.getTopOfPocket());
                        player.incrementTopOfPocket();
                    }   
                }
                else{
                System.out.println("Last winner was computer so cards on the middle will go computer's pocket.");
                }
            }
        }
        if(lastWinner!="noone"){
            for(int i =0;i<player.getTopOfPocket();i++){
                if(playersPocket[i].getNumber().equals("10")&& playersPocket[i].getSuit().equals("♦")){
                    System.out.println("There are 10 ♦ in your pocket so extra 3 pts!");
                    player.incrementPoint(3);
                }
                else if(playersPocket[i].getNumber().equals("2")&& playersPocket[i].getSuit().equals("♣")){
                    System.out.println("There are 2 ♣ in your pocket so extra 2 pts!");
                    player.incrementPoint(2);
                }
                else{
                    player.incrementPoint(1);
                }
            }
            if(player.getTopOfPocket()== 26){
                System.out.println("Player's and computer's card numbers are same so no additional pts.");
            }else if(player.getTopOfPocket()>26){
                System.out.println("You got "+player.getTopOfPocket()+" cards so u got additional 3 pts!");
                player.incrementPoint(3);
            }else{
                System.out.println("You got "+player.getTopOfPocket()+" cards.");
            }
            System.out.println("Your total points: "+player.getPoint());
        }
    }
}   