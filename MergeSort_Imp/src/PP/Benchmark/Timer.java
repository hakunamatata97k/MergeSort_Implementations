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
public class Timer {

    private long startTime=0, endTime=0, takenTime=0;
    private final String message;

    public Timer(String message){ this.message =message; }

    /**
     * start the timer/"stop watch".
     */
    public void setStartTime(){
        startTime+=System.currentTimeMillis();
        takenTime=endTime-startTime;
    }

    /**
     * stops the timer/"stop watch".
     */
    public void setEndTime(){
        endTime+=System.currentTimeMillis();
        takenTime=endTime-startTime;
    }

    /**
     * to be used in setting the time of the class instance which will hold the calculated taken time in all tests.
     * @param taken time to be declared as taken for a certain measuring operations.
     */
    public void setTakenTime(long taken){
        takenTime=taken;
    }

    /**
     *
     * @return the time taken to execute certain task.
     */
    private long getTakenTime(){ return takenTime; }


    @Override
    public String toString() { return String.format("%-50s %-20s %s %n", message, "Time Taken :",getTakenTime()); }

    /**
     * Method to check the timing of the sorting  of a given data set of type {@link LinkedList} based on multiple iterations.
     * this method will test the timing for the classes {@link SeqMergeSorter} adn {@link ParallelMergeSorter}.
     * @param data data to be sorted given by the user.
     * @param iterations the total number of iteration to which the test will be repeated for the same mathematical set of data.
     * @param obj an instance of the following classes {@link ParallelMergeSorter}, {@link SeqMergeSorter} which will determine the logic of sorting.
     * @return Timer object which contains the taken time taken to preform the sorting divided by the number of iterations.
     */
    @SuppressWarnings("unchecked")
    private static<T> Timer timingWithIterations(LinkedList<T> data, int iterations, ISort<T> obj) {
        LinkedList<T> cloned= (LinkedList<T>) data.clone();
        Timer timer = new Timer(obj.getClass().getSimpleName());

        var i=0;
        while (i++ < iterations){
            timer.setStartTime();
            obj.sort(cloned);
            timer.setEndTime();

            cloned=(LinkedList<T>) data.clone();
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
    private static<T> Timer timingForkJoin(LinkedList<T> data, int iterations){
        LinkedList<T> cloned= (LinkedList<T>) data.clone();
        Timer timer = new Timer(ForkMergeSorter.class.getSimpleName());
        var i=0;
        while (i++ < iterations){
            timer.setStartTime();
            ForkMergeSorter.sort(cloned);
            timer.setEndTime();

            cloned=(LinkedList<T>) data.clone();
        }
        timer.setTakenTime(timer.getTakenTime()/iterations);
        return timer;
    }

    /**
     * Method to check the timing of the sorting  of a given data set of type {@link LinkedList} based on multiple iterations.
     * this method will test the timing for the classes {@link ExecutorMergeSorter}.
     * @param data data to be sorted given by the user.
     * @param iterations the total number of iteration to which the test will be repeated for the same mathematical set of data.
     * @return Timer object which contains the taken time taken to preform the sorting divided by the number of iterations.
     */
    @SuppressWarnings("unchecked")
    private static<T> Timer timingExecutor(LinkedList<T> data, int iterations){
        LinkedList<T> cloned= (LinkedList<T>) data.clone();
        Timer timer = new Timer(ExecutorMergeSorter.class.getSimpleName());
        var i=0;
        while (i++ < iterations){
            timer.setStartTime();
            ExecutorMergeSorter.sort(cloned);
            timer.setEndTime();

            cloned=(LinkedList<T>) data.clone();
        }
        timer.setTakenTime(timer.getTakenTime()/iterations);
        return timer;
    }


    public static void main(String[] args) {
        var iterations=1;
        var numberOFData=100000;

        //to give the use the liberty to run the program from the console :).
        if (args.length==1)
            iterations=Integer.parseInt(args[0]);
        else if (args.length>1){
            iterations=Integer.parseInt(args[0]);
            numberOFData=Integer.parseInt(args[1]);
        }


        var timeObjects= new Timer[5];

        var data= SortUtils.randomNumber(numberOFData);


        timeObjects[0] =Timer.timingWithIterations(data,iterations ,new SeqMergeSorter<>());
        timeObjects[1] = Timer.timingWithIterations(data,iterations ,new ParallelMergeSorter<>());
        timeObjects[2] =Timer.timingForkJoin(data,iterations);
        timeObjects[3] =Timer.timingExecutor(data,iterations);

        timeObjects[4] = new Timer("Test done with Random "+ numberOFData+" Numbers");
        timeObjects[4].setTakenTime(Arrays.stream(timeObjects).mapToLong(Timer::getTakenTime).sum());

        Arrays.stream(timeObjects).forEach(System.out::print);
        System.out.println("-".repeat(80));//java 11
        System.out.println(String.format("%-71s %-20s %n", "Total taken Time in Sec. is  :", timeObjects[4].getTakenTime()/1000 ));

    }

}
