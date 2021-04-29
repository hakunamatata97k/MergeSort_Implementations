package PP.test;

import PP.HelpInterfaces.ISort;
import PP.HelpInterfaces.SortUtils;
import PP.MergeSort.ExecutorMergeSorter;
import PP.MergeSort.ForkMergeSorter;
import PP.MergeSort.ParallelMergeSorter;
import PP.MergeSort.SeqMergeSorter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


/**
 * This class is a unit test to ensure the reliability of the written merge sort implementation derived from the {@link ISort} interface.
 * This unit test class uses the {@link org.junit.jupiter.params.provider.MethodSource} and  {@link org.junit.jupiter.params.ParameterizedTest} annotations to avoid
 * code duplication.
 */
class MergeSortTester {

    @ParameterizedTest
    @MethodSource("testWithIntegerSortObjects")
    @SuppressWarnings("unchecked")
    void test_Empty_Sorter (ISort<Integer> obj) {
        var data = new LinkedList<Integer>();
        var temp= (LinkedList<Integer>) data.clone();
        Collections.sort(temp);

        obj.sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        assertEquals(data.size(),temp.size());
    }



    @ParameterizedTest
    @MethodSource("testWithIntegerSortObjects")
    @SuppressWarnings("unchecked")
    void test_Integers_Sorter (ISort<Integer> obj) {
        var data = SortUtils.generateRandomNumberList(128);
        var temp= (LinkedList<Integer>) data.clone();
        Collections.sort(temp);
        obj.sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        data.removeFirst();
        assertNotEquals(temp, data);
    }

    @ParameterizedTest
    @MethodSource("testWithStringSortObjects")
    @SuppressWarnings("unchecked")
    void test_Strings_Sorter (ISort<String> obj) {
        var data= SortUtils.generateRandomStringsList(5,32);

        //Collections.sort() is since 1.2 and some call it unity thus i used list.sort!
        data.sort(Collections.reverseOrder());//this is the same as Collections.sort(data, Collections.reverseOrder());

        var temp= (LinkedList<String>) data.clone();
        Collections.sort(temp);

        obj.sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        data.removeFirst();
        assertNotEquals(temp, data);
    }

    /**
     * @return Stream<ISort<Integer>>> to feed into the test_Integers_Sorter method.
     */
    static Stream<ISort<Integer>> testWithIntegerSortObjects() {
        return Stream.of(new SeqMergeSorter<>(), new ParallelMergeSorter<>(),new ForkMergeSorter<>(), new ExecutorMergeSorter<>());
    }
    /**
     * @return Stream<ISort<String>>> to feed into the test_Strings_Sorter method.
     */
    static Stream<ISort<String>> testWithStringSortObjects() {
        return Stream.of(new SeqMergeSorter<>(), new ParallelMergeSorter<>(),new ForkMergeSorter<>(), new ExecutorMergeSorter<>());
    }
}