package Shape;

/**
 * Created by Brad on 9/27/2016.
 */
public class Circle implements Shape {
    // Instance Date
    private double radius;

    // Constructor
    public Circle(double radius) {
        this.radius = radius;
    }

    // Getters and Setters
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double area() {
        return Math.PI * radius * radius;
    }
}
