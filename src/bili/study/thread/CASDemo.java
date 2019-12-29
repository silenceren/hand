package bili.study.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: tianwei
 * @create: 2019-12-28 20:46\
 *
 * 1  CAS 是什么 ? ===> compareAndSet
 *    比较并交换
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        // main do things

        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data: " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current data: " + atomicInteger.get());

        atomicInteger.getAndIncrement();

    }

}
