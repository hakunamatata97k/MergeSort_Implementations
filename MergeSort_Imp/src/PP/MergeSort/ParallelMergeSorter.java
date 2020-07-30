package PP.MergeSort;

import PP.HelpInterfaces.ISort;

import java.util.LinkedList;

/**
 * <pre>
 * This class is an implementation of the merge sort Algorithm with the following properties :
 *  - work with {@link Thread}s to achieve the better performance while sub-listing.
 *  - It's based on the <a href="https://en.wikipedia.org/wiki/Amdahl's_law">Amdahl's law</a>.
 * </pre>
 * @param <T> The type of elements held in {@link LinkedList} collection.
 */
public class ParallelMergeSorter<T> implements ISort<T> {
    private volatile boolean stop = false;

    /**
     * {@inheritDoc}
     * merge sort the list based on the Amdahl's law.
     * @param dataToBeSorted given by user to be sorted.
     * @throws NullPointerException if the given data is null.
     */
    @Override
    public void sort(LinkedList<T> dataToBeSorted) {
        if (dataToBeSorted.size() < 2) return;
        parallelMergeSort(dataToBeSorted, Runtime.getRuntime().availableProcessors());
    }

    /**
     * invoke the threads termination!.
     */
    private void requestStopExecution () {
        stop=true;
    }

    /**
     * @param dataToBeSorted given by user to be sorted.
     * @param threadCount the number of the available Logical Processors given by {@link Runtime#getRuntime()}.
     */
    private void parallelMergeSort(LinkedList<T> dataToBeSorted, int threadCount) {

        if (threadCount <= 1)
            (new SeqMergeSorter<T>()).sort(dataToBeSorted);
        else {
            var mid = dataToBeSorted.size() / 2;

            var left = new LinkedList<>(dataToBeSorted.subList(0, mid));
            var right = new LinkedList<>(dataToBeSorted.subList(mid, dataToBeSorted.size()));

            Thread tl, tr;
            //this will work as levels so you can imagine it as btree. at the level 0-1-2 we already have 8 threads. so when we reach 3rd level we go sequential.
            var threadsForBranches = threadCount - 1;
            tl = new Thread(new Runnable() { //lambda would've been better but you want it this way!:(
                @Override
                public void run() {
                    if (!stop)
                        parallelMergeSort(left, threadsForBranches / 2);
                    else
                        Thread.currentThread().interrupt();
                }
            });

            tr = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!stop)
                        parallelMergeSort(right, threadsForBranches - threadsForBranches / 2);
                    else
                        Thread.currentThread().interrupt();
                }
            });

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