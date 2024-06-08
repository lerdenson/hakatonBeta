package hakaton.beta.service;


import hakaton.beta.service.field.Coordinates;
import hakaton.beta.service.field.Field;
import hakaton.beta.service.field.Owner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Bot {
    private final double probability;

    private final Field field;

    public Bot(Field field) {
        this.probability = 0.6d;
        this.field = field;
    }

    // choose cell to attack and return coordinates of attacked cell or null if attack wasn't successful
    public Optional<Coordinates> chooseAndAttackCell() {
        List<Coordinates> coordinatesList = getCellsBotCanAttack();
        System.out.println(coordinatesList);
        Coordinates cellToAttack = coordinatesList
                .get(ThreadLocalRandom.current().nextInt(coordinatesList.size()));

        if (ThreadLocalRandom.current().nextDouble() < probability) {
            field.changeOwnerOfCell(cellToAttack, Owner.BUG);
            return Optional.of(cellToAttack);
        } else return Optional.empty();
    }


    private List<Coordinates> getCellsBotCanAttack() {
        List<Coordinates> cellsToAttack = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Coordinates c = new Coordinates(i, j);
                if (field.checkCell(c, Owner.BUG)) {
                    cellsToAttack.add(c);
                }
            }
        }
        return cellsToAttack;
    }
}
