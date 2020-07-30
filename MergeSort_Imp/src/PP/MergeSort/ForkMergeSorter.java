package PP.MergeSort;

import PP.HelpInterfaces.IConcurrentSort;

import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


/**
 * <pre>
 * This class is an implementation of the merge sort Algorithm with the following properties :
 *  - work with {@link ForkJoinPool} to achieve the better performance while sub-listing.
 * </pre>
 * @param <T> The type of elements held in {@link LinkedList} collection.
 * @see RecursiveAction#compute()
 * @see ForkJoinPool
 */
public class ForkMergeSorter<T>  extends RecursiveAction implements IConcurrentSort<T> {

    private final LinkedList<T> internData;

    public ForkMergeSorter(LinkedList<T> data) { internData =data; }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {
        if (internData.size() < 2) return;

        var mid = internData.size()/2;
        var left = new ForkMergeSorter<>( new LinkedList<>( internData.subList(0, mid) ) );
        var right = new ForkMergeSorter<>( new LinkedList<>( internData.subList(mid, internData.size()) ) );

        invokeAll(left,right);

        merge(left.internData, right.internData, internData);
    }

    /**
     * public method that will manage invoking the Tasks to the {@link ForkMergeSorter#compute()}.
     * creating {@link ForkJoinPool} through its main constructor
     * we get parallelism equal to {@link java.lang.Runtime#availableProcessors}
     *
     * @param dataToBeSorted given by user to be sorted.
     * @param <T> The type of elements held in {@link LinkedList} collection.
     * @throws NullPointerException if the given data is null.
     */
    public static<T> void sort(LinkedList<T> dataToBeSorted) {
        new ForkJoinPool().invoke(new ForkMergeSorter<>(dataToBeSorted));
    }
}
