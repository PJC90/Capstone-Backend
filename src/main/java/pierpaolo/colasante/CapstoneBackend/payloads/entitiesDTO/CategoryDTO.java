package pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CategoryDTO(
        @NotEmpty(message = "La categoria è obbligatoria")
        @Size(min = 3, max = 30, message = "La categoria deve avere minimo 3 caratteri, massimo 30")
        String nameCategory
) {
}
