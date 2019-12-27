package bili.study.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {
    volatile int number = 0;
    public void addTo60() {
        this.number = 60;
    }
    // 请注意，此时 number 前加了volatile
    public void addPlusPlus() {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic() {
        atomicInteger.getAndIncrement();
    }
}

/**
 * 1.验证 volatile 的可见性
 *   1.1 假如 int number = 0；， number 变量之前根本没有添加 volatile 关键字修饰，没有可见性
 *   1.2 添加了 volatile，可以解决可见性问题
 *
 * 2. 验证 volatile 不保证原子性
 *   2.1 原子性指得是什么意思？
 *       不可分割，有完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割。
 *       需要整体完成    要么同时成功，要么同时失败
 *   2.2 验证 volatile 是否保证原子性
 *   2.3 why
 *   2.4 如何解决原子性？
 *       加sync
 *       使用 juc 下的 AtomicInteger
 *
 *
 */
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            }, String.valueOf(i)).start();
        }

        // 需要等待上面20线程全部计算完成后，再用main线程取得最终的结果值看是多少？
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t int,finally number value: " + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t ato,finally number value: " + myData.atomicInteger);

    }

    // volatile可以保证可见性，及时通知其它线程，主物理内存的值已经被修改
    public static void seeableVolatile() {

        MyData myData = new MyData();   //资源类

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            // 暂停一会线程
            try {
                TimeUnit.SECONDS.sleep( 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t update number value:" + myData.number);
        }, "AAA").start();

        // 第2个就是我们的main线程
        while(myData.number == 0) {
            // mian 线程就一直在这里等待循环，直到number值不再等于零
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over, value:" + myData.number);
    }
}
