package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    @PostMapping("/")
    public Tag create(@RequestBody Tag tag) {
        return service.createNewTag(tag);
    }

    @GetMapping("/{id}")
    public Tag find(@PathVariable int id) {
        return service.getTagById(id);
    }

    @GetMapping(value = "/")
    public List<Tag> findAll() {
        return service.getAllTags();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.deleteTagById(id);
    }
}
