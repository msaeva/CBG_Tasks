package taskone;

import java.util.Scanner;
import java.util.function.Predicate;

public class JavaLambdaExpressions {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            int type = sc.nextInt();
            int num = sc.nextInt();

            switch (type) {
                case 1:
                    System.out.println(checker(isOdd(), num) ? "ODD" : "EVEN");
                    break;
                case 2:
                    System.out.println(checker(isPrime(), num) ? "PRIME" : "COMPOSITE");
                    break;
                case 3:
                    System.out.println(checker(isPalindrome(), num) ? "PALINDROME" : "NOT PALINDROME");
                    break;
                default:
                    System.out.println("Invalid type!");
                    break;
            }
        }

        sc.close();
    }

    private static boolean checker(Predicate<Integer> p, int num) {
        return p.test(num);
    }

    private static Predicate<Integer> isOdd() {
        return a -> a % 2 != 0;
    }

    private static Predicate<Integer> isPrime() {
        return a -> {
            if (a < 2) return false;
            for (int i = 2; i <= Math.sqrt(a); i++) {
                if (a % i == 0) return false;
            }
            return true;
        };
    }

    private static Predicate<Integer> isPalindrome() {
        return a -> {
            String numString = Integer.toString(a);
            String reverse = new StringBuilder(numString).reverse().toString();
            return numString.equals(reverse);
        };
    }
}
