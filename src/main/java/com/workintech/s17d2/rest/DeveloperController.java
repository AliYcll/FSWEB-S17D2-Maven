package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    public Map<Integer, Developer> developers;
    private final Taxable taxable;

    @Autowired
    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    @PostConstruct
    public void init() {
        this.developers = new HashMap<>();
    }

    @GetMapping
    public List<Developer> getAll() {
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Developer getById(@PathVariable int id) {
        return developers.get(id);
    }

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Developer save(@RequestBody Developer developer) {
        Developer newDeveloper = null;
        switch (developer.getExperience()) {
            case JUNIOR:
                newDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - (developer.getSalary() * taxable.getSimpleTaxRate() / 100));
                break;
            case MID:
                newDeveloper = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - (developer.getSalary() * taxable.getMiddleTaxRate() / 100));
                break;
            case SENIOR:
                newDeveloper = new SeniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - (developer.getSalary() * taxable.getUpperTaxRate() / 100));
                break;
        }
        if (newDeveloper != null) {
            developers.put(newDeveloper.getId(), newDeveloper);
        }
        return newDeveloper;
    }

    @PutMapping("/{id}")
    public Developer update(@PathVariable int id, @RequestBody Developer developer) {
        developer.setId(id);
        Developer newDeveloper = null;
        switch (developer.getExperience()) {
            case JUNIOR:
                newDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - (developer.getSalary() * taxable.getSimpleTaxRate() / 100));
                break;
            case MID:
                newDeveloper = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - (developer.getSalary() * taxable.getMiddleTaxRate() / 100));
                break;
            case SENIOR:
                newDeveloper = new SeniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - (developer.getSalary() * taxable.getUpperTaxRate() / 100));
                break;
        }
        if (newDeveloper != null) {
            developers.put(id, newDeveloper);
        }
        return newDeveloper;
    }

    @DeleteMapping("/{id}")
    public Developer delete(@PathVariable int id) {
        return developers.remove(id);
    }
}
