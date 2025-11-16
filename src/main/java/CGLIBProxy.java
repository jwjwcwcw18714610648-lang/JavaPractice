import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLIBProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(method.getName().equals("sing")){
            System.out.println("准备唱歌");
        }
        if("dance".equals(method.getName())){
            System.out.println("准备舞台");
        }
        return methodProxy.invokeSuper(o,objects);
    }
}
