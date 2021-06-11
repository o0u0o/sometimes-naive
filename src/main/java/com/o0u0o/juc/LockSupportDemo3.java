package com.o0u0o.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用 LockSupport 的park() 和 unparK()阻塞个唤醒线程
 * 优点：
 * 1、不需要写在同步代码块中
 * 2、可以先唤醒再阻塞
 *
 * 【源码解析】
 * LockSupport是一个线程阻塞工具类，所有的方法都是静态方法，可以让线程在任意位置阻塞，阻塞之后也有对应的唤醒方法。
 * 归根结底，LockSupport调用的**unsafe**中的native代码
 *
 * LockSupport和每个使用它的线程都有一个许可（permit）关联。相当于1，0  的开关，默认是0
 * 调用一次unpark就会+1 变成1
 * 调用一次park会消费permit，也就是将1变成0，同时park立即返回。
 * 如果再次调用park会变成阻塞（因为permit为0了，会阻塞在这里，一直到permit变为1）这时调用unpark会把permit置为1.
 * 每个线程都有一个相关的permit，permit最多只有一个，重复调用unpark也不会累计凭证。
 * @author o0u0o
 * @date 2021/6/11 7:04 下午
 */
public class LockSupportDemo3 {

    public static void main(String[] args) {
        Thread a = new Thread(()->{
            //暂停几秒钟
            try { TimeUnit.SECONDS.sleep(3L); } catch (InterruptedException e){e.printStackTrace();};

            System.out.println(Thread.currentThread().getName() + "\t" + "----进入");
            //被阻塞，等待通知放行，它要通过许可证。
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t" + "----被唤醒");
        }, "A线程");
        a.start();


        Thread b = new Thread(()->{
            //被阻塞，等待通知放行，它要通过许可证。
            LockSupport.unpark(a);
            System.out.println(Thread.currentThread().getName() + "\t" + "----通知了");
        }, "B线程");
        b.start();
    }
}
