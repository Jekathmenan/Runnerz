package com.example.demo.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs/")
public class RunController {

    private final RunRepository runRepository;
    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping("")
    List<Run> findAll() {
        return runRepository.findAll();
    }

    @GetMapping("{id}")
    Run findById(@PathVariable Integer id) {
        Optional<Run> r = runRepository.findById(id);
        if (r.isEmpty())
            throw new RunNotFoundException();

        return r.get();
    }

    @GetMapping("location/{location}")
    List<Run> findByLocation (@PathVariable String location) {
        return runRepository.findAllBy(location);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Run run) {
        runRepository.save(run);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("many")
    void createMany(@Valid @RequestBody List<Run> runs) {
        runRepository.saveAll(runs);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/")
    void update (@Valid @RequestBody Run run) {
        runRepository.save(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping ("/{id}")
    void delete (@PathVariable Integer id) {
        runRepository.deleteById(id);
    }
}
