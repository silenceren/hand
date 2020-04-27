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

        int num = 5;

        List<String> location = new ArrayList<>();
        char a = 'A';
        do {
            for (int i = 1; i <= num; i++) {
                location.add(a + Integer.toString(i));
            }
            a++;
        } while ( a <= 'Z');

        location.forEach(System.out::println);
    }
}
