package bili.study.thread;

import java.util.concurrent.*;

/**
 * @program: hand
 * @description: 4 : 使用java多线程的方式，线程池
 * @author: tianwei
 * @create: 2020-01-08 15:22
 */
public class MyThreadPoolDemo {

    public static void main(String[] args) {
//        ExecutorService threadPool = Executors.newFixedThreadPool(5); //一池5个处理线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor(); //一池1个处理线程
//        ExecutorService threadPool = Executors.newCachedThreadPool(); //一池N个处理线程

        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        // 模拟10 个用户来办理业务，每个用户就是一个来自外部的请求线程
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute( () -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

}
