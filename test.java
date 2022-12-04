public class test {
    public static void main(String[] args) {
        String input ="7";
        String[] words = input.split("");
        String[] s = {"7"};
        for(int i =0;i<words.length;i++){
            System.out.println(words[i]==s[0]);
        }
    }
}
