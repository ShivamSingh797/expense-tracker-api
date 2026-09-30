package com.projects.expense_tracker_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.expense_tracker_api.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByType(String type);

	boolean existsByType(String type);
}
