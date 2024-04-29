package unioeste.br.bocajuniorsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TestCaseFormDTO {
    private boolean example;
    private String input;
    private String output;
}
