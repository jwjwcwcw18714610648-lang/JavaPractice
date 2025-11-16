import net.sf.cglib.proxy.Enhancer;

public class CGLIBFactory {
    public Object getProxyCGLIB(Class<?> clazz ){
        Enhancer enhancer=new Enhancer();
        enhancer.setClassLoader(clazz.getClassLoader());
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new CGLIBProxy());
        return enhancer.create();
    }
}
