// Java program for Merge Sort
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class MergeSort implements Callable<List<Integer>> {
    private List<Integer> list;

    public MergeSort(List<Integer> list) {
        this.list = list;
    }

    public List<Integer> call() throws InterruptedException, ExecutionException{
        if(list.size() <= 1){
            return list;
        } else {
            int mid = list.size()/2;
            List<Integer> sublist1 = list.subList(0, mid);
            List<Integer> sublist2 = list.subList(mid, list.size());

            MergeSort left = new MergeSort(sublist1);
            MergeSort right = new MergeSort(sublist2);

            ExecutorService pool = Executors.newFixedThreadPool(2);

            Future<List<Integer>> sortedLeft = pool.submit(left);
            Future<List<Integer>> sortedRight = pool.submit(right);

            List<Integer> sortedSublist1 = sortedLeft.get();
            List<Integer> sortedSublist2 = sortedRight.get();

            pool.shutdown();
            return merge(sortedSublist1, sortedSublist2);
        }
    }

    private List<Integer> merge(List<Integer> sortedSublist1, List<Integer> sortedSublist2) {
        List<Integer> myList = new ArrayList<>();
        int i = 0, j = 0;
        while(i<sortedSublist1.size() && j<sortedSublist2.size()){
            if(sortedSublist1.get(i) <= sortedSublist2.get(j)){
                myList.add(sortedSublist1.get(i++));
            } else {
                myList.add(sortedSublist2.get(j++));
            }
        }
        while (i < sortedSublist1.size()) {
            myList.add(sortedSublist1.get(i++));
        }

        while (j < sortedSublist2.size()) {
            myList.add(sortedSublist2.get(j++));
        }
        return myList;
    }
}
