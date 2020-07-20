package PP.MergeSort;

import PP.HelpInterfaces.IConcurrentSort;

import java.util.LinkedList;
import java.util.concurrent.*;


public class ExecutorMergeSorter<T> implements IConcurrentSort<T> {

    private final ExecutorService exe;

    public ExecutorMergeSorter() {
        exe = Executors.newWorkStealingPool();
    }

    private void divide(LinkedList<T> dataToBeSorted) {
        if (dataToBeSorted.size() < 2)
            return;

        int mid = dataToBeSorted.size() / 2;
        var left = new LinkedList<>(dataToBeSorted.subList(0, mid));
        var right = new LinkedList<>(dataToBeSorted.subList(mid, dataToBeSorted.size()));

        Future<?> f1= (exe.submit(() -> divide(left)));
        Future<?> f2= (exe.submit(() -> divide(right)));

        try {
            f1.get();
            f2.get();
        } catch (InterruptedException | ExecutionException e) {
            //if something bad happens go sort sequentially. this wont be executed unless the cpu is on fire and you are on fire.
            new SeqMergeSorter<T>().sort(dataToBeSorted);
        }
        merge(left, right, dataToBeSorted);
    }

    public static<T> void sort(LinkedList<T> dataToBeSorted){
        if (dataToBeSorted.size() < 2)
            return;
        var temp=new ExecutorMergeSorter<T>();
        temp.divide(dataToBeSorted);
        temp.exe.shutdownNow();//after the sorting there will be no thread alive :)
    }
}