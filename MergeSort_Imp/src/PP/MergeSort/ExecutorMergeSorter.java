package PP.MergeSort;

import PP.HelpInterfaces.Iconcurrentsort;

import java.util.LinkedList;
import java.util.concurrent.*;


public class ExecutorMergeSorter<T> implements Iconcurrentsort<T> {

    private final ExecutorService exe;

    public ExecutorMergeSorter() {
//        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        exe = Executors.newWorkStealingPool();
    }

    private void divide(LinkedList<T> dataToBeSorted) {
        if (dataToBeSorted.size() < 2)
            return;

        int mid = dataToBeSorted.size() / 2;
        LinkedList<T> left = new LinkedList<>(dataToBeSorted.subList(0, mid));
        LinkedList<T> right = new LinkedList<>(dataToBeSorted.subList(mid, dataToBeSorted.size()));

        Future<?> f1= (exe.submit(() -> divide(left)));
        Future<?> f2= (exe.submit(() -> divide(right)));

        try {
            f1.get();
            f2.get();
        } catch (InterruptedException | ExecutionException e) {
            new SeqMergeSorter<T>().sort(dataToBeSorted);
//            return;
        }
        merge(left, right, dataToBeSorted);
    }

    public static<T> void sort(LinkedList<T> dataToBeSorted){
        if (dataToBeSorted.size() < 2)
            return;
        var temp=new ExecutorMergeSorter<T>();
        temp.divide(dataToBeSorted);
        temp.exe.shutdownNow();
    }
}