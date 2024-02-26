package pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pierpaolo.colasante.CapstoneBackend.entities.enums.PaymentType;

public record PaymentDTO(
        @NotEmpty(message = "Il Codice del Pagamento è obbligatorio")
        String transactionCode,
        @NotNull(message = "Il tipo di pagamento è un campo obbligatorio (STRIPE o PAYPAL)")
        PaymentType paymentType
) {
}
