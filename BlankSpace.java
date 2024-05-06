import java.util.Date;
import java.util.Objects;
public class BlankSpace {
    private int instanceVar;
    private Date pastDate = new Date(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000);
    private Date futureDate = new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000);

    public BlankSpace() {
        this.instanceVar = 5;
        // super();
    }

    public static void main(String [] args) {
        BlankSpace instance1 = new BlankSpace();
        instance1.instanceVar = 15;
        instance1.futureDate = new Date(System.currentTimeMillis() + 15 * 24 * 60 * 60 * 1000);
        instance1.InstanceMethod();
    }
    
    public void InstanceMethod() {
        // BlankSpace.printS(this.toString());
        // BlankSpace instance2 = new BlankSpace();
        // BlankSpace.printS(instance2.toString());
        createInstancesWithSameHashCode();
    }

    public static void createInstancesWithSameHashCode() {
        BlankSpace instance1 = new BlankSpace();
        BlankSpace instance2 = new BlankSpace();
        
        // Set different values for instanceVar, pastDate, and futureDate
        instance1.instanceVar = 10;
        instance1.pastDate = new Date(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000);
        instance1.futureDate = new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000);
        
        instance2.instanceVar = 20;
        instance2.pastDate = new Date(System.currentTimeMillis() - 4 * 24 * 60 * 60 * 1000);
        instance2.futureDate = new Date(System.currentTimeMillis() + 4 * 24 * 60 * 60 * 1000);
        
        // Print the hashCodes of the instances
        System.out.println("Instance 1 hashCode: " + instance1.hashCode());
        System.out.println("Instance 2 hashCode: " + instance2.hashCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(instanceVar, pastDate, futureDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof BlankSpace)) {
            return false;
        }
        BlankSpace other = (BlankSpace) obj;
        return instanceVar == other.instanceVar &&
                Objects.equals(pastDate, other.pastDate) &&
                Objects.equals(futureDate, other.futureDate);
    }
    @Override
    public String toString() {
        return "BlankSpace{" +
                "instanceVar=" + instanceVar +
                ", pastDate=" + pastDate +
                ", futureDate=" + futureDate +
                ", hashCode=" + hashCode() +
                '}';
    }

    public int getInstanceVar() {
        return this.instanceVar;
    }

    public static void printS(String value) {
        System.out.println(value);
    }

    public int daysBetweenDate(){
        long daysBetween = futureDate.getTime() - pastDate.getTime();
        long millisecondsPerDay = 24 * 60 * 60 * 1000;
        int days = (int) (daysBetween / millisecondsPerDay);
        return days;
    }
}