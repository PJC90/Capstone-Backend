package pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO;

import jakarta.validation.constraints.*;
import pierpaolo.colasante.CapstoneBackend.entities.enums.ProductType;

import java.util.UUID;

public record ProductDTO(
        @NotEmpty(message = "Il titolo è obbligatorio")
        @Size(min = 3, max = 100, message = "Il titolo deve avere minimo 3 caratteri, massimo 100")
        String title,
        @NotEmpty(message = "La descrizione è obbligatoria")
        @Size(min = 10, max = 500, message = "La descrizione deve avere minimo 10 caratteri, massimo 500")
        String description,
        @NotNull(message = "Il prezzo del prodotto è un campo obbligatorio!")
        double price,
        @NotNull(message = "La quantità del prodotto è un campo obbligatorio!")
        int quantity,
        @NotNull(message = "l'id del negozio è un campo obbligatorio!")
        int shop_id,
        @NotNull(message = "l'id della categoria è un campo obbligatorio!")
        int category_id,
        @NotNull(message = "Il tipo di prodotto è un campo obbligatorio!")
        ProductType productType
) {
}
