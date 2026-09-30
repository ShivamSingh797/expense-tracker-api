package com.projects.expense_tracker_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projects.expense_tracker_api.entity.Category;
import com.projects.expense_tracker_api.exception.CategoryAlreadyExistsException;
import com.projects.expense_tracker_api.exception.ResourceNotFoundException;
import com.projects.expense_tracker_api.repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Category saveCategory(Category category) {
		category.setType(category.getType().trim().toLowerCase());
		if (categoryRepository.existsByType(category.getType())) {
			throw new CategoryAlreadyExistsException("Category already exists");
		}
		return categoryRepository.save(category);
	}

	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	public Category updateCategory(Long id, Category updatedCategory) {

		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		String newType = updatedCategory.getType().trim().toLowerCase();

		if (!category.getType().equals(newType) && categoryRepository.existsByType(newType)) {
			throw new CategoryAlreadyExistsException("Category already exists");
		}

		category.setType(newType);

		return categoryRepository.save(category);
	}

	public void deleteCategory(Long id) {

		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		categoryRepository.delete(category);
	}

	public Long totalCategoryEntry() {
		return categoryRepository.count();
	}
}
