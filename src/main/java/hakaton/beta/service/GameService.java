package hakaton.beta.service;

import hakaton.beta.service.field.Coordinates;
import hakaton.beta.service.field.Field;
import hakaton.beta.service.field.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class GameService {
    private static GameService game;

    private final Bot bot;

    private final Field field;

    public GameService(Bot bot, Field field) {
        this.bot = bot;
        this.field = field;
        restartGame();
    }

    public Question getRandomQuestion(){
        return new Question(1, "1", "2", "3", "4", "5", "6", "4");
    }
   public String getCorrectAnswer(int questionId) {
        return "4";
   }

    public String checkAnswerAndAttackIfItRight(int questionId, String answer, Coordinates coordinates) {
        String correctAnswer = getCorrectAnswer(questionId);
        if (correctAnswer.equals(answer) && field.checkCell(coordinates, Owner.DEFENDER)) {
            field.changeOwnerOfCell(coordinates, Owner.DEFENDER);

        }
        return correctAnswer;
    }

    public Optional<Coordinates> attackByBug() {
        return bot.chooseAndAttackCell();
    }

    //return bug if bug wins, defender if player wins and neutral if game continues
    public Owner checkIsGameEnds() {
        if (field.countCellsUnderOwnerControl(Owner.DEFENDER) == 0) return Owner.BUG;
        if (field.countCellsUnderOwnerControl(Owner.BUG) == 0) return Owner.DEFENDER;
        return Owner.NEUTRAL;
    }

    public void restartGame() {
        initializeField();
    }

    private void initializeField() {
        field.clearField();
        field.changeOwnerOfCell(new Coordinates(0, 3), Owner.DEFENDER);
        field.changeOwnerOfCell(new Coordinates(3, 0), Owner.BUG);
    }

    public Field getField() {
        return field;
    }
}
