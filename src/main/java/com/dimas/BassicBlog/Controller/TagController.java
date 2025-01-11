package com.dimas.BassicBlog.Controller;

import com.dimas.BassicBlog.Entity.Tag;
import com.dimas.BassicBlog.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getTags() {
        return tagService.getTags()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id){
        return tagService.getTagById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag){
        return tagService.saveTag(tag)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody Tag tag) {
        Optional<Tag> tagToChange = tagService.getTagById(id);

        if (tagToChange.isPresent()) {
            Tag existingTag = tagToChange.get();

            if (tag.getName() != null) {
                existingTag.setName(tag.getName());
            }

            Optional<Tag> updatedTag = tagService.saveTag(existingTag);

            return updatedTag
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(500).build());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tag> deleteTag(@PathVariable Long id){
        boolean isDeleted = tagService.deleteTag(id);
        if (isDeleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }

}
