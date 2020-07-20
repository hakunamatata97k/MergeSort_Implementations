package PP.Benchmark;

import PP.MergeSort.*;
import PP.HelpInterfaces.Isort;
import PP.HelpInterfaces.SortUtils;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * This class is made as "benchmark" simulation to check the speed and reliability of the sorting algorithms implemented
 * in the Package {@code PP.Mergesort}.
 */
public class Timer {


    private long startTime=0, endTime=0, takenTime=0;
    private final String message;

    public Timer(String message){ this.message =message; }

    public void setStartTime(){
        startTime+=System.currentTimeMillis();
        takenTime=endTime-startTime;
    }

    public void setEndTime(){
        endTime+=System.currentTimeMillis();
        takenTime=endTime-startTime;
    }

    public void setTakenTime(long taken){
        takenTime=taken;
    }

    private long getTakenTime(){ return takenTime; }


    @Override
    public String toString() { return String.format("%-50s %-20s %s %n", message, "Time Taken :",getTakenTime()); }

    /**
     * Method to check the timing of the sorting  of a given data set of type {@link LinkedList} based on multiple iterations.
     * @param data data to be sorted given by the user.
     * @param iterations the total number of iteration to which the ExecutorMergeSorter will be repeated for the same mathematical set of data.
     * @param obj an instance of the following classes {@link ParallelMergeSorter}, {@link SeqMergeSorter} which will determine the logic of sorting.
     * @return Timer object which contains the taken time for the passed object.
     */
    @SuppressWarnings("unchecked")
    private static<T> Timer timingWithIterations(LinkedList<T> data, int iterations, Isort<T> obj) {
        LinkedList<T> cloned= (LinkedList<T>) data.clone();
        Timer timer = new Timer(obj.getClass().getSimpleName());

        var i=0;
        while (i++ < iterations){
            timer.setStartTime();
            obj.sort(cloned);
            timer.setEndTime();

            cloned=(LinkedList<T>) data.clone();
        }
        return timer;
    }



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
        return timer;
    }


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
        Timer [] timeObjects= new Timer[5];

        LinkedList<Integer> data= SortUtils.randomNumber(numberOFData);


        timeObjects[0] =Timer.timingWithIterations(data,iterations ,new SeqMergeSorter<>());
        timeObjects[1]= Timer.timingWithIterations(data,iterations ,new ParallelMergeSorter<>());
        timeObjects[2]=Timer.timingForkJoin(data,iterations);
        timeObjects[3]=Timer.timingExecutor(data,iterations);

        timeObjects[4]= new Timer("Test done with Random "+ numberOFData+" Numbers");
        timeObjects[4].setTakenTime(Arrays.stream(timeObjects).mapToLong(Timer::getTakenTime).sum());

        Arrays.stream(timeObjects).forEach(System.out::print);

    }
}
