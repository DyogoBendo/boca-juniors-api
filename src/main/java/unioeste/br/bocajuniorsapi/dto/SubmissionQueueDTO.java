package unioeste.br.bocajuniorsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class SubmissionQueueDTO {
    private Long id;
    private String sourceCode;
    private Double timeLimit;
    private Integer memoryLimit;
    private Integer sizeLimit;
    private String language;
    private List<TestCaseDTO> testCases;
}
