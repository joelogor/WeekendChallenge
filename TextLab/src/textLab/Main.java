
package textLab;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================");
        System.out.println("       WELCOME TO TEXTLAB");
        System.out.println("==================================");

        System.out.println("Enter your passage.");
        System.out.println("Press ENTER on an empty line to finish:");

        StringBuilder passage = new StringBuilder();

        while (true) {
            String line = scanner.nextLine();

            if (line.isEmpty()) {
                break;
            }

            passage.append(line).append("\n");
        }

        TextChecker processor =
                new TextChecker(passage.toString());

        boolean running = true;

        while (running) {

            System.out.println("\n========== TEXTLAB MENU ==========");
            System.out.println("1. Display Text Statistics");
            System.out.println("2. Clean Whitespace");
            System.out.println("3. Search for a Word");
            System.out.println("4. Replace a Word");
            System.out.println("5. Check Palindrome");
            System.out.println("6. Display Longest Words");
            System.out.println("7. Display Original Text");
            System.out.println("8. Display Edited Text");
            System.out.println("9. Exit");
            System.out.println("==================================");

            System.out.print("Choose an option: ");

            String input = scanner.nextLine();

            int choice;

            try {
                choice = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number from 1-9.");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.println("\n========== TEXT STATISTICS ==========");
                    System.out.println("Characters: "
                            + processor.countCharacters());
                    System.out.println("Characters without whitespace: "
                            + processor.countCharactersWithoutWhitespace());
                    System.out.println("Words: "
                            + processor.countWords());
                    System.out.println("Sentences: "
                            + processor.countSentences());
                    break;

                case 2:
                    System.out.println("Cleaned text:");
                    System.out.println(processor.cleanText());
                    break;

                case 3:
                    System.out.print("Enter word to search: ");
                    String searchWord = scanner.nextLine();

                    int occurrences = processor.searchWord(searchWord);

                    System.out.println(
                            searchWord + " appears "
                                    + occurrences + " time(s)."
                    );
                    break;

                case 4:
                    System.out.print("Enter word to replace: ");
                    String oldWord = scanner.nextLine();

                    System.out.print("Enter replacement word: ");
                    String newWord = scanner.nextLine();

                    System.out.println("Updated text:");
                    System.out.println(
                            processor.replaceWord(oldWord, newWord)
                    );
                    break;

                case 5:
                    if (processor.isPalindrome()) {
                        System.out.println("The passage is a palindrome.");
                    } else {
                        System.out.println("The passage is not a palindrome.");
                    }
                    break;

                case 6:
                    System.out.println("Longest word(s): "
                            + processor.getLongestWords());
                    break;

                case 7:
                    System.out.println("\nOriginal passage:");
                    System.out.println(processor.getOriginalText());
                    break;

                case 8:
                    System.out.println("\nEdited passage:");
                    System.out.println(processor.getEditedText());
                    break;

                case 9:
                    running = false;
                    System.out.println("Thank you for using TextLab!");
                    break;

                default:
                    System.out.println("Invalid selection. Choose 1-9.");
            }
        }

        scanner.close();
    }
}
