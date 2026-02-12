package elisaraeli.U5_W2_D3.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NewBlogPostPayload {

    @NotBlank(message = "La categoria è obbligatoria")
    @Size(min = 2, max = 30, message = "La categoria deve essere compresa tra i 2 e i 30 caratteri")
    private String categoria;

    @NotBlank(message = "Il titolo è un campo obbligatorio")
    @Size(min = 2, max = 30, message = "Il titolo deve essere compreso tra i 2 e i 30 caratteri")
    private String titolo;

    @NotBlank(message = "Il contenuto è un campo obbligatorio")
    @Size(min = 2, max = 30, message = "Il contenuto deve essere compreso tra i 2 e i 30 caratteri")
    private String contenuto;

    @NotNull(message = "Il tempo di lettura è un campo obbligatorio")
    @Min(value = 1, message = "Il tempo di lettura deve essere minimo 1 minuto")
    @Max(value = 30, message = "Il tempo di lettura può essere massimo 30 minuti")
    private int tempoDiLettura;

    @NotNull(message = "L'ID dell'autore è un campo obbligatorio")
    private UUID autoreId;

}