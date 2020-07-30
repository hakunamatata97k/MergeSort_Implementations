package PP.HelpInterfaces;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * <pre>
 * The creation of this Interface is due to the fact that all the Junit testes {@link PP.test} have to have the following:
 * - Method for generating random numbers with the size according to the desired limit {@link SortUtils#generateRandomNumberList(int)}.
 * - Method for generating a {@link LinkedList} contains randomly generated strings of a specific length provided by the user {@link SortUtils#generateRandomStringsList(int, int)}.
 * </pre>
 */
public interface SortUtils {

    /**
     * Method will generate a {@link LinkedList} contains randomly generated strings of a specific length provided by the user.
     * @param maxLength the desired max length for the generated string.
     * @param limit the desired size of the returned list.
     * @return {@link LinkedList} which contains randomly generated {@link String}s.
     */
    static LinkedList<String> generateRandomStringsList(final int maxLength, final int limit) {
        var ranData = new LinkedList<String>();

        IntStream.range(0,limit).forEach(x -> ranData.add(new Random()
                .ints(65,123)
                .filter(i-> (i>64 && i<91) || (i >96 && i<123))
                .mapToObj(i -> (char) i)
                .limit(maxLength)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString())
        );
        return ranData;
    }

    /**
     * generate a {@link LinkedList} populated with Random generated numbers from zero (inclusive) to limit (exclusive)
     * @param limit the sealing of the random generated numbers.
     * @return {@link LinkedList} which contains randomly generated {@link Integer}s with the the size according to the desired limit.
     */
    static LinkedList<Integer> generateRandomNumberList(final int limit) {
        var ranData = new LinkedList<Integer>();
        new Random().ints(0,limit).limit(limit).forEach(ranData::add);
        return ranData;
    }

    /**
     * generate a {@link LinkedList} populated with numbers in sequential order starting with leftBounds (inclusive) to rightBounds (exclusive).
     * created To simulate both of best and worst case of data for the merge sort algorithm.
     * @param  leftBounds the start of the generated sequence of numbers.
     * @param rightBounds the end of the generated sequence of numbers.
     * @return Sorted {@link LinkedList} which contains {@link Integer}s with the the size according to the desired limit+1.
     */
    static LinkedList<Integer> generateSortedNumbersList(final int leftBounds, final int rightBounds){
        var seqData = new LinkedList<Integer>();
        decideOrder(leftBounds,rightBounds).forEach(seqData::add);
        return seqData;
    }

     /**
     * <pre>
     * The help method will create {@link IntStream} with the following logic:
     *      - if left bound is smaller than the right then return IntStream with ascending order.
     *      - if left bound is bigger than the right then return IntStream with descending order.
     * </pre>
     * @param left bound where the count shall start (inclusive).
     * @param right bound where the count shall end (exclusive).
     * @return  {@link IntStream} with a certain order either ascending or descending order.
     */
     private static IntStream decideOrder(final int left, final int right) {
        if (left < right)
            return IntStream.range(left,right);
        else
            return IntStream.iterate(left-1, i -> i >= right, i-> i-1).limit(left-right);
     }
}
