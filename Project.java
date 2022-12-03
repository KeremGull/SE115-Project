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
        //I realized that if i cut at the position x actually every card goes to "numbers of cards - x".
        for(int i =0;i<deck.length;i++){
            deckCutted[ (i+(deck.length-cut)) % deck.length] =deck[i];
        }
        return deckCutted;
    }
    

    //This is where the game's functions will be.
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("Welcome to the Pişti!!!");

        Card[] deck = createDeck();
        deckShuffling(deck);

        // just checking
        for(int i =0;i<deck.length;i++){
            System.out.println(i+"   "+deck[i].GetNumber()+" . "+deck[i].getSuit());
        }

        //Cutting 
        int cut = rand.nextInt(10,deck.length-10);
        System.out.println(cut+" .  -------------------------");
        deck = deckCut(cut,deck);

        //Another check
        for(int i =0;i<deck.length;i++){
            System.out.println(i+" . "+deck[i].GetNumber()+" . "+deck[i].getSuit());
        }
    }
}   