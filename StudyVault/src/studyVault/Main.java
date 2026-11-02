
package studyVault;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            NoteManager manager = new NoteManager();

            boolean running = true;

            while (running) {

                System.out.println("\n========== STUDYVAULT ==========");
                System.out.println("1. Create Note");
                System.out.println("2. List Notes");
                System.out.println("3. Open Note");
                System.out.println("4. Append to Note");
                System.out.println("5. Search Notes");
                System.out.println("6. Delete Note");
                System.out.println("7. Export Note");
                System.out.println("8. Restore Note");
                System.out.println("9. Exit");

                System.out.print("Choose an option: ");

                String choice = scanner.nextLine();

                try {
                    switch (choice) {

                        case "1":
                            System.out.print("Enter note title: ");
                            String title = scanner.nextLine();

                            System.out.println("Enter note content.");
                            System.out.println("Enter END on a new line to finish.");

                            StringBuilder content = new StringBuilder();

                            while (true) {
                                String line = scanner.nextLine();

                                if (line.equals("END")) {
                                    break;
                                }

                                content.append(line)
                                        .append(System.lineSeparator());
                            }

                            boolean overwrite = false;

                            if (manager.listNotes().contains(
                                    title.endsWith(".txt") ? title : title + ".txt")) {

                                System.out.print("Note exists. Overwrite? (yes/no): ");
                                overwrite = scanner.nextLine()
                                        .equalsIgnoreCase("yes");
                            }

                            manager.createNote(title, content.toString(), overwrite);

                            System.out.println("Note saved successfully!");
                            break;

                        case "2":
                            System.out.println("Saved notes:");
                            for (String note : manager.listNotes()) {
                                System.out.println(note);
                            }
                            break;

                        case "3":
                            System.out.print("Enter note title: ");
                            System.out.println(manager.openNote(scanner.nextLine()));
                            break;

                        case "4":
                            System.out.print("Enter note title: ");
                            String appendTitle = scanner.nextLine();

                            System.out.println("Enter additional content:");
                            String additionalContent = scanner.nextLine();

                            manager.appendNote(appendTitle, additionalContent);

                            System.out.println("Content appended successfully!");
                            break;

                        case "5":
                            System.out.print("Enter search keyword: ");
                            manager.searchNotes(scanner.nextLine());
                            break;

                        case "6":
                            System.out.print("Enter note title: ");
                            String deleteTitle = scanner.nextLine();

                            System.out.print("Confirm deletion? (yes/no): ");

                            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                                manager.deleteNote(deleteTitle);
                                System.out.println("Note deleted.");
                            } else {
                                System.out.println("Deletion cancelled.");
                            }
                            break;

                        case "7":
                            System.out.print("Enter note title: ");
                            manager.exportNote(scanner.nextLine());
                            System.out.println("Note exported successfully!");
                            break;

                        case "8":
                            System.out.print("Enter note title: ");
                            manager.restoreNote(scanner.nextLine());
                            System.out.println("Note restored successfully!");
                            break;

                        case "9":
                            running = false;
                            System.out.println("Thank you for using StudyVault!");
                            break;

                        default:
                            System.out.println("Invalid selection.");
                    }

                } catch (IOException | IllegalArgumentException e) {
                    System.out.println("Operation failed: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Could not initialise StudyVault: " + e.getMessage());
        }

        scanner.close();
    }
}
