package PP.Benchmark;

import PP.HelpInterfaces.ISort;
import PP.HelpInterfaces.SortUtils;
import PP.MergeSort.ExecutorMergeSorter;
import PP.MergeSort.ForkMergeSorter;
import PP.MergeSort.ParallelMergeSorter;
import PP.MergeSort.SeqMergeSorter;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * This class is made as "benchmark" simulation to check the speed and reliability of the sorting algorithms implemented
 * in the Package {@code PP.Mergesort}.
 * @author KAZEM ALJALABI, 1910732 :)
 */
public class Timer implements Comparable<Timer> {

    private long startTime = 0, endTime = 0, takenTime = 0;
    private final String message;

    public Timer(String message) { this.message = message; }

    /**
     * start the timer/"stop watch".
     */
    public void setStartTime() {
        startTime += System.currentTimeMillis();
        takenTime = endTime - startTime;
    }

    /**
     * stops the timer/"stop watch".
     */
    public void setEndTime() {
        endTime += System.currentTimeMillis();
        takenTime = endTime - startTime;
    }

    /**
     * to be used in setting the time of the class instance which will hold the calculated taken time in all tests.
     * @param taken time to be declared as taken for a certain measuring operations.
     */
    public void setTakenTime(final long taken){
        takenTime = taken;
    }

    /**
     * @return the time taken to execute certain task.
     */
    private long getTakenTime() { return takenTime; }

    @Override
    public String toString() { return String.format("%-50s %-20s %s %n", message, "Time Taken :",getTakenTime()); }

    @Override
    public int compareTo(Timer o) {
        return (int) (getTakenTime() - o.getTakenTime());
    }

    /**
     * This method will print perfectly indented Message with a specific line separator sign.
     * @param lineSeparatorStr the string that will be repeated while printing to simulate line separator.
     * @param message the message that will get printed between both lines.
     */
    private static void printDesign (String lineSeparatorStr, String message) {
        String formatted= (message.length() == 0)? String.format("%s%n",lineSeparatorStr.repeat(80)) : String.format("%s %n%s%n%1$s%n",lineSeparatorStr.repeat(80),message) ;
        System.out.print(formatted);
    }

    /**
     * Method to check the timing of the sorting  of a given data set of type {@link LinkedList} based on multiple iterations.
     * this method will test the timing for the classes {@link SeqMergeSorter} adn {@link ParallelMergeSorter}.
     * @param data data to be sorted given by the user.
     * @param iterations the total number of iteration to which the test will be repeated for the same mathematical set of data.
     * @param obj an instance of the following classes {@link ParallelMergeSorter}, {@link SeqMergeSorter} which will determine the logic of sorting.
     * @return Timer object which contains the taken time taken to preform the sorting divided by the number of iterations.
     */
    @SuppressWarnings("unchecked")
    private static<T> Timer timingWithIterations(final LinkedList<T> data, final int iterations, ISort<T> obj) {
        var cloned = (LinkedList<T>) data.clone();
        var timer = new Timer(obj.getClass().getSimpleName());

        var i = 0;
        while (i++ < iterations){
            timer.setStartTime();
            obj.sort(cloned);
            timer.setEndTime();

            cloned = (LinkedList<T>) data.clone();
        }
        timer.setTakenTime(timer.getTakenTime()/iterations);
        return timer;
    }

    /**
     * Method to check the timing of the sorting  of a given data set of type {@link LinkedList} based on multiple iterations.
     * this method will test the timing for the classes {@link ForkMergeSorter}.
     * @param data data to be sorted given by the user.
     * @param iterations the total number of iteration to which the test will be repeated for the same mathematical set of data.
     * @return Timer object which contains the taken time taken to preform the sorting divided by the number of iterations.
     */
    @SuppressWarnings("unchecked")
    private static<T> Timer timingForkJoin(final LinkedList<T> data, final int iterations) {
        var cloned = (LinkedList<T>) data.clone();
        var timer = new Timer(ForkMergeSorter.class.getSimpleName());

        var i = 0;
        while (i++ < iterations){
            timer.setStartTime();
            ForkMergeSorter.sort(cloned);
            timer.setEndTime();

            cloned = (LinkedList<T>) data.clone();
        }
        timer.setTakenTime(timer.getTakenTime()/iterations);
        return timer;
    }

