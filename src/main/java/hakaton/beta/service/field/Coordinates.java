package hakaton.beta.service.field;

public record Coordinates(int x, int y) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinates c)) {
            return false;
        }

        return c.x() == this.x() && c.y() == this.y();
    }

    @Override
    public int hashCode() {
        return 10*x + y;
    }
}
