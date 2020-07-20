package PP.MergeSort;

import PP.HelpInterfaces.ISort;

import java.util.LinkedList;

/**
 * This class is an implementation of the merge sort Algorithm in sequential order.
 * @param <T> The type of elements held in {@link LinkedList} collection.
 */
public class SeqMergeSorter<T> implements ISort<T> {

    @Override
    public void sort(LinkedList<T> dataToBeSorted) {
        if (dataToBeSorted.size() < 2) return;

        int mid = dataToBeSorted.size()/2;
        var left =new LinkedList<>(dataToBeSorted.subList(0, mid));
        var right = new LinkedList<>(dataToBeSorted.subList(mid, dataToBeSorted.size()));

        sort(left);
        sort(right);
        merge(left, right, dataToBeSorted);
    }

}