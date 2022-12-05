import java.util.Scanner;

import javax.print.attribute.standard.OutputDeviceAssigned;

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

    public static boolean checkHands(Card[] hand){
        for(int i =0;i<4;i++){
            if(hand[i].getNumber()!=null) return true;
        }
        return false;
    }
    
    public static int checkInput(String input,Card[] deck){
        String[] words = input.split("");
        String number = "";
        String suit = "";
        String[][] suits = {{"♥","H"},{"♠","S"},{"♣","C"},{"♦","D"}};
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
                        else return -1;
                    }
                }
            }
            for(int j =0;j<numbers.length;j++){
                if(words[i].equals(numbers[j])){
                    if(number.equals("")){
                        number=numbers[j];
                        isNumberFound = true;
                    }
                    else return -2;
                }
            }
            
            if(i>0){
                if(words[i-1].equals("1")&&words[i].equals("0")){
                    if(number==""){
                        number = "10";
                        isNumberFound=true;
                    } 
                    else return -2;
                }
            }
            
        }
        if(!isSuitFound) return -10;
        if(!isNumberFound) return -20;
        for(int i =0;i<deck.length;i++){
            if(deck[i] != null)  if(deck[i].getNumber()==number && deck[i].getSuit()==suit) return i;
        }
        return -5;
    }

    public static void showTable(Card[] hand,Card[] middle,int point){

    }
    //This is where the game's functions will be.
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
        Card[] playersHand = new Card[4];
        for(int i =0;i<playersHand.length;i++) playersHand[i] = null;
        Card[] playersPocket = new Card[52];
        for(int i =0;i<playersPocket.length;i++) playersPocket[i] = null;
        int topOfPlayersPocket = 0;
        int playersPoint =0;
        Computer comp = new Computer(); 

        int topOfDeck = 0;
        topOfDeck = dealCards(deck, topOfDeck, playersHand, comp); //both topOfDeck value has changed and cards are dealed.
        Card[] middle = new Card[deck.length]; //Incase noone ever does a match.
        int topOfMiddle=0;
        for(int i =0;i<middle.length;i++){
            middle[i] = null;
        }
        for(int i =0;i<4;i++){
            moveCard(deck,topOfDeck,middle,topOfMiddle);
            topOfMiddle++;
            topOfDeck++;
        } //Placing 4 cards to middle before game starts.
        
        //Game has started
        for(int turn =0;turn<48;turn++){
            if(!checkHands(comp.getHand())) topOfDeck = dealCards(deck, topOfDeck, playersHand, comp);
            if(turn%2==0){
                int count = 0;
                for(int i =0;i<playersHand.length;i++){
                    if(playersHand[i] == null) count++;
                }
                if(count == 4){
                    topOfDeck = dealCards(deck, topOfDeck, playersHand, comp);
                }
                int output=0;
                while(true){
                    for(int i =0;i<playersHand.length;i++) if( playersHand[i] !=null) System.out.print(playersHand[i].getSuit()+" "+playersHand[i].getNumber()+"   ");
                    System.out.println();
                    System.out.println("------------------Middle---------------------");
                    System.out.println(topOfMiddle);
                    if(topOfMiddle>0) System.out.println(middle[topOfMiddle-1].getSuit()+" "+middle[topOfMiddle-1].getNumber());
                    if(topOfMiddle>1) for(int i = topOfMiddle-2;i>-1;i--) System.out.print(middle[i].getSuit()+" "+middle[i].getNumber()+"   ");
                    System.out.println();
                    String input = sc.nextLine();
                    output = checkInput(input, playersHand);
                    if(output == -1){
                        System.out.println("You can't input more than one suit. Please try again.");
                        continue;
                    }
                    if(output== -2){
                        System.out.println("You can't input more than one number. Please try again.");
                        continue;
                    }
                    if(output == -10){
                        System.out.println("You need to input a suit. Please try again.");
                        continue;
                    }
                    if(output == -20){
                        System.out.println("You need to input a number. Please try again.");
                        continue;
                    }
                    if(output == -5){
                        System.out.println("You don't have such a card in your hand!!!. Please try again.");
                        continue;
                    }
                    break;
                }
                moveCard(playersHand, output, middle, topOfMiddle);
                if(topOfMiddle ==1){
                    if(middle[topOfMiddle].getNumber().equals(middle[topOfMiddle-1].getNumber())){
                        playersPoint +=10; // Because of pişti
                        int topOfPlayersPocketPlus = 0;
                        for(int i =topOfMiddle;i>-1;i--){
                            moveCard(middle, i, playersPocket, topOfPlayersPocket+topOfPlayersPocketPlus);
                            topOfPlayersPocketPlus++;
                        }
                        topOfPlayersPocket += topOfPlayersPocketPlus;
                        System.out.println("Piştii!!");
                        topOfMiddle = 0;
                    }
                    else if(middle[topOfMiddle].getNumber().equals("J")){
                        int topOfPlayersPocketPlus = 0;
                        for(int i =topOfMiddle;i>-1;i--){
                            moveCard(middle, i, playersPocket, topOfPlayersPocket+topOfPlayersPocketPlus);
                            topOfPlayersPocketPlus++;
                        }
                        topOfPlayersPocket += topOfPlayersPocketPlus;
                        System.out.println("Match!!");
                        topOfMiddle = 0;
                    }
                    else{
                        topOfMiddle++;
                    }
                        
                }
                else if(topOfMiddle>1){
                    if(middle[topOfMiddle].getNumber().equals(middle[topOfMiddle-1].getNumber()) || middle[topOfMiddle].getNumber().equals("J")){
                        int topOfPlayersPocketPlus = 0;
                        for(int i =topOfMiddle;i>-1;i--){
                            moveCard(middle, i, playersPocket, topOfPlayersPocket+topOfPlayersPocketPlus);
                            topOfPlayersPocketPlus++;
                        }
                        topOfPlayersPocket += topOfPlayersPocketPlus;
                        System.out.println("Match!!!");
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
                
            }
        }

    }
}   