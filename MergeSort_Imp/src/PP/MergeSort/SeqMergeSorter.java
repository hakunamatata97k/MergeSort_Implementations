package PP.MergeSort;

import PP.HelpInterfaces.ISort;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Objects;

/**
 * This class is an implementation of the merge sort Algorithm in sequential order.
 * @param <T> The type of elements held in {@link LinkedList} collection.
 * @see ISort
 */
public final class SeqMergeSorter<T> implements ISort<T> {

    /**
     * {@inheritDoc}
     *
     * Sequentially sorting a Generic {@link LinkedList}
     * @param dataToBeSorted the data to be sorted.
     */
    @Override
    public void sort(@NotNull final LinkedList<T> dataToBeSorted) {
        Objects.requireNonNull(dataToBeSorted);
        if (dataToBeSorted.size() < 2) return;

        var mid = dataToBeSorted.size()/2;
        var left = new LinkedList<>(dataToBeSorted.subList(0, mid));
        var right = new LinkedList<>(dataToBeSorted.subList(mid, dataToBeSorted.size()));

        sort(left);
        sort(right);
        merge(left, right, dataToBeSorted);
    }

}