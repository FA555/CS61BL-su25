import org.junit.Test;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;

public class DistributionSortsTest {

    @Test
    public void testCountingSort() {
        int[] arr = {2, 5, 3, 2, 9, 1, 4, 3, 0, 8, 7, 6};
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);

        DistributionSorts.countingSort(arr);

        assertThat(expected).isEqualTo(Arrays.copyOf(arr, arr.length));
    }

    @Test
    public void testCountingSort_AllSame() {
        int[] arr = {5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5};
        DistributionSorts.countingSort(arr);
        assertThat(expected).isEqualTo(Arrays.copyOf(arr, arr.length));
    }

    @Test
    public void testCountingSort_Empty() {
        int[] arr = {};
        int[] expected = {};
        DistributionSorts.countingSort(arr);
        assertThat(arr).isEmpty();
    }

    @Test
    public void testCountingSort_SingleElement() {
        int[] arr = {7};
        int[] expected = {7};
        DistributionSorts.countingSort(arr);
        assertThat(expected).isEqualTo(Arrays.copyOf(arr, arr.length));
    }

    @Test
    public void testLSDRadixSort_Basic() {
        int[] arr = {543, 21, 1, 4, 3, 15, 200, 54321};
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);

        DistributionSorts.lsdRadixSort(arr);

        assertThat(expected).isEqualTo(Arrays.copyOf(arr, arr.length));
    }

    @Test
    public void testLSDRadixSort_Empty() {
        int[] arr = {};
        DistributionSorts.lsdRadixSort(arr);
        assertThat(arr).isEmpty();
    }

    @Test
    public void testLSDRadixSort_SingleElement() {
        int[] arr = {42};
        DistributionSorts.lsdRadixSort(arr);
        assertThat(arr).asList().containsExactly(42);
    }

    @Test
    public void testLSDRadixSort_SameDigits() {
        int[] arr = {111, 111, 111};
        DistributionSorts.lsdRadixSort(arr);
        assertThat(arr).asList().containsExactly(111, 111, 111);
    }
}
