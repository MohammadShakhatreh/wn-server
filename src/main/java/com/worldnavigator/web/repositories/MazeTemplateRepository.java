package com.worldnavigator.web.repositories;

import com.worldnavigator.web.entities.MazeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MazeTemplateRepository extends JpaRepository<MazeTemplate, Long> {}
