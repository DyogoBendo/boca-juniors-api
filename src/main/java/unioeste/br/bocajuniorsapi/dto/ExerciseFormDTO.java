package unioeste.br.bocajuniorsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ExerciseFormDTO {
    private String title;
    private String description;
    private String tag;
    private String difficulty;
    private String sourceCode;
    private List<TestCaseFormDTO> testCaseList;
}
