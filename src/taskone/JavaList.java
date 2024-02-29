package taskone;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JavaList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String stringNumbers = scanner.nextLine();
        List<Integer> list = Arrays.stream(stringNumbers
                        .split("\\s"))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        int numberOfQueries = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numberOfQueries; i++) {
            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "insert":
                    int[] numbers = Arrays.stream(scanner.nextLine()
                            .split("\\s+"))
                            .mapToInt(Integer::parseInt).toArray();

                    list.add(numbers[0], numbers[1]);
                    break;
                case "delete":
                    int indexToDelete = Integer.parseInt(scanner.nextLine());
                    list.remove(indexToDelete);
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        }
        list.forEach(a -> System.out.print(a + " "));

    }
}
