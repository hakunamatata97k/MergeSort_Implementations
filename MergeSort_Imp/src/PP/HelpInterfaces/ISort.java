package PP.HelpInterfaces;

import org.jetbrains.annotations.NotNull;
import java.util.LinkedList;

/**
 * <pre>
 * The creation of this Pr2.Hausarbeit.Interface is due to the fact that all the Merge sort implementations have to have the following:
 * - Method for sorting {@link ISort#sort(LinkedList)}.
 * - Method for merging the divided lists {@link ISort#merge(LinkedList, LinkedList, LinkedList)}.
 * - Method that specify the comparison logic of the given type T.
 * </pre>
 * @param <T> is generic specified by the user.
 */
 public interface ISort<T> extends Comparable<T> {

    /**
     * Public Method that Manages the Sorting of the forwarded data.
     * @param dataToBeSorted the data to be sorted.
     */
    void sort(@NotNull final LinkedList<T> dataToBeSorted);

    /**
     * This method will merge the pre sorted sub-lists and insert them in the original data list.
     * @param left the left sub-list of the data.
     * @param right the right sub-list of the data.
     * @param dataToBeSorted merging destination.
     */
    @SuppressWarnings("unchecked")
    default void merge(LinkedList<T> left, LinkedList<T> right, final LinkedList<T> dataToBeSorted) {
        int leftIndex, listIndex, rightIndex;

        leftIndex = rightIndex = listIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {

            if ( ((Comparable<T>)left.get(leftIndex)).compareTo(right.get(rightIndex)) <= 0 )
                dataToBeSorted.set(listIndex, left.get(leftIndex++));
            else
                dataToBeSorted.set(listIndex, right.get(rightIndex++));

            listIndex++;
        }
        while (leftIndex < left.size())
            dataToBeSorted.set(listIndex++, left.get(leftIndex++));

        while (rightIndex < right.size())
            dataToBeSorted.set(listIndex++, right.get(rightIndex++));
    }

    @Override
    default int compareTo(@NotNull T o) { return compareTo(o); }
}
