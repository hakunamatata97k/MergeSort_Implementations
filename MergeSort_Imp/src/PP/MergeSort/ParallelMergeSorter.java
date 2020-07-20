package PP.MergeSort;

import PP.HelpInterfaces.Isort;

import java.util.LinkedList;


/**
 * <pre>
 * This class is an implementation of the merge sort Algorithm with the following properties :
 *  - work with {@link Thread}s to achive the better performance while sub-listing.
 *  - It's based on the <a href="https://en.wikipedia.org/wiki/Amdahl's_law">Amdahl's law</a>.
 * </pre>
 * @param <T> The type of elements held in {@link LinkedList} collection.
 */
public class ParallelMergeSorter<T> implements Isort<T> {

    /**
     * merge sort the list based on the Amdahl's law.
     * @param dataToBeSorted given by user to be sorted.
     * @throws NullPointerException if the given data is null.
     */
    @Override
    public void sort(LinkedList<T> dataToBeSorted) {
        if (dataToBeSorted.size() < 2)
            return;
        parallelMergeSort(dataToBeSorted, Runtime.getRuntime().availableProcessors());
    }

    /**
     * @param dataToBeSorted given by user to be sorted.
     * @param threadCount the number of the available Logical Processors given by {@link Runtime#getRuntime()}.
     * @throws NullPointerException if the given data is null.
     */
    private void parallelMergeSort(LinkedList<T> dataToBeSorted, int threadCount) {
        if (threadCount <= 1)
            (new SeqMergeSorter<T>()).sort(dataToBeSorted);
        else {
            var mid = dataToBeSorted.size() / 2;

            var left = new LinkedList<>(dataToBeSorted.subList(0, mid));
            var right = new LinkedList<>(dataToBeSorted.subList(mid, dataToBeSorted.size()));

            Thread tl, tr;

            int threadsForBranches = threadCount - 1;
            tl = new Thread(() -> parallelMergeSort(left, threadsForBranches / 2));
            tr = new Thread(() -> parallelMergeSort(right, threadsForBranches - threadsForBranches / 2));

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