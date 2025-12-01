package com.Executor;

import java.util.Date;

public class MyRunnable implements Runnable{
    private String command;

    public MyRunnable(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"Start Time"+new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName()+"End Time"+new Date());
    }
    private void processCommand(){
        try {
            // API: Thread.sleep(millis)
            // 作用：让当前正在执行的线程暂停（睡眠）指定的毫秒数。
            // 这里的 5000 毫秒 = 5 秒。
            Thread.sleep(5000);
        }catch (InterruptedException e){
            // API: InterruptedException
            // 作用：当线程在睡眠期间被中断（例如调用了 thread.interrupt()）时抛出的异常。
            e.printStackTrace();;
        }
    }
}
