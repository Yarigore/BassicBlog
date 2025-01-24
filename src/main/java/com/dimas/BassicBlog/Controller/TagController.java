package com.dimas.BassicBlog.Controller;

import com.dimas.BassicBlog.DTO.TagDTO.TagRequestDTO;
import com.dimas.BassicBlog.DTO.TagDTO.TagResponseDTO;
import com.dimas.BassicBlog.Entity.Tag;
import com.dimas.BassicBlog.Mapper.TagMapper;
import com.dimas.BassicBlog.Service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/tags")
@AllArgsConstructor
public class TagController {

    private TagService tagService;
    private TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponseDTO>> getAllTags() {
        List<TagResponseDTO> tags = tagService.getAllTags()
                .stream()
                .map(tagMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDTO> getTagById(@PathVariable Long id) {
        return tagService.getTagById(id)
                .map(tagMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TagResponseDTO> createTag(@RequestBody TagRequestDTO tagRequestDTO) {
        Tag tag = tagMapper.toEntity(tagRequestDTO);
        return tagService.saveTag(tag)
                .map(tagMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TagResponseDTO> updateTag(@PathVariable Long id, @RequestBody TagRequestDTO tagRequestDTO) {
        Optional<Tag> tagToUpdate = tagService.getTagById(id);

        if (tagToUpdate.isPresent()) {
            Tag existingTag = tagToUpdate.get();

            if (tagRequestDTO.getName() != null) {
                existingTag.setName(tagRequestDTO.getName());
            }

            return tagService.saveTag(existingTag)
                    .map(tagMapper::toResponseDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(500).build());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        boolean isDeleted = tagService.deleteTag(id);
        if (isDeleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }
}
