package com.o0u0o.juc;

import java.util.concurrent.TimeUnit;

/**
 * LockSupport演示
 * 通过Object类中1wait和notify方法实现线程等待和唤醒
 * wait 和 notify 唤醒机制的问题
 * 【异常1】:
 * wait 和notify 方法，两个都去掉同步代码块，执行报错。
 *
 * 【异常2】：
 * 将notify放在wait方法前面，程序无法唤醒
 *
 * 【总结】：
 * 1、wait 和 notify 方法必须要在同步（synchronized）块或者方法里面且成对出现使用。
 * 2、先wait 后 notify 才 OK
 * @author o0u0o
 * @date 2021/6/11 8:56 上午
 */
public class LockSupportDemo1 {

    static Object objectLock = new Object();

    //main 方法 主线程一切程序的入口
    public static void main(String[] args) {

        //创建一个线程 A
        new Thread(() -> {
            //暂停3秒钟线程
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            synchronized (objectLock){
                System.out.println(Thread.currentThread().getName() + "\t" + "----进来了");
                try {
                    objectLock.wait();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t"  + "---- 被唤醒");
            }
        }, "A线程").start();

        //创建一个线程B
        new Thread(() -> {
            synchronized (objectLock){
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t" + "---- 通知");
            }
        }, "B线程").start();

    }
}
