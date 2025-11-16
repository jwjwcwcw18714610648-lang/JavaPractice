import sun.net.util.ProxyUtil;

public class test {
    public static void main(String[] args) {


    BigStar bigStar=new BigStar("cxk");
    StarCommen starCommen= new JDkProxyFactory().CreateProxy(bigStar);
    starCommen.sing("东风破");
    starCommen.dance("天鹅湖");
    BigStar bigStar2= (BigStar) new CGLIBFactory().getProxyCGLIB(BigStar.class);
    bigStar2.sing("逆战");
    bigStar2.dance("舞动青春");
    }
}
