package com.ukefu.webim.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.webim.web.model.Skill;


/**
 * 
 * @author admin
 *
 */
public interface SkillRepository extends JpaRepository<Skill, String> {

    Skill findByIdAndOrgi(String id , String orgi);

    Page<Skill> findByOrgi(String orgi , Pageable pageable);

	Skill findByNameAndOrgi(String name , String orgi);
}
