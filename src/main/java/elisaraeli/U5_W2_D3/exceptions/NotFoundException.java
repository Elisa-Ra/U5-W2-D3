package elisaraeli.U5_W2_D3.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Ops! La risorsa con id " + id + " non è stata trovata!");
    }
}
