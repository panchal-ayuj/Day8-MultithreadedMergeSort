import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Prac {
    static int obj =0;

    public static void main(String[] args) throws InterruptedException {
        final PC pc = new PC();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.a();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t1.join();
        ExecutorService service
                = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++){
            service.submit(() -> {
                try {
                    pc.b();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        service.shutdown();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.a();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
        t2.join();


        System.out.println("Final: " + obj);
    }

    public static class PC {

        public void a() throws InterruptedException {
//            T obj = (T) new Object();
            for(int i = 0; i<10; i++) { // Because we need to produce till the time the code is running
                synchronized (this) {
                    System.out.println("Val: " + obj++);
                }
            }
        }

        public void b() throws InterruptedException {
            synchronized (this) {
                while (obj < 40) {
                    System.out.println("Val: " + obj++);
                }
            }
        }


    }
}
