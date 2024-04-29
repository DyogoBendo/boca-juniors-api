package unioeste.br.bocajuniorsapi.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.ExercisesGroup;
import unioeste.br.bocajuniorsapi.dto.ExerciseGroupFormDTO;
import unioeste.br.bocajuniorsapi.repository.ExerciseRepository;
import unioeste.br.bocajuniorsapi.repository.ExercisesGroupRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Getter @Setter
public class ExerciseGroupService {
    private ExercisesGroupRepository exercisesGroupRepository;
    public void save(ExercisesGroup exercisesGroup){
        exercisesGroupRepository.save(exercisesGroup);
    }

    public List<ExercisesGroup> list(){
        return exercisesGroupRepository.findAll();
    }

    public ExercisesGroup findById(Long id){
        return exercisesGroupRepository.findById(id).orElse(null);
    }

    public ExercisesGroup convert(ExerciseGroupFormDTO form, List<Exercise> exerciseList){
        ExercisesGroup exercisesGroup = new ExercisesGroup();

        exercisesGroup.setName(form.getName());
        exercisesGroup.setOpen(form.isOpen());
        exercisesGroup.setExerciseList(exerciseList);

        return exercisesGroup;
    }

}
