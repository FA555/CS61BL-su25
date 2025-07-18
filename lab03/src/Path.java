/** A class that represents a path via pursuit curves. */
public class Path {

    Point curr;
    Point next;

    public Path(double x, double y) {
        this.curr = new Point(x, y);
        this.next = new Point(x, y);
    }

    double getCurrX() {
        return curr.getX();
    }

    double getCurrY() {
        return curr.getY();
    }

    double getNextX() {
        return next.getX();
    }

    double getNextY() {
        return next.getY();
    }

    Point getCurrentPoint() {
        return new Point(curr);
    }

    void setCurrentPoint(Point p) {
        curr = new Point(p);
    }

    void iterate(double dx, double dy) {
        setCurrentPoint(next);
        next = new Point(next.getX() + dx, next.getY() + dy);
    }

}
