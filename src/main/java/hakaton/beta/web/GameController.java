package hakaton.beta.web;


import hakaton.beta.service.GameService;
import hakaton.beta.service.Question;
import hakaton.beta.service.field.Coordinates;
import hakaton.beta.web.dto.RequestDto;
import hakaton.beta.web.dto.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/api/start")
    void start() {
        gameService.restartGame();
    }

    @GetMapping("/bugBusters")
    Question getRandomQuestion() {
        return gameService.getRandomQuestion();
    }

    @PostMapping("/bugBusters")
    ResponseDto getAnswer(@RequestBody RequestDto requestDto) {
        String correctAnswer = gameService
                .checkAnswerAndAttackIfItRight(
                        requestDto.questionId(),
                        requestDto.answer(),
                        new Coordinates(requestDto.x(), requestDto.y()));

        Optional<Coordinates> coordinatesOptional = gameService.attackByBug();
        if (coordinatesOptional.isPresent()) {
            return new ResponseDto(correctAnswer, coordinatesOptional.get());
        }
        return new ResponseDto(correctAnswer, new Coordinates(-1, -1));

    }

}
