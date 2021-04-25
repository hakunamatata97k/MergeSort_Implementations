package PP.MergeSort;

import PP.HelpInterfaces.ISort;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Objects;

/**
 * <pre>
 * This class is an implementation of the merge sort Algorithm with the following properties :
 *  - work with {@link Thread}s to achieve the better performance while sub-listing.
 *  - It's based on the <a href="https://en.wikipedia.org/wiki/Amdahl's_law">Amdahl's law</a>.
 * </pre>
 * @param <T> The type of elements held in {@link LinkedList} collection.
 */
public final class ParallelMergeSorter<T> implements ISort<T> {

    /**
     * {@inheritDoc}
     *
     * Implements the Merge sort Algorithm based on the Amdahl's law.
     * @param dataToBeSorted given by user to be sorted.
     */
    @Override
    public void sort(@NotNull final LinkedList<T> dataToBeSorted) {
        Objects.requireNonNull(dataToBeSorted);
        if (dataToBeSorted.size() < 2) return;
        parallelMergeSort(dataToBeSorted, Runtime.getRuntime().availableProcessors());
    }

    /**
     * @param dataToBeSorted given by user to be sorted.
     * @param threadCount the number of the available Logical Processors given by {@link Runtime#getRuntime()}.
     */
    private void parallelMergeSort(final LinkedList<T> dataToBeSorted, final int threadCount) {

        if (threadCount <= 1)
            (new SeqMergeSorter<T>()).sort(dataToBeSorted);
        else {
            var mid = dataToBeSorted.size() / 2;

            var left = new LinkedList<>(dataToBeSorted.subList(0, mid));
            var right = new LinkedList<>(dataToBeSorted.subList(mid, dataToBeSorted.size()));

            Thread tl, tr;
            //this will work as levels so you can imagine it as btree. at the level 0-1-2 we already have 8 threads. so when we reach 3rd level we go sequential.
            var threadsForBranches = threadCount - 1;

            tl = new Thread(() -> parallelMergeSort(left, threadsForBranches / 2) );

            tr = new Thread(() -> parallelMergeSort(right, threadsForBranches - threadsForBranches / 2) );

            tl.start();
            tr.start();

            try {
                tl.join();
                tr.join();
            } catch (InterruptedException e) { e.printStackTrace(); }

            merge(left, right, dataToBeSorted);
        }
    }
}