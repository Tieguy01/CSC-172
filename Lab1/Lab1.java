package Lab1;

import java.util.Arrays;

public class Lab1 {

    public static boolean isAnagram(String word1, String word2) { 
        if (word1.length() != word2.length()) return false;
        
        char[] w1 = word1.toCharArray();
        char[] w2 = word2.toCharArray();

        Arrays.sort(w1);
        Arrays.sort(w2);

        for (int i = 0; i < w1.length; i++) {
            if(w1[i] != w2[i]) return false;
        }
        return true;

        // checks if every character in word 1 is contained within word2
        /*  for (int i = 0; i < word2.length(); i++) {
            if (word1.indexOf(word2.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
        */
    }

    // public static boolean isRotation(String word1, String word2) {
    //     if (word1.length() != word2.length()) return false;
    //     int secondIndex = word2.indexOf(word1.charAt(0)); // the index at which the first char in word1 occurs in word2
    //     for (int i = 0; i < word2.length(); i++) {
    //         if (word1.charAt(i) != word2.charAt(secondIndex)) {
    //             return false;
    //         }

    //         if(secondIndex == word2.length() - 1) {
    //             secondIndex = 0;
    //         } else {
    //             secondIndex++;
    //         }
    //     }
    //     return true;
    // }

    public static boolean isRotation(String word1, String word2) {
        if (!isAnagram(word1, word2)) return false;

        int w2StartIndex = 0;
        while (w2StartIndex < word2.length()) {
            int w2Index = word2.indexOf(word1.charAt(0), w2StartIndex);
            if (w2Index == -1) break;
            w2StartIndex = w2Index;

            for(int i = 0; i < word2.length(); i++) {
                if (word1.charAt(i) != word2.charAt(w2Index)) break;

                if (w2Index == word2.length() - 1) {
                    w2Index = 0;
                } else {
                    w2Index++;
                }

                if (w2Index == w2StartIndex) return true; 
            }

            w2StartIndex++;
            System.out.println(w2StartIndex);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isAnagram("QweRty", "QweRtY"));
        System.out.println(isAnagram("qwe_123_omorw3", "3123_owrmoq_we"));
        System.out.println(isAnagram("^^^^&&123", "^^^^&&123"));
        System.out.println(isAnagram("1111", "11111"));

        System.out.println();

        System.out.println(isRotation("123yrewq", "yreqw123"));
        System.out.println(isRotation("0  1  2", "1  20  "));
        System.out.println(isRotation("^^^^&&123", "^^^^&&123"));
        System.out.println(isRotation("1111", "11111"));

        System.out.println(isRotation("anagram", "angrama"));
    }

}