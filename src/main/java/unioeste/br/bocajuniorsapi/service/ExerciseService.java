package unioeste.br.bocajuniorsapi.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.dto.ExampleDTO;
import unioeste.br.bocajuniorsapi.dto.ExerciseFormDTO;
import unioeste.br.bocajuniorsapi.dto.ExerciseWithExamplesDTO;
import unioeste.br.bocajuniorsapi.repository.ExerciseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Getter @Setter
public class ExerciseService {
    private ExerciseRepository exerciseRepository;

    public List<Exercise> list(){
        return exerciseRepository.findAll();
    }

    public Exercise findById(Long id){
        if (id == null) return null;
        return exerciseRepository.findById(id).orElse(null);
    }

    public Exercise convert(ExerciseFormDTO form){
        Exercise exercise = new Exercise();
        exercise.setDescription(form.getDescription());
        exercise.setTag(form.getTag());
        exercise.setDifficulty(form.getDifficulty());
        exercise.setTitle(form.getTitle());
        exercise.setSourceCode(form.getSourceCode());

        return exercise;
    }

    public void save(Exercise exercise){
        exerciseRepository.save(exercise);
    }

    public List<Exercise> findById(List<Long> idList){
        if (idList == null || idList.isEmpty()) return new ArrayList<>();
        return idList.stream().map(this::findById).collect(Collectors.toList());
    }

    public ExerciseWithExamplesDTO convert(Exercise exercise, List<ExampleDTO> exampleDTOList){
        ExerciseWithExamplesDTO exerciseWithExamplesDTO = new ExerciseWithExamplesDTO();
        exerciseWithExamplesDTO.setExamples(exampleDTOList);
        exerciseWithExamplesDTO.setDescription(exercise.getDescription());
        exerciseWithExamplesDTO.setTag(exercise.getTag());
        exerciseWithExamplesDTO.setTitle(exercise.getTitle());
        exerciseWithExamplesDTO.setDifficulty(exercise.getDifficulty());

        return exerciseWithExamplesDTO;
    }
}
