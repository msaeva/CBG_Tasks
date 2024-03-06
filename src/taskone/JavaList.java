package taskone;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JavaList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Integer> list = Arrays.stream(scanner.nextLine().split("\\s"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int numberOfQueries = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numberOfQueries; i++) {
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "insert":
                    int[] numbers = Arrays.stream(scanner.nextLine().split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    insertElement(list, numbers[0], numbers[1]);
                    break;
                case "delete":
                    int indexToDelete = Integer.parseInt(scanner.nextLine());
                    deleteElement(list, indexToDelete);
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        }

        list.forEach(a -> System.out.print(a + " "));
    }

    private static void insertElement(List<Integer> list, int index, int value) {
        if (index >= 0 && index <= list.size()) {
            list.add(index, value);
        } else {
            System.out.println("Invalid index for insertion.");
        }
    }

    private static void deleteElement(List<Integer> list, int index) {
        if (index >= 0 && index < list.size()) {
            list.remove(index);
        } else {
            System.out.println("Invalid index for deletion.");
        }
    }
}
