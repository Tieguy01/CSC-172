package Lab1;

public class Lab1 {

    
    public static boolean isAnagram(String word1, String word2){
        if (word1.length() != word2.length()) return false;
        for (int i = 0; i < word2.length(); i++) {
            if (word1.indexOf(word2.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isRotation(String word1, String word2) {
        if (word1.length() != word2.length()) return false;
        int secondIndex = word2.indexOf(word1.charAt(0));
        for (int i = 0; i < word2.length(); i++) {
            if (word1.charAt(i) != word2.charAt(secondIndex)) {
                return false;
            }

            if(secondIndex == word2.length() - 1) {
                secondIndex = 0;
            } else {
                secondIndex++;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isAnagram("cinema", "iceman"));
        System.out.println(isRotation("1111", "11111"));
    }

}