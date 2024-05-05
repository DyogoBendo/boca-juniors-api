package unioeste.br.bocajuniorsapi.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.ExercisesGroup;
import unioeste.br.bocajuniorsapi.domain.Submission;
import unioeste.br.bocajuniorsapi.domain.SubmissionStatus;
import unioeste.br.bocajuniorsapi.dto.ExerciseGroupFormDTO;
import unioeste.br.bocajuniorsapi.dto.UserSubmissionFromExerciseGroupDTO;
import unioeste.br.bocajuniorsapi.repository.ExerciseRepository;
import unioeste.br.bocajuniorsapi.repository.ExercisesGroupRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public void convert(ExercisesGroup exercisesGroup, ExerciseGroupFormDTO form, List<Exercise> exerciseList){
        exercisesGroup.setName(form.getName());
        exercisesGroup.setOpen(form.isOpen());
        exercisesGroup.setExerciseList(exerciseList);
    }

    public Map<String, Boolean> generateUserExerciseStatus(List<Submission> submissionList){
        Map<String, Boolean> userExerciseStatus = new HashMap<>();
        for (Submission submission : submissionList){
            String username = submission.getUsername();
            Exercise exercise = submission.getExercise();
            String key = generateUserExerciseKey(username, exercise);

            if (userExerciseStatus.containsKey(key)){
                Boolean previousValue = userExerciseStatus.get(key);
                userExerciseStatus.put(key, previousValue || submission.getStatus().equals(SubmissionStatus.AC));
            } else{
                userExerciseStatus.put(key, submission.getStatus().equals(SubmissionStatus.AC));
            }
        }
        return userExerciseStatus;
    }

    public UserSubmissionFromExerciseGroupDTO convert(List<String> usernameList, List<Exercise> exerciseList, Map<String, Boolean> userExerciseStatus){
        return new UserSubmissionFromExerciseGroupDTO(usernameList, exerciseList, userExerciseStatus);
    }

    public String generateUserExerciseKey(String username, Exercise exercise){
        return username + "," + exercise.getId().toString();
    }

    public void removeExercise(Exercise exercise){
        List<ExercisesGroup> exercisesGroupList = exercisesGroupRepository.findAll();

        for (ExercisesGroup exercisesGroup : exercisesGroupList){
            List<Exercise> exerciseList = exercisesGroup.getExerciseList();
            exerciseList.remove(exercise);
        }

        exercisesGroupRepository.saveAll(exercisesGroupList);
    }

    public void delete(ExercisesGroup exercisesGroup){
        exercisesGroupRepository.delete(exercisesGroup);
    }
}
