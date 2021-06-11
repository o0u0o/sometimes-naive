package com.o0u0o.juc;

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
public class ReEnterLockDemo {

    /**
     * 定义一个锁对象A
     * 每一个锁对象拥有一个锁计数器和一个指向持有该锁的线程指针
     */
    static Object objectLockA =  new Object();

    /**
     * 同步代码块的可重入锁演示
     */
    public static void m1(){
        new Thread(()->{
            synchronized (objectLockA){
                System.out.println(Thread.currentThread().getName() + "\t" + "------外层调用------");
                synchronized (objectLockA){
                    System.out.println(Thread.currentThread().getName() + "\t" + "------中层调用------");
                    synchronized (objectLockA){
                        System.out.println(Thread.currentThread().getName() + "\t" + "------内层调用------");
                    }
                }
            }
        }, "t1线程").start();
    }

    /**
     * 同步方法的可重入锁演示
     */
    public synchronized void m2(){
        System.out.println("------外层调用------");
        m3();
    }

    public synchronized void m3(){
        System.out.println("------中层调用------");
        m4();
    }

    public synchronized void m4(){
        System.out.println("------内层调用------");
    }

    public static void main(String[] args) {
        //m1();
        new ReEnterLockDemo().m2();
    }
}
