package PP.test;


import PP.MergeSort.SeqMergeSorter;
import PP.HelpInterfaces.SortUtils;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SeqMergeSorterTest {

    @Test
    @SuppressWarnings("unchecked")
    void test_SeqMerge_Empty() {
        var data = new LinkedList<Integer>();
        var temp= (LinkedList<Integer>) data.clone();
        Collections.sort(temp);

        (new SeqMergeSorter<Integer>()).sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        assertEquals(data.size(),temp.size());
    }


    @Test
    @SuppressWarnings("unchecked")
    void test_SeqMerge_Integers() {
        var data = SortUtils.randomNumber(128);
        var temp= (LinkedList<Integer>) data.clone();
        Collections.sort(temp);

        (new SeqMergeSorter<Integer>()).sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        data.removeFirst();
        assertNotEquals(temp, data);
    }

    @Test
    @SuppressWarnings("unchecked")
    void test_SeqMerge_Strings() {
        var data= SortUtils.randomString(5,32);

        data.sort(Collections.reverseOrder());

        var temp= (LinkedList<String>) data.clone();
        Collections.sort(temp);

        (new SeqMergeSorter<String>()).sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        data.removeFirst();
        assertNotEquals(temp, data);
    }
}