package com.o0u0o.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author o0u0o
 * @date 2021/6/11 5:44 下午
 */
public class LockSupportDemo2 {

    static Object object = new Object();
    static Lock lock = new ReentrantLock();
    static Condition condition =  lock.newCondition();

    public static void main(String[] args) {

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t" + "----进入");
                try {
                    condition.await();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "----被唤醒");
            } finally {
                lock.unlock();
            }
        }, "A线程").start();

        new Thread(()->{
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t" + "----通知");
            } finally {
                lock.unlock();
            }
        }, "B线程").start();

    }

}
