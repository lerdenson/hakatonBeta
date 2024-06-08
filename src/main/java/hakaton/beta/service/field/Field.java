package hakaton.beta.service.field;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Field {

    private Map<Coordinates, Cell> cells;

    private Field() {
        this.cells = getNewField();
    }


    public void changeOwnerOfCell(Coordinates coordinates, Owner owner) {
        Cell cell = cells.get(coordinates);
        cell.setOwner(owner);
    }

    public Owner getOwnerOfCell(Coordinates coordinates) {
        return cells.get(coordinates).getOwner();
    }

    public long countCellsUnderOwnerControl(Owner owner) {
        return cells
                .entrySet()
                .stream()
                .filter(e -> e.getValue().getOwner().equals(owner))
                .count();
    }


    public void clearField() {
        this.cells = getNewField();
    }

    private static HashMap<Coordinates, Cell> getNewField() {
        HashMap<Coordinates, Cell> cells = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cells.put(new Coordinates(i, j), new Cell());
            }
        }
        return cells;
    }

    //check if cell located in (x+xBias; y+yBias) is under control by specific owner while cell (x, y) is not
    private boolean checkIsNeighbourCellControlledBySpecificOwner(Coordinates coordinates, Owner owner, int xBias, int yBias) {
        return !(this.getOwnerOfCell(coordinates).equals(owner)) &&
                this.getOwnerOfCell(new Coordinates(coordinates.x() + xBias, coordinates.y() + yBias))
                        .equals(owner);
    }

    public boolean checkCell(Coordinates coordinates, Owner owner) {
        if (coordinates.x() == 0 && coordinates.y() == 0) {
            return checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 1, 0);

        }
        if (coordinates.x() == 0 && coordinates.y() < 3) {
            return checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 1, 0) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, -1);
        }

        if (coordinates.x() == 0 && coordinates.y() == 3) {
            return checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 1, 0) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, -1);
        }

        if (coordinates.x() < 3 && coordinates.y() == 0) {
            return checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 1, 0) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, -1, 0);
        }

        if (coordinates.x() == 3 && coordinates.y() == 0) {
            return checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, -1, 0);
        }
        if (coordinates.x() < 3 && coordinates.y() < 3) {
            return checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 1, 0) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, -1) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, -1, 0);
        }
        if (coordinates.x() == 3 && coordinates.y() < 3) {
            return checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, -1) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, -1, 0);
        }
        if (coordinates.x() < 3 && coordinates.y() == 3) {
            return checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 1, 0) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, -1) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, -1, 0);
        }
        if (coordinates.x() == 3 && coordinates.y() == 3) {
            return checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, 0, -1) ||
                    checkIsNeighbourCellControlledBySpecificOwner(coordinates, owner, -1, 0);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Coordinates c : cells.keySet()) {
            str.append("x: ").append(c.x())
                    .append(", y: ").append(c.y())
                    .append(", owner: ").append(cells.get(c).getOwner().toString()).append('\n');
        }
        return str.toString();
    }
}
