package pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ReviewDTO(
        @Min(1)
        @Max(5)
        int rating,
        @NotEmpty(message = "La descrizione nella recensione è obbligatoria")
        @Size(min = 10, max = 300, message = "La recensione deve avere minimo 10 caratteri, massimo 300")
        String description
) {
}
