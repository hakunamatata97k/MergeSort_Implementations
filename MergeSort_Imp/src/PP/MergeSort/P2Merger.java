package PP.MergeSort;

import PP.HelpInterfaces.ISort;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class P2Merger<T> implements ISort<T> {
    private static final int MAX_THREADS=Runtime.getRuntime().availableProcessors();
    private static final AtomicInteger currentThreadsNum=new AtomicInteger(0);
//    private volatile boolean flag=true;

    @Override
    public void sort(LinkedList<T> dataToBeSorted) {
        if (dataToBeSorted.size()< 2)
            return;

        var mid=dataToBeSorted.size()/2;
        var left = new LinkedList<>(dataToBeSorted.subList(0,mid));
        var right = new LinkedList<>(dataToBeSorted.subList(mid,dataToBeSorted.size()));

        Thread threadedLeft, threadedRight ;
        threadedLeft=threadedRight=null;


        if (currentThreadsNum.get()<MAX_THREADS) {
            threadedLeft = new Thread(() -> sort(left));
            currentThreadsNum.incrementAndGet();
            threadedLeft.start();
        }else
            sort(left);

        if (currentThreadsNum.get()<MAX_THREADS) {
            threadedRight = new Thread(() -> sort(right));
            currentThreadsNum.incrementAndGet();
            threadedRight.start();
        }else
            sort(right);

        try {
            if (null != threadedLeft ) {
                threadedLeft.join();
                currentThreadsNum.decrementAndGet();
            }
            if (null != threadedRight ) {
                threadedRight.join();
                currentThreadsNum.decrementAndGet();
            }
        } catch (InterruptedException e) { e.printStackTrace();}

        merge(left,right,dataToBeSorted);
    }
//    private synchronized void stopExecution (Thread t){
//        flag=false;
//        t.interrupt();
//    }
}
