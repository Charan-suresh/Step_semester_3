public class SquareShape extends Shape {
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
