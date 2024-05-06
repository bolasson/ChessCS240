public class BlankSpace {
    private int instanceVar;
    public static void main(String [] args) {
        BlankSpace instance = new BlankSpace();
        instance.instanceVar = 15;
        instance.InstanceMethod();
    }
    
    public void InstanceMethod() {
        System.out.println("Hello BYU");
        System.out.println(instanceVar);
    }
}