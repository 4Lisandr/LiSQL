/**
 *
 */
public class Test {

    public static void main(String[] args) {
        Integer a = new Integer(2);
        Integer b = new Integer(2);

        System.out.println(a.intValue() == b.intValue());
        System.out.println(a.compareTo(b));
        System.out.println(a.equals(b));
        System.out.println(a == b);
    }
}
