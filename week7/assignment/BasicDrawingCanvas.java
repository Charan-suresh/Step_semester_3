abstract class Shape {
    private static int counter = 1000;
    private final String shapeId;
    protected double scaleX = 1.0;
    protected double scaleY = 1.0;

    public Shape() {
        counter++;
        this.shapeId = "SHAPE-" + counter;
    }

    public String getShapeId() {
        return shapeId;
    }

    public abstract double calculateArea();

    public void scale(double factor) {
        this.scaleX *= factor;
        this.scaleY *= factor;
    }

    public void scale(double xFactor, double yFactor) {
        this.scaleX *= xFactor;
        this.scaleY *= yFactor;
    }

    public static void printArea(Shape s) {
        if (s != null) {
            System.out.println(s.calculateArea());
        }
    }
}

class CircleShape extends Shape {
    private final double radius;

    public CircleShape(double radius) {
        super();
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * (radius * scaleX) * (radius * scaleY);
    }
}

class SquareShape extends Shape {
    private final double side;

    public SquareShape(double side) {
        super();
        if (side <= 0) {
            throw new IllegalArgumentException("Side must be positive");
        }
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    @Override
    public double calculateArea() {
        return (side * scaleX) * (side * scaleY);
    }
}

public class BasicDrawingCanvas {
    public static void printArea(Shape s) {
        Shape.printArea(s);
    }

    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        System.out.printf("Circle area: %.2f%n", c.calculateArea());

        SquareShape sq = new SquareShape(4.0);
        System.out.println("Square initial area: " + sq.calculateArea());

        // uses the one-argument overload
        sq.scale(2.0);
        System.out.println("Square scaled area: " + sq.calculateArea());

        printArea(c);
    }
}
