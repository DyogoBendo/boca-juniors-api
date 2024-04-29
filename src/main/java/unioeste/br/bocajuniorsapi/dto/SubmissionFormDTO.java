package unioeste.br.bocajuniorsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unioeste.br.bocajuniorsapi.domain.Exercise;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class SubmissionFormDTO {
    private Long exerciseId;
    private String username;
    private String sourceCode;
}
