package PP.test;

import PP.MergeSort.ParallelMergeSorter;
import PP.HelpInterfaces.SortUtils;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ParallelMergeSorterTest {

    @Test
    @SuppressWarnings("unchecked")
    void test_ParallelMerge_Empty() {
        LinkedList<Integer> data = new LinkedList<>();
        var temp= (LinkedList<Integer>) data.clone();
        Collections.sort(temp);

        (new ParallelMergeSorter<Integer>()).sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        assertEquals(data.size(),temp.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    void test_ParallelMerge_Integers() {
        LinkedList<Integer> data = SortUtils.randomNumber(128);
        var temp= (LinkedList<Integer>) data.clone();
        Collections.sort(temp);

        (new ParallelMergeSorter<Integer>()).sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        data.removeFirst();
        assertNotEquals(temp, data);
    }

    @Test
    @SuppressWarnings("unchecked")
    void test_ParallelMerge_Strings() {
        LinkedList<String> data= SortUtils.randomString(5,32);
        data.sort(Collections.reverseOrder());

        var temp= (LinkedList<String>) data.clone();
        Collections.sort(temp);

        (new ParallelMergeSorter<String>()).sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        data.removeFirst();
        assertNotEquals(temp, data);
    }
}