package PP.HelpInterfaces;

import java.util.LinkedList;

/**
 * <pre>
 * The creation of this Interface is due to the fact that all the Merge sort implementations have to have the following:
 * - Method for sorting {@link ISort#sort(LinkedList)}.
 * - Method for merging the divided lists {@link ISort#merge(LinkedList, LinkedList, LinkedList)}.
 * - Method that specify the comparision logic of the given type T.
 * </pre>
 * @param <T> is generic specified by the user.
 */
 public interface ISort<T> extends Comparable<T> {

    /**
     * Classes that implement this Interface have to define the Logic for the sorting.
     * @param dataToBeSorted the data to be sorted.
     * @throws NullPointerException if the given data is null.
     */
     void sort(LinkedList<T> dataToBeSorted);



    /**
     * This method will merge the pre sorted sub-lists and insert them in the original data list.
     * @param left the left sub-list of the data.
     * @param right the right sub-list of the data.
     * @param dataToBeSorted merging destination.
     */
    @SuppressWarnings("unchecked")
    default void merge(LinkedList<T> left, LinkedList<T> right, LinkedList<T> dataToBeSorted) {
        int leftIndex, listIndex,rightIndex;

        leftIndex=rightIndex=listIndex=0;

        while (leftIndex < left.size() && rightIndex < right.size()) {

            if ( ((Comparable<T>)left.get(leftIndex)).compareTo(right.get(rightIndex)) <=0 )
                dataToBeSorted.set(listIndex++, left.get(leftIndex++));
            else
                dataToBeSorted.set(listIndex++, right.get(rightIndex++));

        }
        while (leftIndex < left.size())
            dataToBeSorted.set(listIndex++, left.get(leftIndex++));

        while (rightIndex < right.size())
            dataToBeSorted.set(listIndex++, right.get(rightIndex++));
    }

    @Override
    default int compareTo(T o) { return compareTo(o); }

}
