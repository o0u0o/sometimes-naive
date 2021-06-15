package com.o0u0o.volatiles;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Volatile 是Java虚拟机提供轻量级的同步机制
 * 1、保证了可见性（某一线程在自己工作空间修改共享变量服副本后，通知到其他线程的工作空间）
 * 2、不保证原子性
 * 3、禁止指令重排
 *
 * 【原子性是什么意思?】
 * 原子性：就是不可分割、完整性，也即某个线程正在做某个业务时，中间不可以被加塞或者分割。
 *        要么同时成功，要么同时失败
 *
 * 验证volatile不保证原子性的案例演示
 *
 * @author o0u0o
 * @date 2021/6/14 11:48 上午
 */
class MyData2{
    volatile int number = 0;

    public void addTo60(){
        this.number = 60;
    }

    //请注意，此时number前面加了valatile关键字修饰的，volatile不保证原子性
    public void addPlusPlus(){
        number++;
    }

    //通过AtomicInteger来解决原子性
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }


}

public class VolatileDemo2 {


    public static void main(String[] args) {


        MyData2 myData2 = new MyData2();

        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    myData2.addPlusPlus();
                    myData2.addMyAtomic();
                }
            }, "线程"+String.valueOf(i)).start();
        }

        //需要等待上面20个线程计算完成后，在使用mian线程取得最终的结构值是多少
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e){ e.printStackTrace(); }
        while (Thread.activeCount() > 2){
            Thread.yield();
        }

        //结算结果并不是20000 volatile 说明不保证原子性
        System.out.println(Thread.currentThread().getName() + "最终计算结果：" + myData2.number);

        System.out.println(Thread.currentThread().getName() + "最终计算结果(保证原子性)：" + myData2.atomicInteger);




    }
}
