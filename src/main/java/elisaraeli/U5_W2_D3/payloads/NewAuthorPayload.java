package elisaraeli.U5_W2_D3.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NewAuthorPayload {
    @NotBlank(message = "Il nome è un campo obbligatorio")
    @Size(min = 2, max = 30, message = "Il nome deve essere compreso tra i 2 e i 30 caratteri")
    private String nome;

    @NotBlank(message = "Il cognome è un campo obbligatorio")
    @Size(min = 2, max = 30, message = "Il cognome deve essere compreso tra i 2 e i 30 caratteri")
    private String cognome;

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "L'indirizzo email inserito non è nel formato corretto!")
    private String email;

    // NotBlank è per le String, quindi per la data di nascita uso NotNull
    // Inoltre, controllo che il formato sia quello accettato da JSON
    @NotNull(message = "La data di nascita è obbligatoria e deve essere nel formato yyyy-mm-dd")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate dataDiNascita;
}
