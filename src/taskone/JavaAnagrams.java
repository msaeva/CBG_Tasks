package taskone;

import java.io.IOException;
import java.util.*;

public class JavaAnagrams {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String string1 = scanner.nextLine();
        String string2 = scanner.nextLine();
        if (areAnagrams(string1.toUpperCase(), string2.toUpperCase())) {
            System.out.println("Anagrams");
        } else {
            System.out.println("Not Anagrams");
        }
    }

    public static boolean areAnagrams(String s1, String s2) {
        if (s1.equalsIgnoreCase(s2)) return true;
        if (s1.length() != s2.length()) return false;

        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s1.length(); i++) {
            char symbol = s1.charAt(i);
            map.put(symbol, map.getOrDefault(symbol, 0) + 1);
        }
        for (int i = 0; i < s2.length(); i++) {
            char symbol = s2.charAt(i);

            if (!map.containsKey(symbol) || map.get(symbol) == 0) {
                return false;
            }
            map.put(symbol, map.get(symbol) - 1);

            if (map.get(symbol) == 0) {
                map.remove(symbol);
            }
        }
        return map.isEmpty();
    }
}