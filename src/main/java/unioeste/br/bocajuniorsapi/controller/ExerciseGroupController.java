package unioeste.br.bocajuniorsapi.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.ExercisesGroup;
import unioeste.br.bocajuniorsapi.domain.Submission;
import unioeste.br.bocajuniorsapi.dto.ExerciseGroupFormDTO;
import unioeste.br.bocajuniorsapi.dto.UserSubmissionFromExerciseGroupDTO;
import unioeste.br.bocajuniorsapi.service.ExerciseGroupService;
import unioeste.br.bocajuniorsapi.service.ExerciseService;
import unioeste.br.bocajuniorsapi.service.SubmissionService;
import unioeste.br.bocajuniorsapi.service.UserService;

import java.util.List;
import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@RestController
@RequestMapping("/exercise-group")
@CrossOrigin
public class ExerciseGroupController {
    private ExerciseGroupService exerciseGroupService;
    private ExerciseService exerciseService;
    private UserService userService;
    private SubmissionService submissionService;

    @GetMapping
    public ResponseEntity<List<ExercisesGroup>> list(){
        List<ExercisesGroup> exercisesGroupList = exerciseGroupService.list();
        return ResponseEntity.ok(exercisesGroupList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExercisesGroup> get(@PathVariable Long id){
        ExercisesGroup exercisesGroup = exerciseGroupService.findById(id);

        return ResponseEntity.ok(exercisesGroup);
    }

    @PostMapping
    public ResponseEntity<ExercisesGroup> create(@RequestBody ExerciseGroupFormDTO form){
        List<Exercise> exerciseList = exerciseService.findById(form.getExerciseIdList());
        ExercisesGroup exercisesGroup = exerciseGroupService.convert(form, exerciseList);

        exerciseGroupService.save(exercisesGroup);

        return ResponseEntity.ok(exercisesGroup);
    }

    @GetMapping("/{id}/users-submissions")
    public ResponseEntity<UserSubmissionFromExerciseGroupDTO> listUserSubmissions(@PathVariable Long id){
        ExercisesGroup exercisesGroup = exerciseGroupService.findById(id);
        List<Exercise> exerciseList = exercisesGroup.getExerciseList();
        List<Submission> submissionList = submissionService.findSubmissionsByExerciseList(exerciseList);
        List<String> usernameList = userService.findUsernamesBySubmission(submissionList);

        Map<String, Boolean> userExerciseStatus = exerciseGroupService.generateUserExerciseStatus(submissionList);

        UserSubmissionFromExerciseGroupDTO userSubmissionFromExerciseGroupDTO = exerciseGroupService.convert(usernameList, exerciseList, userExerciseStatus);

        return ResponseEntity.ok(userSubmissionFromExerciseGroupDTO);
    }
}
