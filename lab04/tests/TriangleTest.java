import org.junit.Test;

import static com.google.common.truth.Truth.assertWithMessage;

public abstract class TriangleTest {

    /**
     * For autograding purposes; do not change this line.
     */
    abstract Triangle getNewTriangle();

    /* ***** TESTS ***** */

    @Test
    public void testSidesFormTriangle() {
        Triangle t = getNewTriangle();
        assertWithMessage("3, 4, 5 should form a triangle")
                .that(t.sidesFormTriangle(3, 4, 5))
                .isTrue();
        assertWithMessage("5, 5, 5 should form a triangle")
                .that(t.sidesFormTriangle(5, 5, 5))
                .isTrue();

        assertWithMessage("1, 2, 3 should NOT form a triangle")
                .that(t.sidesFormTriangle(1, 2, 3))
                .isFalse();
        assertWithMessage("0, 0, 0 should NOT form a triangle")
                .that(t.sidesFormTriangle(0, 0, 0))
                .isFalse();
        assertWithMessage("-1, 4, 5 should NOT form a triangle")
                .that(t.sidesFormTriangle(-1, 4, 5))
                .isFalse();
    }

    @Test
    public void testPointsFormTriangle() {
        Triangle t = getNewTriangle();
        assertWithMessage("Points (0,0), (1,0), (0,1) should form triangle")
                .that(t.pointsFormTriangle(0, 0, 1, 0, 0, 1))
                .isTrue();

        assertWithMessage("Colinear points (0,0), (1,1), (2,2) should NOT form triangle")
                .that(t.pointsFormTriangle(0, 0, 1, 1, 2, 2))
                .isFalse();
    }

    @Test
    public void testTriangleType() {
        Triangle t = getNewTriangle();
        assertWithMessage("5, 5, 5 should be Equilateral")
                .that(t.triangleType(5, 5, 5))
                .isEqualTo("Equilateral");

        assertWithMessage("5, 5, 3 should be Isosceles")
                .that(t.triangleType(5, 5, 3))
                .isEqualTo("Isosceles");

        assertWithMessage("3, 4, 5 should be Scalene")
                .that(t.triangleType(3, 4, 5))
                .isEqualTo("Scalene");
    }

    @Test
    public void testSquaredHypotenuseBasic() {
        Triangle t = getNewTriangle();
        assertWithMessage("squaredHypotenuse(3,4) should be 25")
                .that(t.squaredHypotenuse(3, 4))
                .isEqualTo(25);
        assertWithMessage("squaredHypotenuse(5,12) should be 169")
                .that(t.squaredHypotenuse(5, 12))
                .isEqualTo(169);

        assertWithMessage("squaredHypotenuse(0,0) should be 0")
                .that(t.squaredHypotenuse(0, 0))
                .isEqualTo(0);

        assertWithMessage("squaredHypotenuse(-3,-4) should still be 25")
                .that(t.squaredHypotenuse(-3, -4))
                .isEqualTo(25);
    }
}
