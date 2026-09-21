public abstract class Shape {
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
