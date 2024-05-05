package unioeste.br.bocajuniorsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import unioeste.br.bocajuniorsapi.domain.SubmissionStatus;
import unioeste.br.bocajuniorsapi.helper.SubmissionStatusDeserializer;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @ToString
public class SubmissionResultDTO {
    private String status;
    @JsonProperty("verdict")
    @JsonDeserialize(using = SubmissionStatusDeserializer.class)
    private SubmissionStatus verdict;
    private String stderr;
    private Long id;
}
