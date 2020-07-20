package PP.HelpInterfaces;

import java.util.LinkedList;

public interface Iconcurrentsort<T> extends Comparable<T> {

    /**
     * This method will merge the pre sorted sub-lists and insert them in the original data list.
     * @param left the left sub-list of the data.
     * @param right the righ sub-list of the data.
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
    default int compareTo(T o) {
        return compareTo(o);
    }

}
