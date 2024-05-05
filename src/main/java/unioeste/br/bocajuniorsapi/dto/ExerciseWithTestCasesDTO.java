package unioeste.br.bocajuniorsapi.dto;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unioeste.br.bocajuniorsapi.domain.TestCase;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ExerciseWithTestCasesDTO {
    private String title;
    private String description;
    private String tag;
    private String difficulty;
    private String sourceCode;
    private List<TestCaseDTO> testCases;
}
