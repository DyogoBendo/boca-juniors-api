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
    private Double timeLimit;
    private Integer memoryLimit;
    private Integer sizeLimit;
    private String language;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;
}
