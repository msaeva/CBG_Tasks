package taskone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class JavaSubstringComparisons {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String s = scanner.nextLine();
        int k = Integer.parseInt(scanner.nextLine());

        String result = getSmallestAndLargest(s, k);

        System.out.println(result);
    }

    public static String getSmallestAndLargest(String s, int k) {
        ArrayList<String> substrings = new ArrayList<>();

        for (int i = 0; i <= s.length() - k; i++) {
            substrings.add(s.substring(i, i + k));
        }

        Collections.sort(substrings);

        String smallest = substrings.get(0);
        String largest = substrings.get(substrings.size() - 1);

        return smallest + "\n" + largest;
    }
}
