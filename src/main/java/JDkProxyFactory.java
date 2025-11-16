import sun.net.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
//使用JDK 动态代理

public class JDkProxyFactory {
    public StarCommen CreateProxy(BigStar bigStar){
    StarCommen starCommen= (StarCommen) Proxy.newProxyInstance(
            JDkProxyFactory.class.getClassLoader(),
            new Class[]{StarCommen.class},
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if(method.getName().equals("sing")){
                        System.out.println("准备唱歌");
                    }
                    if("dance".equals(method.getName())){
                        System.out.println("准备舞台");
                    }
                    return method.invoke(bigStar,args);
                }
            }

    );
    return starCommen;}
}
