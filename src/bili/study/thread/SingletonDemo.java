package bili.study.thread;

/**
 * @program: hand
 * @description:
 * @author: tianwei
 * @create: 2019-12-27 17:38
 */
public class SingletonDemo {

    private static SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t我是构造方法SingletonDemo()");
    }
    public static SingletonDemo getInstance() {
        if (instance == null) {
            instance = new SingletonDemo();
        }
        return instance;
    }

    public static void main(String[] args) {
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        // 并发多线程后，情况发生了变化
        for (int i = 0; i < 10; i++) {
            new Thread(() -> SingletonDemo.getInstance(), String.valueOf(i)).start();
        }

    }

}
