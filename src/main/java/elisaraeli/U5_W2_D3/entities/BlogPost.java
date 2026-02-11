package elisaraeli.U5_W2_D3.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BlogPost {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String categoria;
    private String titolo;
    private String cover; // lo genera il server
    private String contenuto;
    private int tempoDiLettura; // in minuti

    public BlogPost(String categoria, String titolo, String contenuto, int tempoDiLettura) {

        this.categoria = categoria;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tempoDiLettura = tempoDiLettura;
    }
}
