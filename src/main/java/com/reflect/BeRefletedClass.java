package com.reflect;

import static java.lang.Thread.sleep;
import static java.time.LocalTime.now;

public class BeRefletedClass {
    private int value1;
    private String value2;

    public BeRefletedClass(int value1) {
        this.value1 = value1;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public BeRefletedClass(String value2){
        this.value2=value2;
    }
    public BeRefletedClass() throws InterruptedException {
        System.out.println("无参构造方法产生了");
        System.out.println(now());
        sleep(1000);
    }
    public void sing(String sing){
        System.out.println(sing+"被唱了");
    }
    public void dance(String dance){
        System.out.println(dance+"被跳了");
    }
}
