package pierpaolo.colasante.CapstoneBackend.payloads.errorDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorDTOwithList(
        String message,
        LocalDateTime time,
        List<String> errorsList
) {
}
