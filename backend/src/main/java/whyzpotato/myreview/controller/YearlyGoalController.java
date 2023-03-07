package whyzpotato.myreview.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whyzpotato.myreview.dto.GoalResponseDto;
import whyzpotato.myreview.service.YearlyGoalService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class YearlyGoalController {

    private final YearlyGoalService yearlyGoalService;

    @GetMapping("/v1/goal/{id}")
    public ResponseEntity<GoalResponseDto> getGoal(@PathVariable("id") Long id) {
        return new ResponseEntity<>(yearlyGoalService.currentGoal(id), HttpStatus.OK);
    }

    @PutMapping("/v1/goal/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GoalResponseDto> postGoal(@PathVariable("id") Long id, @RequestBody @Valid int target) {
        return new ResponseEntity<>(yearlyGoalService.updateYearlyGoal(id, target), HttpStatus.OK);
    }

    @GetMapping("/v1/goal/history/{id}")
    public ResponseEntity<historyDto> getGoalHistory(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new historyDto(id, yearlyGoalService.historyYearlyGoal(id)), HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class historyDto<T> {
        private Long id;
        private T goals;
    }
}
