package taskone;

import java.util.ArrayList;
import java.util.List;

public class JavaGenerics {
    public static void main(String[] args) {
        ArrayList<Integer> i = new ArrayList<>();
        i.add(1);
        i.add(2);
        i.add(3);
        ArrayList<String> str = new ArrayList<>();
        str.add("Hello");
        str.add("World");
        printArray(i);
        printArray(str);
    }
    public static void printArray(List<?> list){
        list.forEach(System.out::println);
    }
}
