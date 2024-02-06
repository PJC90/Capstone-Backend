package pierpaolo.colasante.CapstoneBackend.payloads.errorDTO;

import java.time.LocalDateTime;

public record ErrorDTO(String message, LocalDateTime time) {
}
