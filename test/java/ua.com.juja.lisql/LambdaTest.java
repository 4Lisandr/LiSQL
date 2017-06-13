import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 */
public class LambdaTest {


    public static int sumAll(List<Integer> numbers, Predicate<Integer> p) {
        int total = 0;
        for (int number : numbers) {
            if (p.test(number)) {
                total += number;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(
                String.format("1 - %d,%n2 - %d,%n3 - %d.",
        sumAll(numbers, n -> true),
        sumAll(numbers, n -> n % 2 == 0),
        sumAll(numbers, n -> n > 3)));
    }
}
