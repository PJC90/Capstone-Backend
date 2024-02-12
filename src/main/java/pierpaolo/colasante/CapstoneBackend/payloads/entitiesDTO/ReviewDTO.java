package pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record ReviewDTO(
        @Min(1)
        @Max(5)
        int rating,
        @NotEmpty(message = "La descrizione nella recensione è obbligatoria")
        @Size(min = 10, max = 300, message = "La recensione deve avere minimo 10 caratteri, massimo 300")
        String description,
        @NotNull(message = "l'id dello shop è un campo obbligatorio!")
        int shop_id,
        @NotNull(message = "l'id del prodotto è un campo obbligatorio!")
        UUID product_id,
        @NotNull(message = "l'id dell' ordine è un campo obbligatorio!")
        UUID order_id
) {
}
