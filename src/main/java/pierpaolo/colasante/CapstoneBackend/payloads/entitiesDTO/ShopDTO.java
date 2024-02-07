package pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record ShopDTO(
         @NotEmpty(message = "Il titolo del negozio è obbligatorio")
         @Size(min = 3, max = 30, message = "Il titolo del negozio deve avere minimo 3 caratteri, massimo 30")
         String shopName
) {
}
