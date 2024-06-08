package hakaton.beta.web.dto;

import hakaton.beta.service.field.Coordinates;

public record ResponseDto (
        String answer,
        Coordinates coordinates
) {}
