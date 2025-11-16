import org.omg.CORBA.PUBLIC_MEMBER;

public class BigStar implements StarCommen{
    private String name;
    public void sing(String sing){
        System.out.println(sing+"被唱了"+this.name);
    }
    public void dance(String dance){
        System.out.println(dance+"被跳了"+this.name);
    }
public BigStar(){
        this.name="疯子";
}
    public BigStar(String name) {
        this.name = name;
    }
}
