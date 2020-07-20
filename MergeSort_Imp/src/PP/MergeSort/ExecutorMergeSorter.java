package PP.MergeSort;

import PP.HelpInterfaces.IConcurrentSort;

import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * <pre>
 * This class is an implementation of the merge sort Algorithm with the following properties :
 *  - work with {@link ExecutorService} to achieve the better performance while sub-listing.
 *  - It's based on the Work stealing concept .
 *  - merge sort based on the divide and conquer concept.
 * </pre>
 * @param <T> The type of elements held in {@link LinkedList} collection.
 * @see Executors#newWorkStealingPool()
 */
public class ExecutorMergeSorter<T> implements IConcurrentSort<T> {

    private final ExecutorService exe;

    public ExecutorMergeSorter() {
        exe = Executors.newWorkStealingPool();
    }

    /**
     * it will divide the original data recursively till each sublist has 2 elements.
     * @param dataToBeSorted given by user to be sorted.
     */
    private void divide(LinkedList<T> dataToBeSorted) {
        if (dataToBeSorted.size() < 2)
            return;

        int mid = dataToBeSorted.size() / 2;
        var left = new LinkedList<>(dataToBeSorted.subList(0, mid));
        var right = new LinkedList<>(dataToBeSorted.subList(mid, dataToBeSorted.size()));

        Future<?> f1= (exe.submit(() -> divide(left)));
        Future<?> f2= (exe.submit(() -> divide(right)));

        try {
            //hold till the execution is done!:)
            f1.get();
            f2.get();
        } catch (InterruptedException | ExecutionException e) {
            //if something bad happens go sort sequentially. this wont be executed unless the cpu is on fire and you are on fire.
            new SeqMergeSorter<T>().sort(dataToBeSorted);
        }
        merge(left, right, dataToBeSorted);
    }

    /**
     * public method that will manage giving the forwarded data to the divide method and the termination of execution.
     * @param dataToBeSorted given by user to be sorted.
     * @param <T> The type of elements held in {@link LinkedList} collection.
     * @throws NullPointerException if the given data is null.
     */
    public static<T> void sort(LinkedList<T> dataToBeSorted){
        if (dataToBeSorted.size() < 2) return;

        var temp=new ExecutorMergeSorter<T>();
        temp.divide(dataToBeSorted);
        temp.exe.shutdownNow();//after the sorting there will be no thread alive :)
    }

}