package hakaton.beta.service.field;

public class Cell {
    private Owner owner;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    public Cell() {
        this.owner = Owner.NEUTRAL;
    }
}


