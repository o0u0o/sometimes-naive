package com.o0u0o.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程 重入锁演示案例
 * 可重入锁：可重复递归调用的锁，在外层使用锁之后，在内层仍然可以使用，并且不会发生死锁，
 * 这样的锁叫做可重入锁。
 *
 * 在一个synchronized 修饰的方法或者代码块内部
 * 调用本类的其他synchronized 修饰的方法或者代码块时，是永远的到锁的
 * @author o0u0o
 * @date 2021/6/11 7:31 上午
 */
public class ReEnterLockDemo2 {

    //显式锁
    static Lock lock = new ReentrantLock();


    public static void main(String[] args) {

        new Thread(()->{
            lock.lock();
            try {
                System.out.println("=== 外层调用 ===");

                lock.lock();
                try {
                    System.out.println("=== 中层调用 ===");

                    lock.lock();
                    try {
                        System.out.println("=== 内层调用 ===");
                    } finally {
                        lock.unlock();
                    }

                } finally {
                    lock.unlock();
                }

            } finally {
                /**
                 * 由于加锁次数和释放次数不一样，第二个线程始终无法获取到锁，导致一直等待。
                 * 正常情况下，加锁几次就要解锁几次
                 */
                lock.unlock();
            }
        }, "T1").start();
    }
}
