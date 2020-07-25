# MergeSort Implementations

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

In this repo I tried my best to implement the MergeSort in various ways: 
  - [sequentailly](https://github.com/hakunamatata97k/MergeSort_Implementations/blob/master/MergeSort_Imp/src/PP/MergeSort/SeqMergeSorter.java) 
  - via Threads (parallel):
    - [optimized Implementation](https://github.com/hakunamatata97k/MergeSort_Implementations/blob/master/MergeSort_Imp/src/PP/MergeSort/ParallelMergeSorter.java)
    - [Naive Implementation](https://github.com/hakunamatata97k/MergeSort_Implementations/blob/master/MergeSort_Imp/src/PP/MergeSort/P2Merger.java)
  - via [WorkStealingThreadPool](https://github.com/hakunamatata97k/MergeSort_Implementations/blob/master/MergeSort_Imp/src/PP/MergeSort/ExecutorMergeSorter.java)
  - via [ForkJoin](https://github.com/hakunamatata97k/MergeSort_Implementations/blob/master/MergeSort_Imp/src/PP/MergeSort/ForkMergeSorter.java)

------------------------------------------------------------------------

### Papers I've read: 
A) [What's New in java 8](https://leanpub.com/whatsnewinjava8) to read it please refer [here.](https://leanpub.com/whatsnewinjava8/read)
B) In order to **maximize the understanding** of mergesort and it's implementations i've read your pdfs and the following papers from open-scources: 

* Knowing and understanding the differance between [Executor], [Executors] and [ExecutorService].
* Reading the **complete** javaDoc of the following: 
    - [ThreadPoolExecutor], [Fork/Join], [RecursiveAction].

* Tried my best to understand the concept of [Amdahl's law].

* Parallel Programming from Zuric [lst.ethz.ch].
* Distributed Algorithms and Optimization (stanford) (partially read) [from Drawbacks of Divide and Conquer].
* [Performance analysis of multithreaded sorting algorithms by Kevin Jouper, Henrik Nordin] (amazing/read just the mergesort parts till now.)

C) In order to optimize the code I did read the following: 
* [Writing Testable Code](https://medium.com/feedzaitech/writing-testable-code-b3201d4538eb).

* chosing the rigt method to generate random data thus its being used in all test cases: 
   - [ `Math.random() versus Random.nextInt(int)` ].
   - [Random Number Generation] (Oracle fourm).
   - [Random Number Generator in Java]. chosing the `ThreadLocalRandom` or `SecureRandom` is a bit overkill for our program.
   - having very little understanding of the [Diehard tests].
* premature optimization `>>>1` vs `/`
  - [wikipedia premature optimization]
  - [Is shifting bits faster than multiplying and dividing in Java?]
  - [Should I bit-shift to divide by 2 in Java?]

------------------------------------------------------------------------

### Problems encountered me through the Coding per exercise: 

##### Exercise 2: (**Contains unreviewed Issue**)
* I could NOT understand the logic behind terminating the threads via a boolean flag and `synchronized` method!.
* understanding the [Amdahl's law].
* I learned that lambdas are not to be treated as Anonymous classes thus according to the book [What's New in java 8] :
    > A lambda expression is not an anonymous class; it actually uses invokedynamic in the byte-code.

##### Exercise 3: 
* the trouble i went throught could be observed in this [StackOverFlow] Question _(asked and answered by me)_.
* Choosing the right Threadpool that will alow the program to work freely without having any memory issues when sorting big data such like `1000.000` . 
    * `newCachedThreadPool` worked till some amount of data i think `70000`then melt down happend.
    * `newFixedThreadPool` failed miserably thus the program would be in hold whenever the threads are busy.
    * `newWorkStealingPool`worked like a charm due to the fact that the workStealing according to the javaDoc:
         >"creates a work-stealing thread pool using all available processors as its target parallelism level." 
    
* managing the  Sorting without wasting objects by making the `sort(LinkedList<T> data) static.

##### Exercise 4: 
* was in general not hard to implement and as in the pervious exercise i had  to make the `sort(LinkedList<T> data) method static.

------------------------------------------------------------------------

### Todo for me!

  - Check with prof if he accept the Second approach. (threads dont need temination flag program works fine without it.)
  - Doing the paper work the fifth exercise. 

------------------------------------------------------------------------
[What's New in java 8]: https://leanpub.com/whatsnewinjava8
 [StackOverFlow]:https://stackoverflow.com/questions/62982442/threadpool-unable-to-create-native-thread/62982939?noredirect=1#comment111408759_62982939
 [from Drawbacks of Divide and Conquer]:https://stanford.edu/~rezab/classes/cme323/S16/notes/Lecture03/cme323_lec3.pdf
 [Amdahl's law]:https://en.wikipedia.org/wiki/Amdahl's_law
 [lst.ethz.ch]:http://www.lst.ethz.ch/teaching/lectures/ss10/24/slides/recitation/week03/mergesort.pdf
 [Executors]:https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executors.html
 [Executor]:https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executor.html
 [ExecutorService]:https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html
 [ThreadPoolExecutor]:https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html
 [Fork/join]:https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html
 [RecursiveAction]:https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/RecursiveAction.html
 [Performance analysis of multithreaded sorting algorithms by Kevin Jouper, Henrik Nordin]:https://www.diva-portal.org/smash/get/diva2:839729/FULLTEXT02
 [ `Math.random() versus Random.nextInt(int)` ]:https://stackoverflow.com/questions/738629/math-random-versus-random-nextintint
 [Random Number Generator in Java]:https://explainjava.com/random-number-generator-java/
 [Diehard tests]:https://en.wikipedia.org/wiki/Diehard_tests
 [Random Number Generation]:https://community.oracle.com/message/6596485#thread-message-6596485
 [wikipedia premature optimization]:https://en.wikipedia.org/wiki/Program_optimization#When_to_optimize
 [Is shifting bits faster than multiplying and dividing in Java?]:https://stackoverflow.com/questions/1168451/is-shifting-bits-faster-than-multiplying-and-dividing-in-java-net
 [Should I bit-shift to divide by 2 in Java?]:https://stackoverflow.com/questions/4072703/should-i-bit-shift-to-divide-by-2-in-java#4072714
