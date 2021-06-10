package com.o0u0o.javaee;

/**
 * 58同城字符串常量池面试题
 * 方法区和运行时常量池溢出
 * 由于运行时常量池是方法区的一部分，所以这两个区域溢出的测试可以放到一起进行。
 * 前面曾经提到HotSpot从JDK7开始逐渐"去永久代"，并且在JDK8中完全使用元空间来代替永久代的背景故事，
 * 在此我们就可以测试代码来观察一下，使用"永久代"还是"元空间"来实现方法区，对程序有什么实际的影响。
 *
 * String:intern()是一个本地方法，他的作用是如果字符串常量池已经包含一个等于此String对象的字符串，则返回代表
 * 池中这个字符串的String对象的引用；否则，会降此String对象包含字符串添加到常量池中，并且返回此String对象的引用。
 *
 * 【考察点】
 * 1、考察intern() 方法，判断 true / false
 * 2、是否阅读《深入理解Java虚拟机》周志明
 * @author o0u0o
 * @date 2021/6/10 8:27 下午
 */
public class StringPoll58Demo {
    public static void main(String[] args) {

        String str1 = new StringBuilder("58").append("tongcheng").toString();
        System.out.println(str1);
        System.out.println(str1.intern());
        System.out.println(str1 == str1.intern());

        System.out.println();

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2);
        System.out.println(str2.intern());

        /**
         *
         * 输出false 因为"java" 已经在常量池中
         * 在初始化java字符串（JDK出娘胎自带的），在加载sun.misc.Version这个类的时候进入常量池
         * 推演步骤 System  -> initializeSystemClass -> sun.misc.Version.init();
         *
         * 类加载器和rt.jar
         */
        System.out.println(str2 == str2.intern());

    }
}
