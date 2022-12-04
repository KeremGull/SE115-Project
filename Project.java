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
                    if(playersHand[j].getSuit()==""&&playersHand[j].getNumber()==""){
                        setCard(deck[i],playersHand[j]); 
                        break;
                    }
                }
            }else{
                comp.addCard(deck[i]);
            }
        }
        return index +8;
    }

    public static void setCard(Card from,Card to){
        to.setSuit(from.getSuit());
        to.setNumber(from.getNumber());
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
        for(int i =0;i<playersHand.length;i++) playersHand[i] = new Card("","");
        int playersPoint =0;
        Computer comp = new Computer(); 

        int topOfDeck = 0;
        topOfDeck = dealCards(deck, topOfDeck, playersHand, comp); //both topOfDeck value has changed and cards are dealed.
        Card[] middle = new Card[deck.length]; //Incase noone ever does a match.
        int topOfMiddle=0;
        for(int i =0;i<middle.length;i++){
            middle[i] = new Card("","");
        }
        for(int i =0;i<4;i++){
            setCard(deck[topOfDeck],middle[topOfMiddle]);
            topOfMiddle++;
            topOfDeck++;
        } //Placing 4 cards to middle before game starts.

        //Test
        System.out.println("-------------------------Player's Hand----------------------");
        for(int i =0;i<playersHand.length;i++){
            System.out.print(playersHand[i].getSuit()+" "+playersHand[i].getNumber()+"     ");
        }
        System.out.println();
        System.out.println("-------------------------Computer's Hand----------------------");
        for(int i =0;i<comp.getHand().length;i++){
            System.out.print(comp.getHand()[i].getSuit()+" "+comp.getHand()[i].getNumber()+"    ");
        }
        System.out.println();
        System.out.println("-------------------------Middle----------------------");
        System.out.println(middle[topOfMiddle-1].getSuit()+" "+middle[topOfMiddle-1].getNumber()+"    ");
        for(int i =0;i<topOfMiddle-1;i++){
            System.out.print(middle[i].getSuit()+" "+middle[i].getNumber()+"    ");
        }
    }
}   