package unioeste.br.bocajuniorsapi.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.TestCase;
import unioeste.br.bocajuniorsapi.dto.ExampleDTO;
import unioeste.br.bocajuniorsapi.dto.TestCaseDTO;
import unioeste.br.bocajuniorsapi.dto.TestCaseFormDTO;
import unioeste.br.bocajuniorsapi.repository.TestCaseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TestCaseService {
    private TestCaseRepository testCaseRepository;

    public TestCase convert(TestCaseFormDTO form, Exercise exercise){
        TestCase testCase = new TestCase();
        testCase.setExample(form.isExample());
        testCase.setExercise(exercise);
        testCase.setOutput(form.getOutput());
        testCase.setInput(form.getInput());

        return testCase;
    }

    public List<TestCase> convert(List<TestCaseFormDTO> formList, Exercise exercise){
        if (formList == null) return new ArrayList<>();
        return formList.stream().map(form -> convert(form, exercise)).filter(testCase -> !(testCase.getInput().isEmpty() || testCase.getOutput().isEmpty())).toList();
    }

    public void save(List<TestCase> testCaseList){
        if (testCaseList == null || testCaseList.isEmpty()) return;
        testCaseRepository.saveAll(testCaseList);
    }

    @Transactional
    public List<TestCase> findExampleByExercise(Exercise exercise){
        return testCaseRepository.findByExerciseAndExample(exercise, true);
    }

    @Transactional
    public List<TestCase> findByExercise(Exercise exercise){
        return testCaseRepository.findByExercise(exercise);
    }

    public TestCaseDTO convert(TestCase testCase){
        TestCaseDTO testCaseDTO = new TestCaseDTO();

        testCaseDTO.setId(testCase.getId());
        testCaseDTO.setInput(testCase.getInput());
        testCaseDTO.setOutput(testCase.getOutput());
        testCaseDTO.setExample(testCaseDTO.isExample());

        return testCaseDTO;
    }

    public ExampleDTO convertToExample(TestCase testCase){
        ExampleDTO exampleDTO = new ExampleDTO();

        exampleDTO.setInput(testCase.getInput());
        exampleDTO.setOutput(testCase.getOutput());

        return exampleDTO;
    }

    public List<ExampleDTO> convertToExercise(List<TestCase> testCaseList){
        return testCaseList.stream().map(this::convertToExample).toList();
    }

    public List<TestCaseDTO> convert(List<TestCase> testCaseList){
        return testCaseList.stream().map(this::convert).toList();
    }

    public void delete(List<TestCase> testCaseList){
        testCaseRepository.deleteAll(testCaseList);
    }
}
