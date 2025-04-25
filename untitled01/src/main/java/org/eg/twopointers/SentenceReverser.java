package org.eg.twopointers;

public class SentenceReverser {
    public static void main(String[] args) {
        System.out.println(reverseWords("  Hello World!  "));
    }

    public static String reverseWords(String s) {
        StringBuilder result = new StringBuilder();
        int n = s.length();
        int i = n - 1;

        while (i >= 0) {
            // Skip spaces
            while (i >= 0 && s.charAt(i) == ' ') {
                i--;
            }
            // Find the end of the word
            int end = i;
            // Find the start of the word
            while (i >= 0 && s.charAt(i) != ' ') {
                i--;
            }
            // Append the word to the result
            if (end >= 0) {
                result.append(s, i + 1, end + 1).append(' ');
            }
        }

        // Remove the trailing space
        if (!result.isEmpty()) {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }
}
