package thread;

/**
 * @program: hand
 * @description:
 * @author: tianwei
 * @create: 2019-12-12 09:00
 */
public class study {

    private int j = 0;

    private synchronized void inc() {
        ++ this.j;
        System.out.println(Thread.currentThread().getName() + " -inc: " + this.j);
    }

    private synchronized void dec() {
        -- this.j;
        System.out.println(Thread.currentThread().getName() + " -dec: " + this.j);
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                new study().inc();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                new study().dec();
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                new study().inc();
            }
        });
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                new study().dec();
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}