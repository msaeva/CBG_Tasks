package taskone;

import java.util.*;
import java.util.stream.Collectors;

public class JavaArraylist {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());

        HashMap<Integer, List<Integer>> map = new LinkedHashMap<>();
        for (int i = 1; i <= n; i++) {

            List<Integer> list = new ArrayList<>(Arrays.stream(scanner.nextLine()
                            .split("\\s"))
                    .mapToInt(Integer::parseInt)
                    .boxed().skip(1)
                    .collect(Collectors.toList()));

            map.put(i, list);
        }

        n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            String[] split = scanner.nextLine()
                    .split("\\s");

            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);

            if (map.containsKey(x)) {
                List<Integer> integers = map.get(x);
                if (integers.size() >= y) {
                    System.out.println(integers.get(y - 1));
                    continue;
                }
            }
            System.out.println("ERROR!");
        }
    }
}
