package PP.Benchmark;

import PP.HelpInterfaces.ISort;
import PP.HelpInterfaces.SortUtils;
import PP.MergeSort.ExecutorMergeSorter;
import PP.MergeSort.ForkMergeSorter;
import PP.MergeSort.ParallelMergeSorter;
import PP.MergeSort.SeqMergeSorter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;

/**
 * <pre>
 * This class is made as a "Benchmark" simulation to check the speed and reliability of the sorting algorithms implemented in the Following classes found in the Package {@code PP.Mergesort}:
 * - {@link SeqMergeSorter}
 * - {@link ParallelMergeSorter}
 * - {@link ForkMergeSorter}
 * - {@link ExecutorMergeSorter}
 * @author KAZEM ALJALABI :)
 * </pre>
 */
public final class Timer implements Comparable<Timer> {

    private static int iterations = 10;//by default there are 10 iterations.
    private long startTime = 0, endTime = 0, takenTime = 0;
    private final String message;

    public Timer(String message) { this.message = message; }

    /**
     * start the timer/"stop watch".
     */
    public void setStartTime() {
        startTime += System.currentTimeMillis();
        setTakenTime(endTime - startTime);
    }

    /**
     * stops the timer/"stop watch".
     */
    public void setEndTime() {
        endTime += System.currentTimeMillis();
        setTakenTime(endTime - startTime);
    }


    /**
     * to be used in setting the time of the class instance which will hold the calculated taken time in all tests.
     * @param taken time to be declared as taken for a certain measuring operations.
     */
    public void setTakenTime(final long taken){ takenTime = taken; }

    /**
     * @return the time taken to execute certain task.
     */
    private long getTakenTime() { return takenTime; }

    @Override
    public String toString() { return String.format("%-50s %-20s %s %n", message, "Time Taken :",getTakenTime()); }

    @Override
    public int compareTo(@NotNull Timer o) {
        return (int) (getTakenTime() - o.getTakenTime());
    }

    /**
     * This method will print perfectly indented Message with a specific line separator sign.
     * @param lineSeparatorStr the string that will be repeated while printing to simulate line separator.
     * @param message the message that will get printed between both lines.
     */
    private static void printDesign (final String lineSeparatorStr, final String message) {
        String formatted= (message.length() == 0)? String.format("%s%n",lineSeparatorStr.repeat(80)) : String.format("%s %n%s%n%1$s%n",lineSeparatorStr.repeat(80),message) ;
        System.out.print(formatted);
    }

    /**
     * Method to check the timing of the sorting  of a given originalData set of type {@link LinkedList} based on multiple iterations.
     * @param originalData originalData to be sorted given by the user.
     * @param obj an instance of the classes that implements {@link ISort} which will determine the logic of sorting.
     * @return Timer object which contains the taken time taken to preform the sorting divided by the number of iterations.
     */
    @SuppressWarnings("unchecked")
    private static<T> Timer timingWithIterations(final LinkedList<T> originalData, final ISort<T> obj) {

        //for testing the sorting we clone the originalData so that we can get the original state after each iteration.
        var cloned = (LinkedList<T>) originalData.clone();
        var timer = new Timer(obj.getClass().getSimpleName());

        var i = 0;
        while (i++ < iterations){
            timer.setStartTime();
            obj.sort(cloned);
            timer.setEndTime();

            cloned = (LinkedList<T>) originalData.clone();
        }
        timer.setTakenTime(timer.getTakenTime()/iterations);
        return timer;
    }

    /**
     * This method will give the Time taken by each specific implementation of the merge sort Algorithm to sort the given data, measured in Milliseconds.
     * @param data on which copy the tests will be preformed.
     * @param <T> the type of the data given by the user!.
     */
    private static<T> void timeLogger (final LinkedList<T> data) {
        var timeObjects = new Timer[5];

        timeObjects[0] = Timer.timingWithIterations(data ,new SeqMergeSorter<>());
        timeObjects[1] = Timer.timingWithIterations(data ,new ParallelMergeSorter<>());
        timeObjects[2] = Timer.timingWithIterations(data ,new ExecutorMergeSorter<>());
        timeObjects[3] = Timer.timingWithIterations(data ,new ForkMergeSorter<>());
        timeObjects[4] = new Timer(Timer.class.getSimpleName()+ " done with Random " +data.size()+ " Numbers");

        timeObjects[4].setTakenTime(Arrays.stream(timeObjects).mapToLong(Timer::getTakenTime).sum());

        Arrays.stream(timeObjects).forEach(System.out::print);

        printDesign("-","");
        System.out.printf("%-71s %-20d %n", "Total taken Time in Sec. is  :", timeObjects[4].getTakenTime()/1000 );
    }

    /**
     * Method to manage simulating the different cases of the Sorting algorithm.
     * @param caseName String that is either:
     *                 <pre>
     *                  - Best: this simulate the best case scenario for a merge sort algorithm which is pre sorted list in ascending order.
     *                  - Worst: this simulate the worst case scenario for a merge sort algorithm which is pre sorted list in descending order.
     *                  - Normal: this simulate the normal case scenario for a merge sort algorithm which is non sorted list.
     *                 </pre>
     * @param rightBound identifies the limit of the list size and the values present in it.
     */
    private static void measureCasesTime(final String caseName,final int leftBound, final int rightBound) {
        switch (caseName.toLowerCase(Locale.ROOT)) {
            case "normal" -> timeLogger(SortUtils.generateRandomNumberList(rightBound));
            case "best" -> timeLogger(SortUtils.generateSortedNumbersList(leftBound, rightBound));
            case "worst" -> timeLogger(SortUtils.generateSortedNumbersList(rightBound, leftBound));
        }
    }

    public static void main(String[] args) {
        var numberOfData = 1000;

        //to give the use the liberty to run the program from the console :).
        if (args.length == 1)
            iterations = Integer.parseInt(args[0]);
        else if (args.length > 1) {
            iterations = Integer.parseInt(args[0]);
            numberOfData = Integer.parseInt(args[1]);
        }else
            System.out.printf("No Arguments has been given the program will start with %d element and %d iterations.%n", numberOfData,iterations);


        printDesign("-","First Measuring case : Type (normal)");

        //we test first the common case for merge sort which is randomly generated data.
        measureCasesTime("normal",0,numberOfData);

        printDesign("-","");

        printDesign("-","Second Measuring case : Type (best case)");

        //simulate the best case (already sorted list!:)
        measureCasesTime("best", 0, numberOfData);

        printDesign("-","Third Measuring case : Type (worst case)");

        //we give the sorting algorithms already sorted list but in descending order!.
        measureCasesTime("worst", numberOfData, 0);

        printDesign("-","Fourth Measuring case : with double the amount of Data given!.");

        measureCasesTime("normal", 0, numberOfData*2);

        printDesign("-","Fifth Measuring case : with 4 times the amount of Data given!.");

        measureCasesTime("normal", 0,numberOfData*4);

        printDesign("-","");
        printDesign("*","End");
    }
}