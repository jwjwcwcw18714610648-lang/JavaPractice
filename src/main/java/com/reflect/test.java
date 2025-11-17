package com.reflect;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class test {
    @Test
    public void ReflectTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        // 1. 获取 Class 对象（反射对象）的四种方式
        Class reflectClass1= BeRefletedClass.class;
        Class reflectClass2=Class.forName("com.reflect.BeRefletedClass");
        BeRefletedClass beRefletedClass=new BeRefletedClass();
        Class reflectClass3=beRefletedClass.getClass();
        Class reflectClass4= ClassLoader.getSystemClassLoader().loadClass("com.reflect.BeRefletedClass");//创建了四个反射对象

        // 2. 使用 Class 对象创建实例对象
        BeRefletedClass berfletedClass1= (BeRefletedClass) reflectClass2.newInstance();//创建了四个实例对象
        BeRefletedClass berfletedClass2= (BeRefletedClass) reflectClass2.newInstance();
        BeRefletedClass berfletedClass3= (BeRefletedClass) reflectClass3.newInstance();
        BeRefletedClass berfletedClass4= (BeRefletedClass) reflectClass4.newInstance();

        // 构造方法调用输出 (sleep时间戳)
        //输出无参构造方法产生了
        //无参构造方法产生了
        //无参构造方法产生了
        //无参构造方法产生了
        //无参构造方法产生了

        // 3. 获取所有公共方法（包括继承自 Object 的方法）
        Method[] methods=reflectClass1.getMethods();
        for(Method method:methods){
            System.out.println(method.getName());
        }
        //输出dance
        //sing
        //wait
        //wait
        //wait
        //equals
        //toString
        //hashCode
        //getClass
        //notify
        //notifyAll

        // 4. 反射调用方法 (Method.invoke())

        // Method 1: 从 reflectClass2 获取，在 berfletedClass1 上调用
        Method method1=reflectClass2.getDeclaredMethod("sing", String.class);
        method1.invoke(berfletedClass1,"东风破");
        //东风破被唱了

        // Method 3: 从 reflectClass3 获取，在 berfletedClass3 和 berfletedClass1 上调用
        Method method3=reflectClass3.getDeclaredMethod("sing", String.class);
        method3.invoke(berfletedClass3,"奔雷");
        method3.invoke(berfletedClass1,"下山");
        // 笔记：你的代码没有报错，因为 reflectClass2 和 reflectClass3 实际上指向的是同一个类的定义
        // （BeRefletedClass），而 method3 是这个类定义中的一个方法。只要你调用 invoke()
        // 时传入的对象是这个类或其子类的实例，就不会报错。

        // Method 4: 调用 dance 方法
        Method method4=reflectClass1.getDeclaredMethod("dance",String.class);
        method4.invoke(berfletedClass1,"天鹅湖");

        // 5. 反射访问和修改字段 (Field.set())

        // 注意：这里使用 getDeclaredField() 来获取私有字段，解决了之前 getField() 的问题。
        Field field1=reflectClass1.getDeclaredField("value1");//getDeclaredField获取私有字段
        Field field2=reflectClass1.getDeclaredField("value2");

        // 取消安全检查，使其能够访问私有/非公有字段
        field2.setAccessible(true);
        field1.setAccessible(true);//取消安全检查

        // 赋值：注意 field1 是 int 类型，必须传入 int/Integer 对象
        field1.set(berfletedClass1,2);
        field2.set(berfletedClass1,"字符串");//借助反射去给被反射对象赋值

        // 6. 打印结果
        System.out.println(berfletedClass1.getValue1()+berfletedClass1.getValue2());//输出"2字符串"
    }
}