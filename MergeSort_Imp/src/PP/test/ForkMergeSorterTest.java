package PP.test;

import PP.MergeSort.ForkMergeSorter;
import PP.HelpInterfaces.SortUtils;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ForkMergeSorterTest {

    @Test
    @SuppressWarnings("unchecked")
    void test_ForkMergeSorter_Empty() {
        var data = new LinkedList<>();
        var temp= (LinkedList<Integer>) data.clone();
        Collections.sort(temp);

        ForkMergeSorter.sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        assertEquals(data.size(),temp.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    void test_ForkMergeSorter_Integers() {
        var data = SortUtils.randomNumber(128);
        var temp= (LinkedList<Integer>) data.clone();
        Collections.sort(temp);


        ForkMergeSorter.sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        data.removeFirst();
        assertNotEquals(temp, data);
    }

    @Test
    @SuppressWarnings("unchecked")
    void test_ForkMergeSorter_Strings() {
        var data= SortUtils.randomString(5,32);

        //        Collections.sort() is since 1.2 and some call it unity thus i used list.sort!
        data.sort(Collections.reverseOrder());//this is the same as Collections.sort(data, Collections.reverseOrder());


        var temp= (LinkedList<String>) data.clone();
        Collections.sort(temp);

        ForkMergeSorter.sort(data);

        assertEquals(temp, data);//this will call the AbstractList.equals. so instead of assertTrue(data.equals(temp));
        data.removeFirst();
        assertNotEquals(temp, data);
    }
}