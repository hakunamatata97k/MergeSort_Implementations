package PP.MergeSort;

import PP.HelpInterfaces.ISort;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


/**
 * <pre>
 * This class is an implementation of the merge sort Algorithm with the following properties :
 *  - work with {@link ForkJoinPool} to achieve the better performance while sub-listing.
 * </pre>
 * @param <T> The type of elements held in {@link LinkedList} collection.
 * @see ISort
 * @see RecursiveAction#compute()
 * @see ForkJoinPool
 */
public final class ForkMergeSorter<T>  extends RecursiveAction implements ISort<T> {

    private LinkedList<T> internData;
    private final ForkJoinPool pool;

    public ForkMergeSorter() {
        pool = new ForkJoinPool();
    }

    private void setInternData (LinkedList<T> data) {
        internData = data;
    }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {
        if (internData.size() < 2) return;

        var mid = internData.size()/2;

        var left = new ForkMergeSorter<T>();
        var right = new ForkMergeSorter<T>();

        left.setInternData( new LinkedList<>( internData.subList(0, mid) ) );
        right.setInternData(new LinkedList<>(internData.subList(mid, internData.size())) );

        invokeAll(left,right);

        merge(left.internData, right.internData, internData);
    }

    /**
     * {@inheritDoc}
     *
     * public method that will manage invoking the Tasks to the {@link ForkMergeSorter#compute()}.
     * creating {@link ForkJoinPool} through its main constructor
     * we get parallelism equal to {@link java.lang.Runtime#availableProcessors}
     *
     * @param dataToBeSorted given by user to be sorted.
     */
    @Override
    public void sort(@NotNull final LinkedList<T> dataToBeSorted) {
        Objects.requireNonNull(dataToBeSorted);
        setInternData(dataToBeSorted);
        if (internData.size() < 2) return;
        pool.invoke(this);
    }
}
