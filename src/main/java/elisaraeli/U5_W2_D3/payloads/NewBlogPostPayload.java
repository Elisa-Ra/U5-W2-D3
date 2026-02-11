package elisaraeli.U5_W2_D3.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewBlogPostPayload {
    private String categoria;
    private String titolo;
    private String contenuto;
    private int tempoDiLettura;
}