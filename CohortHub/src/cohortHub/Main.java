package cohortHub;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CohortManager manager = new CohortManager();

        while (true) {
            System.out.println("\n===== CohortHub =====");
            System.out.println("1. Register learner");
            System.out.println("2. Find learner");
            System.out.println("3. Display learners");
            System.out.println("4. Create study group");
            System.out.println("5. Add learner to group");
            System.out.println("6. View learner's groups");
            System.out.println("7. Request mentor support");
            System.out.println("8. Process mentor request");
            System.out.println("9. Remove learner");
            System.out.println("10. View statistics");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Learner ID: ");
                        String id = scanner.nextLine();

                        System.out.print("Name: ");
                        String name = scanner.nextLine();

                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        manager.registerLearner(
                                new Learner(id, name, email));

                        System.out.println("Learner registered.");
                    }

                    case "2" -> {
                        System.out.print("Learner ID: ");
                        Learner learner =
                                manager.findLearner(scanner.nextLine());

                        System.out.println(
                                learner == null ? "Learner not found."
                                        : learner);
                    }

                    case "3" ->
                            manager.getLearnersSorted()
                                    .forEach(System.out::println);

                    case "4" -> {
                        System.out.print("Group name: ");
                        manager.createGroup(scanner.nextLine());

                        System.out.println("Group created.");
                    }

                    case "5" -> {
                        System.out.print("Learner ID: ");
                        String id = scanner.nextLine();

                        System.out.print("Group name: ");
                        String group = scanner.nextLine();

                        manager.addLearnerToGroup(id, group);

                        System.out.println("Learner added to group.");
                    }

                    case "6" -> {
                        System.out.print("Learner ID: ");
                        String id = scanner.nextLine();

                        System.out.println(
                                manager.getLearnerGroups(id));
                    }

                    case "7" -> {
                        System.out.print("Learner ID: ");
                        manager.requestMentorSupport(
                                scanner.nextLine());

                        System.out.println(
                                "Mentor request added to queue.");
                    }

                    case "8" -> {
                        Learner learner =
                                manager.processNextSupportRequest();

                        System.out.println(
                                learner == null
                                        ? "The support queue is empty."
                                        : "Next learner: " + learner);
                    }

                    case "9" -> {
                        System.out.print("Learner ID to remove: ");
                        manager.removeLearner(scanner.nextLine());

                        System.out.println("Learner removed.");
                    }

                    case "10" -> {
                        System.out.println(
                                "Total learners: "
                                        + manager.getTotalLearners());

                        System.out.println(
                                "Support queue size: "
                                        + manager.getSupportQueueSize());

                        System.out.println(
                                "Group membership counts: "
                                        + manager.getGroupMembershipCounts());
                    }

                    case "0" -> {
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;
                    }

                    default ->
                            System.out.println("Invalid menu choice.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}