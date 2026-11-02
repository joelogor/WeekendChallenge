
package textLab;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextChecker {

    private final String originalText;
    private String editedText;

    public TextChecker(String text) {
        this.originalText = text == null ? "" : text;
        this.editedText = this.originalText;
    }

    public int countCharacters() {
        return editedText.length();
    }
    public int countCharactersWithoutWhitespace() {
        return editedText.replaceAll("\\s", "").length();
    }


    public int countWords() {
        Matcher matcher = Pattern.compile("[\\p{L}\\p{N}]+").matcher(editedText);

        int count = 0;

        while (matcher.find()) {
            count++;
        }

        return count;
    }


    public int countSentences() {
        String text = editedText.trim();

        if (text.isEmpty()) {
            return 0;
        }

        int count = 0;
        boolean hasContent = false;

        for (int i = 0; i < text.length(); i++) {

            char current = text.charAt(i);

            if (current == '.' || current == '!' || current == '?') {

                if (hasContent) {
                    count++;
                    hasContent = false;
                }

                while (i + 1 < text.length()) {
                    char next = text.charAt(i + 1);

                    if (next == '.' || next == '!' || next == '?') {
                        i++;
                    } else {
                        break;
                    }
                }

            } else if (!Character.isWhitespace(current)) {
                hasContent = true;
            }
        }

        if (hasContent) {
            count++;
        }

        return count;
    }

    public String cleanText() {

        editedText = editedText.trim().replaceAll("\\s+", " ");

        return editedText;
    }


    public int searchWord(String word) {

        if (word == null || !word.matches("[\\p{L}\\p{N}]+")) {
            return 0;
        }

        String regex = "(?<![\\p{L}\\p{N}])" + Pattern.quote(word) + "(?![\\p{L}\\p{N}])";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
        );

        Matcher matcher = pattern.matcher(editedText);

        int count = 0;

        while (matcher.find()) {
            count++;
        }

        return count;
    }

    public String replaceWord(String oldWord, String newWord) {

        if (oldWord == null || !oldWord.matches("[\\p{L}\\p{N}]+")) {
            return editedText;
        }

        if (newWord == null) {
            return editedText;
        }

        String regex = "(?<![\\p{L}\\p{N}])" + Pattern.quote(oldWord) + "(?![\\p{L}\\p{N}])";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

        Matcher matcher = pattern.matcher(editedText);

        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(result, Matcher.quoteReplacement(newWord));
        }

        matcher.appendTail(result);

        editedText = result.toString();

        return editedText;
    }

    public boolean isPalindrome() {

        String cleaned = editedText.replaceAll("[^\\p{L}\\p{N}]", "")
                .toLowerCase(Locale.ROOT);

        if (cleaned.isEmpty()) {
            return false;
        }

        String reversed = new StringBuilder(cleaned).reverse().toString();

        return cleaned.equals(reversed);
    }

    public List<String> getLongestWords() {

        Matcher matcher = Pattern.compile("[\\p{L}\\p{N}]+").matcher(editedText);

        List<String> longestWords = new ArrayList<>();

        int maxLength = 0;

        while (matcher.find()) {

            String word = matcher.group();

            if (word.length() > maxLength) {
                maxLength = word.length();
                longestWords.clear();
                longestWords.add(word);

            } else if (word.length() == maxLength
                    && !longestWords.contains(word)) {

                longestWords.add(word);
            }
        }

        return longestWords;
    }

  
    public String getOriginalText() {
        return originalText;
    }

    // Get edited passage
    public String getEditedText() {
        return editedText;
    }
}
