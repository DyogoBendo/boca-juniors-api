package unioeste.br.bocajuniorsapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.Submission;
import unioeste.br.bocajuniorsapi.domain.TestCase;
import unioeste.br.bocajuniorsapi.dto.*;
import unioeste.br.bocajuniorsapi.service.ExerciseGroupService;
import unioeste.br.bocajuniorsapi.service.ExerciseService;
import unioeste.br.bocajuniorsapi.service.SubmissionService;
import unioeste.br.bocajuniorsapi.service.TestCaseService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/exercise")
@CrossOrigin
@AllArgsConstructor
public class ExerciseController {
    private ExerciseService exerciseService;
    private TestCaseService testCaseService;
    private SubmissionService submissionService;
    private ExerciseGroupService exerciseGroupService;

    @GetMapping
    public ResponseEntity<List<Exercise>> list(){
        List<Exercise> exerciseList =exerciseService.list();
        return ResponseEntity.ok(exerciseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseWithExamplesDTO> get(@PathVariable Long id){
        Exercise exercise = exerciseService.findById(id);
        List<TestCase> testCaseList = testCaseService.findExampleByExercise(exercise);

        List<ExampleDTO> exampleDTOList = testCaseService.convertToExercise(testCaseList);

        ExerciseWithExamplesDTO exerciseWithExamplesDTO = exerciseService.convert(exercise, exampleDTOList);

        return ResponseEntity.ok(exerciseWithExamplesDTO);
    }

    @GetMapping("/complete/{id}")
    public ResponseEntity<ExerciseWithTestCasesDTO> getComplete(@PathVariable Long id){
        Exercise exercise = exerciseService.findById(id);
        List<TestCase> testCaseList = testCaseService.findByExercise(exercise);

        List<TestCaseDTO> testCaseDTOList = testCaseService.convert(testCaseList);

        ExerciseWithTestCasesDTO exerciseWithTestCasesDTO = exerciseService.convertToTestCase(exercise, testCaseDTOList);

        return ResponseEntity.ok(exerciseWithTestCasesDTO);
    }

    @PostMapping
    public ResponseEntity<Exercise> create(@RequestBody ExerciseFormDTO form){
        Exercise exercise = exerciseService.convert(form);

        exerciseService.save(exercise);

        List<TestCase> testCaseList = testCaseService.convert(form.getTestCaseList(), exercise);

        testCaseService.save(testCaseList);
        return ResponseEntity.ok(exercise);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ExerciseFormDTO form){
        Exercise exercise = exerciseService.findById(id);
        List<TestCase> oldTestCaseList = testCaseService.findByExercise(exercise);
        testCaseService.delete(oldTestCaseList);

        exerciseService.save(exercise);

        List<TestCase> testCaseList = testCaseService.convert(form.getTestCaseList(), exercise);

        testCaseService.save(testCaseList);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Exercise exercise = exerciseService.findById(id);
        List<TestCase> oldTestCaseList = testCaseService.findByExercise(exercise);
        testCaseService.delete(oldTestCaseList);

        List<Submission> submissionList = submissionService.findSubmissionsByExerciseList(Collections.singletonList(exercise));
        submissionService.delete(submissionList);

        exerciseGroupService.removeExercise(exercise);

        exerciseService.delete(exercise);
        return ResponseEntity.noContent().build();
    }
}
