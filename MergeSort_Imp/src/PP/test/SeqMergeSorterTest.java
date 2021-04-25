package PP.test;

import PP.HelpInterfaces.SortUtils;
import PP.MergeSort.SeqMergeSorter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SeqMergeSorterTest {

    @Test
    @SuppressWarnings("unchecked")
    void test_SeqMerge_Empty() {
        var data = new LinkedList<>();
        var temp= (LinkedList<Integer>) data.clone();
        Collections.sort(temp);

        new SeqMergeSorter<>().sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        assertEquals(data.size(),temp.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    void test_SeqMerge_Integers() {
        var data = SortUtils.generateRandomNumberList(128);
        var temp= (LinkedList<Integer>) data.clone();
        Collections.sort(temp);

        new SeqMergeSorter<Integer>().sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        data.removeFirst();
        assertNotEquals(temp, data);
    }

    @Test
    @SuppressWarnings("unchecked")
    void test_SeqMerge_Strings() {
        var data= SortUtils.generateRandomStringsList(5,32);

        //Collections.sort() is since 1.2 and some call it unity thus i used list.sort!
        data.sort(Collections.reverseOrder());//this is the same as Collections.sort(data, Collections.reverseOrder());

        var temp= (LinkedList<String>) data.clone();
        Collections.sort(temp);

        new SeqMergeSorter<String>().sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        data.removeFirst();
        assertNotEquals(temp, data);
    }
}