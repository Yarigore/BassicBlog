package com.dimas.BassicBlog.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private Users author;

    @ManyToOne
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "post_tags", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "post_id"), // Clave foránea hacia `Post`
            inverseJoinColumns = @JoinColumn(name = "tag_id") // Clave foránea hacia `Tag`
    )
    private List<Tag> tags = new ArrayList<>();

    /*
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;
    */

}
