package elisaraeli.U5_W2_D3.payloads;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NewAuthorPayload {
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataDiNascita;
}