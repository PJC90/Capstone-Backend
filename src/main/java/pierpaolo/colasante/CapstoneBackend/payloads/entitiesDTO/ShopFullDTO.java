package pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ShopFullDTO(
        @NotEmpty(message = "Il titolo del negozio è obbligatorio")
        @Size(min = 3, max = 30, message = "Il titolo del negozio deve avere minimo 3 caratteri, massimo 30")
        String shopName,
        @NotEmpty(message = "La descrizione del negozio è obbligatorio")
        @Size(min = 10, max = 100, message = "La descrizione del negozio deve avere minimo 10 caratteri, massimo 100")
        String description,
        @NotEmpty(message = "La nazione del negozio è obbligatorio")
        @Size(min = 3, max = 15, message = "La nazione del negozio deve avere minimo 3 caratteri, massimo 15")
        String nation,
        @NotEmpty(message = "La località del negozio è obbligatorio")
        @Size(min = 3, max = 30, message = "La località del negozio deve avere minimo 3 caratteri, massimo 30")
        String locality
) {
}
