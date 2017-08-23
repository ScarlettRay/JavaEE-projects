package com.ukefu.webim.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.webim.web.model.Reporter;

public interface ReporterRepository extends  JpaRepository<Reporter, String> {
}
