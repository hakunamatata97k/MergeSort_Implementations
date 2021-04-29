package PP.MergeSort;

import PP.HelpInterfaces.ISort;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <pre>
 * This class is an implementation of the merge sort Algorithm with the following properties :
 *  - work with {@link ExecutorService} to achieve the better performance while sub-listing.
 *  - It's based on the Work stealing concept .
 *  - merge sort based on the divide and conquer concept.
 * </pre>
 * @param <T> The type of elements held in {@link LinkedList} collection.
 * @see ISort
 * @see Executors#newWorkStealingPool()
 */
public final class ExecutorMergeSorter<T> implements ISort<T> {

    private final ExecutorService exe;

    public ExecutorMergeSorter() {
        //Creates a work-stealing thread pool
        exe = Executors.newWorkStealingPool();
    }

    /**
     * it will divide the original data recursively till each sublist has 2 elements.
     * @param dataToBeSorted given by user to be sorted.
     */
    private void divide(final LinkedList<T> dataToBeSorted) {
        if (dataToBeSorted.size() < 2) return;

        var mid = dataToBeSorted.size() / 2;
        var left = new LinkedList<>(dataToBeSorted.subList(0, mid));
        var right = new LinkedList<>(dataToBeSorted.subList(mid, dataToBeSorted.size()));

        Future<?> f1 = (exe.submit(() -> divide(left)));
        Future<?> f2 = (exe.submit(() -> divide(right)));

        try {
            //hold till the execution is done!:)
            f1.get();
            f2.get();
        } catch (InterruptedException | ExecutionException e) {
            //if something bad happens go sort sequentially.
            new SeqMergeSorter<T>().sort(dataToBeSorted);
        }
        merge(left, right, dataToBeSorted);
    }

    /**
     * {@inheritDoc}
     *
     * will manage giving the forwarded data to the divide method and thus sorting it with the help of {@link ExecutorService}.
     * @param dataToBeSorted given by user to be sorted.
     */
    @Override
    public void sort(@NotNull final LinkedList<T> dataToBeSorted) {
        Objects.requireNonNull(dataToBeSorted);
        if (dataToBeSorted.size() < 2) return;
        divide(dataToBeSorted);
    }
}