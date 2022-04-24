package com.scaffold.zmain.api;

/**
 * @author jingletian
 * @date 2022/4/22 17:15
 */
public class LockSupportDemo {

    // 加锁对象
    static Object objectLock = new Object();

    public static void main(String[] args) {

        // 第二个线程调用 notify()方法，唤醒线程A
        new Thread(()->{
            synchronized (objectLock){
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "：通知");
            }
        },"B").start();

        // 第一个线程调用 wait()方法
        new Thread(()->{
            /*try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            synchronized (objectLock){
                System.out.println(Thread.currentThread().getName() + "：进入并等待");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "：被唤醒");
            }
        },"A").start();


    }
}
