package com.dimas.BassicBlog.Service;

import com.dimas.BassicBlog.Entity.Tag;
import com.dimas.BassicBlog.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getTagsByIds(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

    public Optional<Tag> saveTag(Tag tag) {
        return Optional.of(tagRepository.save(tag));
    }

    public boolean deleteTag(Long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
