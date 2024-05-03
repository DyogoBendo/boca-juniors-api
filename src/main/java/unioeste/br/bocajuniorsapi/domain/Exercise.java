package unioeste.br.bocajuniorsapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @ToString
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    @Lob
    private String description;
    private String tag;
    private String difficulty;
    @Lob
    private String sourceCode;
}
