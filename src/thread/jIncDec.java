package thread;

public class jIncDec {

    private int j;

    private synchronized void inc(){
        j++;
        System.out.println(Thread.currentThread().getName() + " -inc: " + j);
    }

    private synchronized void dec(){
        j--;
        System.out.println(Thread.currentThread().getName() + " -dec: " + j);
    }

    class Inc implements Runnable {
        @Override
        public void run(){
            for(int i = 0; i < 10 ; i++){
                inc();
            }
        }
    }

    class Dec implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < 10 ; i++){
                dec();
            }
        }
    }

    public static void main(String[] args) {
        jIncDec tt = new jIncDec();
        Inc inc = tt.new Inc();
        Dec dec = tt.new Dec();

        for(int i = 0 ; i < 2 ; i++){
            Thread t = new Thread(inc);
            t.start();
            t = new Thread(dec);
            t.start();
        }
    }

}