    /**
     * Method to check the timing of the sorting  of a given data set of type {@link LinkedList} based on multiple iterations.
     * this method will test the timing for the classes {@link ExecutorMergeSorter}.
     * @param data given by the user, which will be cloned and the cloned version would be sorted .
     * @param iterations the total number of iteration to which the test will be repeated for the same mathematical set of data.
     * @return Timer object which contains the taken time taken to preform the sorting divided by the number of iterations.
     */
    @SuppressWarnings("unchecked")
    private static<T> Timer timingExecutor(final LinkedList<T> data, final int iterations) {

        var cloned = (LinkedList<T>) data.clone();
        var timer = new Timer(ExecutorMergeSorter.class.getSimpleName());

        var i = 0;
        while (i++ < iterations) {
            timer.setStartTime();
            ExecutorMergeSorter.sort(cloned);
            timer.setEndTime();

            cloned = (LinkedList<T>) data.clone();
        }
        timer.setTakenTime(timer.getTakenTime()/iterations);
        return timer;
    }

    /**
     * This method will give the Time taken by each specific sorting Algorithm to sort the given data, measured in Milliseconds.
     * @param data on which copy the tests will be preformed.
     * @param iterations to improve the precision.
     * @param <T> the type of the data given by the user!.
     */
    private static<T> void timeLogger (LinkedList<T> data, int iterations) {
        var timeObjects = new Timer[5];

        timeObjects[0] = Timer.timingWithIterations(data,iterations ,new SeqMergeSorter<>());
        timeObjects[1] = Timer.timingWithIterations(data,iterations ,new ParallelMergeSorter<>());
        timeObjects[2] = Timer.timingForkJoin(data,iterations);
        timeObjects[3] = Timer.timingExecutor(data,iterations);

        timeObjects[4] = new Timer("Test done with Random "+ data.size() +" Numbers");

        timeObjects[4].setTakenTime(Arrays.stream(timeObjects).mapToLong(Timer::getTakenTime).sum());

        Arrays.stream(timeObjects).forEach(System.out::print);

        printDesign("-","");
        System.out.printf("%-71s %-20d %n", "Total taken Time in Sec. is  :", timeObjects[4].getTakenTime()/1000 );
    }

    public static void main(String[] args) {
        var iterations = 10;
        var numberOFData = 25000;

        //to give the use the liberty to run the program from the console :).
        if (args.length == 1)
            iterations = Integer.parseInt(args[0]);
        else if (args.length > 1) {
            iterations = Integer.parseInt(args[0]);
            numberOFData = Integer.parseInt(args[1]);
        }else{
            System.out.printf("No Arguments has been given the program will start with %d element and %d iterations.%n", numberOFData,iterations);
        }

        printDesign("-","First Measuring case : Type (normal)");

        //we test first the common case for merge sort which is randomly generated data.
        var data = SortUtils.generateRandomNumberList(numberOFData);
        timeLogger(data, iterations);

        printDesign("-","");

        printDesign("-","Second Measuring case : Type (best case)");

        //we take the same data and overwrite its memory with new values to simulate the best case (already sorted list!:)
        data = SortUtils.generateSortedNumbersList(0,numberOFData);
        timeLogger(data,iterations);

        printDesign("-","Third Measuring case : Type (worst case)");

        //we give the sorting algorithms already sorted list but in descending order!.
        data = SortUtils.generateSortedNumbersList(numberOFData,0);
        timeLogger(data,iterations);


        printDesign("-","Fourth Measuring case : with double the amount of Data given!.");

        data = SortUtils.generateRandomNumberList(numberOFData*2);
        timeLogger(data, iterations);


        printDesign("-","Fifth Measuring case : with 4 times the amount of Data given!.");

        data = SortUtils.generateRandomNumberList(numberOFData*4);
        timeLogger(data, iterations);

        printDesign("-","");
        printDesign("*","End");
    }
}
