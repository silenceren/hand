package bili.study.thread;

import java.util.concurrent.TimeUnit;

class HoldLockThread implements Runnable {

    private String lockA;
    private String lockB;

    public HoldLockThread (String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有："
                    + lockA + "\t 尝试获得：" +lockB);
            try { TimeUnit.SECONDS.sleep( 2); } catch (InterruptedException e) { e.printStackTrace(); }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有" + lockB + "\t 尝试获得：" + lockA);
            }
        }
    }
}



/**
 * @author: tianwei
 * @create: 2020-01-09 10:24
 * 死锁是指两个或两个以上的进程在执行过程中因争夺资源而造成的一种互相等待的现象，若无外力干涉那他们都将无法推进下去
 */
public class DeadLockDemo {

    public static void main(String[] args) {
         String lockA = "lockA";
         String lockB = "lockB";

         new Thread( new HoldLockThread(lockA, lockB), "ThreadAAA").start();
         new Thread( new HoldLockThread(lockB, lockA), "ThreadBBB").start();
    }
}
