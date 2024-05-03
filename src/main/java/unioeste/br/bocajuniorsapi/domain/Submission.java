package unioeste.br.bocajuniorsapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity @ToString
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Exercise exercise;
    private String username;

    @Lob
    private String sourceCode;
    private Boolean accepted;
}
