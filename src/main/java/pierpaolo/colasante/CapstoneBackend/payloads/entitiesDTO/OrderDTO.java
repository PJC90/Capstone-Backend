package pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderDTO(
        @NotNull(message = "l'id dell' user è un campo obbligatorio!")
        UUID user_id,
        @NotNull(message = "l'id del carrello è un campo obbligatorio!")
        UUID cart_id
) {
}
