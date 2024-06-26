package unioeste.br.bocajuniorsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unioeste.br.bocajuniorsapi.domain.SubmissionStatus;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class
SubmissionFilterDTO {
    private Long exerciseId;
    private String username;
    private SubmissionStatus status;
}
