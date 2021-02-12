package Lab1;

import java.util.Arrays;

public class Lab1 {

    public static boolean isAnagram(String word1, String word2) { 
        if (word1.length() != word2.length()) return false;
        
        // break strings up into char arrays
        char[] w1 = word1.toCharArray();
        char[] w2 = word2.toCharArray();

        // sort char arrays
        Arrays.sort(w1);
        Arrays.sort(w2);

        // return false if any of the elements in the char arrays are different
        for (int i = 0; i < w1.length; i++) {
            if(w1[i] != w2[i]) return false;
        }

        return true;
    }

    public static boolean isRotation(String word1, String word2) {
        if (word1.length() != word2.length()) return false;

        // the purpose of the multipe indices and the while loop is to ensure the entirety of word2 is searched through to find the beginning of word1
        // in case the first character of word1 occurs multiple times in word2, as in the case of anagram and nagrama
        int w2StartIndex = 0; // the index of word2 at which to start searching for the first character of word1
        while (w2StartIndex < word2.length()) {
            int w2Index = word2.indexOf(word1.charAt(0), w2StartIndex); // index at which the first character of word1 is found in word2
            if (w2Index == -1) break;
            w2StartIndex = w2Index; // save the found index within the start index to check against if the for loop loops around the whole word

            for(int i = 0; i < word2.length(); i++) {
                if (word1.charAt(i) != word2.charAt(w2Index)) break;

                if (w2Index == word2.length() - 1) { // used to loop back to the beginning of word2 to check its starting letters before the start index
                    w2Index = 0;
                } else {
                    w2Index++;
                }

                if (w2Index == w2StartIndex) return true; // if the iterated index (w2Index) gets back to the start index, then the two words are rotations
            }

            w2StartIndex++; // will now be the index in word2 located directly after the index at which the first character of word1 was found in the last iteration
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(isAnagram("QweRty", "QweRtY"));
        System.out.println(isAnagram("qwe_123_omorw3", "3123_owrmoq_we"));
        System.out.println(isAnagram("^^^^&&123", "^^^^&&123"));
        System.out.println(isAnagram("1111", "11111"));
        System.out.println(isAnagram("21111", "22111"));

        System.out.println();

        System.out.println(isRotation("123yrewq", "yreqw123"));
        System.out.println(isRotation("0  1  2", "1  20  "));
        System.out.println(isRotation("^^^^&&123", "^^^^&&123"));
        System.out.println(isRotation("1111", "11111"));
        System.out.println(isRotation("anagram", "nagrama"));
    }

}