package bili.study.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class MyThread implements Callable<Integer> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "***********come in Callable******");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1024;
    }
}


/**
 * @program: hand
 * @description:
 * @author: tianwei
 * @create: 2020-01-06 16:10
 */
public class CallableDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // 两个线程，一个 main 主线程， 一个是 AA futureTask

        // FutureTask(Callable<V> callable)
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());

        new Thread(futureTask, "AA").start();

        new Thread(futureTask, "BB").start();
        // int result02 = futureTask.get();

        System.out.println("****result: " + Thread.currentThread().getName());

        int result01 = 100;

//        while ( !futureTask.isDone() ) { }

        int result02 = futureTask.get();

        System.out.println("****result: " + (result01 + result02));

    }
}
