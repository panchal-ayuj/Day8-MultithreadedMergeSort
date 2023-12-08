import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String args[]) throws ExecutionException, InterruptedException {
        List<Integer> arr = new ArrayList<>();
        arr.add(12);
        arr.add(11);
        arr.add(13);
        arr.add(5);
        arr.add(6);
        arr.add(7);

        ExecutorService pool = Executors.newSingleThreadExecutor();
        MergeSort mergeSorter = new MergeSort(arr);
        Future<List<Integer>> future = pool.submit(mergeSorter);
        List<Integer> sorted = future.get();
        System.out.println(sorted);
        pool.shutdown();
    }
}