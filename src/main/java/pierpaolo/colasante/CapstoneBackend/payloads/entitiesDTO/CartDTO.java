package pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CartDTO(
        @NotNull(message = "l'id dell'user è un campo obbligatorio!")
        UUID user_id
) {
}
