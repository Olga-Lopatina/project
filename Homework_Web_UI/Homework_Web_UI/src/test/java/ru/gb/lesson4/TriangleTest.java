package ru.gb.lesson4;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ru.gb.lesson4.Triangle.triangleCalc;

public class TriangleTest {
    @Test
    void TriangleSquareTest() throws Exception {
Assertions.assertEquals(3, triangleCalc(1,2,3));
    }
}
