import org.junit.Test;

import static com.google.common.truth.Truth.assertWithMessage;

public class CodingChallengesTest {

    @Test
    public void testMissingNumber() {
        // DONE
        int[] values = {0, 1, 2, 4, 5}; // Missing number is 3
        assertWithMessage("Missing number should be 3").that(CodingChallenges.missingNumber(values)).isEqualTo(3);

        values = new int[]{1, 2, 3, 4, 5}; // Missing number is 0
        assertWithMessage("Missing number should be 0").that(CodingChallenges.missingNumber(values)).isEqualTo(0);

        values = new int[]{0}; // Missing number is 1
        assertWithMessage("Missing number should be 1").that(CodingChallenges.missingNumber(values)).isEqualTo(1);
    }

    @Test
    public void testIsPermutation() {
        // TODO
        String s1 = "abcde";
        String s2 = "edcba"; // s2 is a permutation of s1
        assertWithMessage("s2 should be a permutation of s1").that(CodingChallenges.isPermutation(s1, s2)).isTrue();

        s1 = "123salda89Ahhkjasd";
        s2 = "3218Asaldahhkjasd9"; // s2 is a permutation of s1
        assertWithMessage("s2 should be a permutation of s1").that(CodingChallenges.isPermutation(s1, s2)).isTrue();

        s1 = "aabbcc";
        s2 = "abcabc"; // s2 is a permutation of s1
        assertWithMessage("s2 should be a permutation of s1").that(CodingChallenges.isPermutation(s1, s2)).isTrue();

        s1 = "abc";
        s2 = ""; // s2 is not a permutation of s1 (different lengths)
        assertWithMessage("s2 should not be a permutation of s1").that(CodingChallenges.isPermutation(s1, s2)).isFalse();

        s1 = "abc";
        s2 = "adc"; // s2 is not a permutation of s1 (different characters)
        assertWithMessage("s2 should not be a permutation of s1").that(CodingChallenges.isPermutation(s1, s2)).isFalse();

        s1 = "abc";
        s2 = "abcc"; // s2 is not a permutation of s1 (different character counts)
        assertWithMessage("s2 should not be a permutation of s1").that(CodingChallenges.isPermutation(s1, s2)).isFalse();
    }
}
