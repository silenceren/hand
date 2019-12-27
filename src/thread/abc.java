package thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: hand
 * @description:
 * @author: tianwei
 * @create: 2019-12-16 15:53
 */
public class abc {

    public static void main(String[] args) {

        char from = 'A';
        char to = 'z';
        int num = 5;

        List<String> location = new ArrayList<>();
        char a = from;
        do {
            for (int i = 1; i <= num; i++) {
                String sb = a + Integer.toString(i);
                location.add(sb);
            }
            a++;
        } while ( a <= to);

        location.forEach(System.out::println);
    }
}
