package ru.gb.lesson4;




public class Triangle {
    public static double triangleCalc(double a, double b, double c) throws Exception {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new Exception("Отрицательное число для стороны");

        }
        double halfPerimeter = (a + b + c) / 2;
        return Math.sqrt(halfPerimeter + (halfPerimeter - a) + (halfPerimeter - b) + (halfPerimeter - c));
    }



}
