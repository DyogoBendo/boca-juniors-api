package unioeste.br.bocajuniorsapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.TestCase;
import unioeste.br.bocajuniorsapi.dto.ExampleDTO;
import unioeste.br.bocajuniorsapi.dto.ExerciseFormDTO;
import unioeste.br.bocajuniorsapi.dto.ExerciseWithExamplesDTO;
import unioeste.br.bocajuniorsapi.service.ExerciseService;
import unioeste.br.bocajuniorsapi.service.TestCaseService;

import java.util.List;

@RestController
@RequestMapping("/exercise")
@CrossOrigin
@AllArgsConstructor
public class ExerciseController {
    private ExerciseService exerciseService;
    private TestCaseService testCaseService;

    @GetMapping
    public ResponseEntity<List<Exercise>> list(){
        List<Exercise> exerciseList =exerciseService.list();
        return ResponseEntity.ok(exerciseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseWithExamplesDTO> get(@PathVariable Long id){
        Exercise exercise = exerciseService.findById(id);
        List<TestCase> testCaseList = testCaseService.findExampleByExercise(exercise);

        List<ExampleDTO> exampleDTOList = testCaseService.convert(testCaseList);

        ExerciseWithExamplesDTO exerciseWithExamplesDTO = exerciseService.convert(exercise, exampleDTOList);

        return ResponseEntity.ok(exerciseWithExamplesDTO);
    }

    @PostMapping
    public ResponseEntity<Exercise> create(@RequestBody ExerciseFormDTO form){
        Exercise exercise = exerciseService.convert(form);

        exerciseService.save(exercise);

        List<TestCase> testCaseList = testCaseService.convert(form.getTestCaseList(), exercise);

        testCaseService.save(testCaseList);
        return ResponseEntity.ok(exercise);
    }
}
