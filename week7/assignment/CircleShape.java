public class CircleShape extends Shape {
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
