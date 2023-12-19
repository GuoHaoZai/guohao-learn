package guohao.learn;

import org.apache.ignite.lang.IgniteRunnable;

public class MyRunnable implements IgniteRunnable {
    private static final long serialVersionUID = -8491962424023801613L;
    private int _index;

    MyRunnable(int index) {
        this._index = index;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
        }
        System.out.println(String.format("Processed %d", _index));
    }
}