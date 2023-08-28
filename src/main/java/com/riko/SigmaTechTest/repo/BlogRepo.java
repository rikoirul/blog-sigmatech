package com.riko.SigmaTechTest.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riko.SigmaTechTest.model.BlogModel;

@Repository
public interface BlogRepo extends JpaRepository<BlogModel, Long> {
	List<BlogModel> findByIsDelete(Boolean isDelete);
	Optional<BlogModel> findByIdAndIsDelete(Long id, Boolean isDelete);
}
