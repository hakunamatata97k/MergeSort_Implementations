package PP.MergeSort;

import PP.HelpInterfaces.Iconcurrentsort;

import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkMergeSorter<T>  extends RecursiveAction implements Iconcurrentsort<T> {
    private final LinkedList<T> internData;

    public ForkMergeSorter(LinkedList<T> data){ internData =data; }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {
        if (internData.size() < 2)
            return;

        var mid = internData.size()/2;
        var left = new ForkMergeSorter<>( new LinkedList<>( internData.subList(0, mid) ) );
        var right = new ForkMergeSorter<>( new LinkedList<>( internData.subList(mid, internData.size()) ) );

        invokeAll(left,right);

        merge(left.internData, right.internData, internData);
    }

    public static<T> void sort(LinkedList<T> dataToBeSorted) {
        new ForkJoinPool(Runtime.getRuntime().availableProcessors())
                .invoke(new ForkMergeSorter<>(dataToBeSorted));
    }
}
