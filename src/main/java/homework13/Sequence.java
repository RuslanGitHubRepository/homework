package homework13;

import java.util.Arrays;

public class Sequence {
    public static int[] filter(int[] array, ByCondition condition) {
        return Arrays.stream(array)
                .filter(condition::isOk)
                .toArray();
    }
}
