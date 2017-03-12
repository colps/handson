package com.example.java8;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrencyExercise {

    private Lock lockA = new ReentrantLock();
    private ReentrantReadWriteLock readWrite = new ReentrantReadWriteLock();
    // can have multiple conditions
    private Condition lockACondA = lockA.newCondition();

    public class ForkJoinInit extends RecursiveAction {

        String[] str;
        int start, end;

        public ForkJoinInit(String[] str, int start, int end) {
            this.str = str;
            this.start = start;
            this.end = end;
        }
        
        @Override
        protected void compute() {
            if(end-start == 0) {
                str[start] = new String("temp");
            }

            int mid = (start + end)/2;
            // USE THIS
            /* ForkJoinInit left = new ForkJoinInit(str, start, mid);
            left.fork();
            ForkJoinInit right = new ForkJoinInit(str, mid+1, end);
            right.compute();
            left.join();*/
            // OR
            ForkJoinInit left = new ForkJoinInit(str, start, mid);
            ForkJoinInit right = new ForkJoinInit(str, mid+1, end);
            invokeAll(left, right);
        }
    }


    public void lock () {
        lockA.lock();
        try {
            // do work in lock
        } finally {
            lockA.unlock();
        }
    }

    public void lockInterruptibly () {
        try {
            // If a thread is blocked waiting for an intrinsic lock, there is nothing you can do to stop
            // it short of ensuring that it eventually acquires the lock and makes enough progress that
            // you can get its attention some other way. However, the explicit Lock classes offer the
            // lockInterruptibly method, which allows you to wait for a lock and still be responsive to interrupts
            lockA.lockInterruptibly();
            // do work in lock
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockA.unlock();
        }
    }

    /**
     * Reentrancy is implemented by associating with each lock an acquisition count and an owning thread.
     * When the count is zero, the lock is considered unheld. When a thread acquires a previously unheld lock,
     * the JVM records the owner and sets the acquisition count to one. If that same thread acquires the
     * lock again, the count is incremented, and when the owning thread exits the synchronized block,
     * the count is decremented. When the count reaches zero, the lock is released
     */
    public void rentrant () {
        lockA.lock();
        try {
            lockA.lock();
        } finally {

        }
    }


    public void conditionAwait () {
        lockA.lock();
        try {
            lockACondA.await();
            // do work in lock
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockA.unlock();
        }
    }

    public void conditionSignal () {
        lockA.lock();
        try {
            // do work in lock

        } finally {
            lockACondA.signalAll();
            lockA.unlock();
        }
    }
    
    public void readLock () {
        readWrite.readLock().lock();
        try {

        } finally {
            readWrite.readLock().unlock();
        }
    }

    public void writeLock () {
        readWrite.writeLock().lock();
        try {

        } finally {
            readWrite.writeLock().unlock();
        }
    }

    public void multiLock () {
        Lock l1 = new ReentrantLock();
        Lock l2 = new ReentrantLock();
        boolean aq1 = l1.tryLock();
        boolean aq2 = l2.tryLock();
        try{
            if (aq1 && aq2) {
                // work
            }
        } finally {
            if (aq2) l2.unlock(); // don't unlock if not locked
            if (aq1) l1.unlock();
        }
    }

    public void queues () {
        // Queue - {LinkedList, ConcurrentLinkedQueue}
        // Queue -> Dequeue - {ArrayDeque, ConcurrentLinkedDeque, LinkedList}
        // Queue -> BlockingQueue - {ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue, SynchronousQueue}
        // Deque -> BlockingDeque - {ArrayBlockingDeque, LinkedBlockingDeque}
        // BlockingDeque -> TransferQueue - {LinkedTransferQueue}

        // ConcurrentLinkedQueue non blocking
        Queue<String> q = new ConcurrentLinkedQueue<>();
        // throws exception - add, remove, examine
        q.add("");
        String s = q.remove();
        s = q.element();

        // special value - add, remove, examine
        boolean res = q.offer("");
        s = q.poll();
        s = q.peek();

        // blocking operations
        BlockingQueue<String> bq = new LinkedBlockingDeque<String>();
        try {
            // blocks
            bq.put("");
            bq.take();

            // waits for time out
            bq.offer("", 10, TimeUnit.SECONDS);
            bq.poll(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // queue with capacity 1
        SynchronousQueue<String> sq = new SynchronousQueue<>();
        sq.offer("");
        sq.poll();
        sq.peek();

        sq.add("");
        sq.remove();
        sq.element();

        try {
            sq.put("");
            sq.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * The basic difference between TransferQueue and BlockingQueue is that TransferQueue waits for consumer to
     * consume the element. TransferQueue is useful in scenario where message passing need to be guaranteed.
     * TransferQueue makes it possible because producer will wait for consumer to transfer the message. The main
     * method of TransferQueue is transfer(E e)
     */
    public void transferQueue () {
        TransferQueue<String> tq = new LinkedTransferQueue<>();

    }

    public void countdownLatch () {
        CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(
                () -> {
                    latch.countDown();
                    System.out.println("Running t1");
                }
        );

        Thread t2 = new Thread(
                () -> {
                    latch.countDown();
                    System.out.println("Running t2");
                }
        );
        t1.start();
        t2.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            // IMPORTANT - If you catch InterruptedException and are unable to throw it for some reason
            // ensure that you restore the interrupted status
            // The non static interrupt method sets the status and the isInterrupted method returns the status.
            // The static interrupted method clears the interrupt status of the current thread and returns the previous value
            Thread.currentThread().interrupt();
        }
    }

    public void semaphore () {
        // creates a set of 10 permits
        Semaphore permits = new Semaphore(10);

        class  TestTask implements Runnable {
            @Override
            public void run() {
                try {
                    permits.acquire();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                } finally {
                    permits.release();
                }
            }
        }
    }

    public void cyclicBarrier () {
        Runnable barrier1Action = new Runnable() {
            public void run() {
                System.out.println("BarrierAction 1 executed ");
            }
        };
        Runnable barrier2Action = new Runnable() {
            public void run() {
                System.out.println("BarrierAction 2 executed ");
            }
        };

        CyclicBarrier barrier1 = new CyclicBarrier(2, barrier1Action);
        CyclicBarrier barrier2 = new CyclicBarrier(2, barrier2Action);

        CyclicBarrierRunnable barrierRunnable1 =
                new CyclicBarrierRunnable(barrier1, barrier2);

        CyclicBarrierRunnable barrierRunnable2 =
                new CyclicBarrierRunnable(barrier1, barrier2);

        new Thread(barrierRunnable1).start();
        new Thread(barrierRunnable2).start();
    }

    public class CyclicBarrierRunnable implements Runnable{

        CyclicBarrier barrier1 = null;
        CyclicBarrier barrier2 = null;

        public CyclicBarrierRunnable(
                CyclicBarrier barrier1,
                CyclicBarrier barrier2) {

            this.barrier1 = barrier1;
            this.barrier2 = barrier2;
        }

        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() +
                        " waiting at barrier 1");
                this.barrier1.await();

                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() +
                        " waiting at barrier 2");
                this.barrier2.await();

                System.out.println(Thread.currentThread().getName() +
                        " done!");

            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public void exchanger () {
        Exchanger exchanger = new Exchanger();

        ExchangerRunnable exchangerRunnable1 =
                new ExchangerRunnable(exchanger, "A");

        ExchangerRunnable exchangerRunnable2 =
                new ExchangerRunnable(exchanger, "B");

        new Thread(exchangerRunnable1).start();
        new Thread(exchangerRunnable2).start();
    }

    public class ExchangerRunnable implements Runnable{

        Exchanger exchanger = null;
        Object    object    = null;

        public ExchangerRunnable(Exchanger exchanger, Object object) {
            this.exchanger = exchanger;
            this.object = object;
        }

        public void run() {
            try {
                Object previous = this.object;

                this.object = this.exchanger.exchange(this.object);

                System.out.println(
                        Thread.currentThread().getName() +
                                " exchanged " + previous + " for " + this.object
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
