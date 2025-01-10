package com.dimas.BassicBlog.Repository;

import com.dimas.BassicBlog.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
