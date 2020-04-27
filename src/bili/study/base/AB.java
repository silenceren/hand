package bili.study.base;

import java.util.Scanner;

/**
 * @program: hand
 * @description:
 * @author: tianwei
 * @create: 2020-03-04 12:38
 */
public class AB {

    public static int count (int n, int k) {
        if ( n < 1 || k < 0) return -1;
        if ( k == 0 && n < 20) return 1;
        int c = 0;
        int base = 1;
        int round = n;
        while (round > 0 ) {
            int weight = round % 10;
            round /= 10;
            c += round * base;
            if (weight == k) {
                c += (n % base) + 1;
            } else if ( weight < k){
                c += 0;
            } else {
                if ( !(k == 0 && round == 0)) {
                    c += base;
                }
            }
            base *= 10;
        }
        return c;
    }

    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int a = in.nextInt();
//        int b = in.nextInt();
//        System.out.println(count(a, b));
        Clothe clothe = new Clothe();
        Clothe c1 = new Clothe();
        Clothe c2 = new Clothe();
        System.out.println(clothe.getId());
        System.out.println(c1.getId());
        System.out.println(c2.getId());

    }
}
