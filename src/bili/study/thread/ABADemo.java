package bili.study.thread;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @program: hand
 * @description:
 * @author: tianwei
 * @create: 2019-12-29 20:01
 */
public class ABADemo {

    public static void main(String[] args) {
        new Thread(() -> {

        }).start();
    }
}

