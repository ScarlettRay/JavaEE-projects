package com.ukefu.webim.service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.webim.web.model.Organ;

public abstract interface OrganRepository
  extends JpaRepository<Organ, String>
{
  public abstract Organ findByIdAndOrgi(String paramString, String orgi);
  
  public abstract Page<Organ> findByOrgi(String orgi , Pageable paramPageable);
  
  public abstract Organ findByNameAndOrgi(String paramString, String orgi);
  
  public abstract List<Organ> findByOrgi(String orgi);
  
  public abstract List<Organ> findByOrgiAndSkill(String orgi , boolean skill);
}
