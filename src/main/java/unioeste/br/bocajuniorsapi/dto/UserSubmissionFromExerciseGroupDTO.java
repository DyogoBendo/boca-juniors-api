package unioeste.br.bocajuniorsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.User;

import java.util.List;
import java.util.Map;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserSubmissionFromExerciseGroupDTO {
    private List<String> usernameList;
    private List<Exercise> exerciseList;
    private Map<String, Boolean> userExerciseStatus;
}
