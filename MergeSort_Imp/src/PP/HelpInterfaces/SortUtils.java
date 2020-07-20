package PP.HelpInterfaces;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

public interface SortUtils {
    /**
     * Method will generate a {@link LinkedList} contains randomly generated strings of a specific length provided by the user.
     * @param maxLength the desired max length for the generated string.
     * @param limit the desired size of the returned list.
     * @return {@link LinkedList} which contains randomly generated {@link String}s.
     */
    static LinkedList<String> randomString(final int maxLength,int limit) {
        var ranData=new LinkedList<String>();

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
     * @param limit the sealing of the random generated numbers.
     * @return {@link LinkedList} which contains randomly generated {@link Integer}s with the the size according to the desired limit.
     */
    static LinkedList<Integer> randomNumber(int limit) {
        var ranData=new LinkedList<Integer>();
        new Random().ints(0,limit).limit(limit).forEach(ranData::add);
        return ranData;
    }
}
