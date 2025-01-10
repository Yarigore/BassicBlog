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

    public Optional<Tag> getTagById(Long id){
        return tagRepository.findById(id);
    }

    public Optional<List<Tag>> getTags(){
        return Optional.of(tagRepository.findAll());
    }

    public Optional<Tag> saveTag(Tag tag){
        return Optional.of(tagRepository.save(tag));
    }

    public Optional<Tag> deleteTag(Long id){
        Optional<Tag> deleteTag = tagRepository.findById(id);
        tagRepository.deleteById(id);
        return deleteTag;
    }

}
