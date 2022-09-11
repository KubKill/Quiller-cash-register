import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import model.usefull.Range;

import java.util.stream.Stream;

public class RangeTest {

    private Range range;

    @BeforeEach
    public void setup() {
        range = new Range(1, 30);
    }

    private static Stream<Integer[]> dataBelowRange() {
        return Stream.of(
                new Integer[]{0, 0},
                new Integer[]{5, 5},
                new Integer[]{10,10},
                new Integer[]{23, 23},
                new Integer[]{30, 30}
        );
    }

    private static Stream<Integer[]> dataLowerBiggerThenUpper() {
        return Stream.of(
                new Integer[]{1, 0},
                new Integer[]{8, 5},
                new Integer[]{11,10},
                new Integer[]{30, 23},
                new Integer[]{40, 30}
        );
    }

    @ParameterizedTest
    @MethodSource("dataBelowRange")
    public void rangeConstructor_lowerBoundSameValueAsUpperBound_throwsIllegalArgumentException(int lowerBound, int upperBound) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Range(lowerBound, upperBound));
    }

    @ParameterizedTest
    @MethodSource("dataLowerBiggerThenUpper")
    public void rangeConstructor_lowerBoundBiggerThenUpperBound_throwsIllegalArgumentException(int lowerBound, int upperBound) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Range(lowerBound, upperBound));
    }


    @ParameterizedTest
    @ValueSource(ints = {0, -5, -100, -5000, -1234567, Integer.MIN_VALUE})
    public void rangeCheckIfInRange_numberBelowRangeLowerBound_returnsFalse(int number) {

        Assertions.assertFalse(range.checkIfInRange(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 15, 27, 30})
    public void rangeCheckIfInRange_numberInPermittedRange_returnsTrue(int number) {

        Assertions.assertTrue(range.checkIfInRange(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {31, 40, 500, 1321, Integer.MAX_VALUE})
    public void rangeCheckIfInRange_numberAbovePermittedRange_returnsFalse(int number) {

        Assertions.assertFalse(range.checkIfInRange(number));
    }

}
