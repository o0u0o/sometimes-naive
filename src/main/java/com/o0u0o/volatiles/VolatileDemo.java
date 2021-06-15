package com.o0u0o.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * Volatile 是Java虚拟机提供轻量级的同步机制
 * 1、保证了可见性（某一线程在自己工作空间修改共享变量服副本后，通知到其他线程的工作空间）
 * 2、不保证原子性
 * 3、禁止指令重排
 * @author o0u0o
 * @date 2021/6/14 11:03 上午
 */
class MyData{

    volatile int number = 0;

    public void addTo60(){
        this.number =  60;
    }
}

/**
 * 验证Volatile的可见性
 * 1、假设 int number = 0;  number变量之前没有添加（没有可见性）
 */
public class VolatileDemo {

    /**
     * main 是一切方法的运行入口
     * @param args
     */
    public static void main(String[] args) {

        //资源类
        MyData myData = new MyData();

        //线程A
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "进来了");
            //线程休眠3秒
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e){ e.printStackTrace(); }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "更新了number值为:" + myData.number);
        }, "线程A").start();

        //第二个线程，主线程
        while (myData.number == 0){
            //mian线程在这里一直等待循环，直到number值不再等于零
        }
        System.out.println(Thread.currentThread().getName() + "任务完成" + myData.number);
    }
}
