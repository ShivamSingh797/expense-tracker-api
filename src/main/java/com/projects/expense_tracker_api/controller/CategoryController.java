package com.projects.expense_tracker_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.expense_tracker_api.entity.Category;
import com.projects.expense_tracker_api.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Operation(summary = "Get all categories")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized") })
	@GetMapping
	public List<Category> getAllCategoryEntry() {
		return categoryService.getAllCategory();
	}

	@Operation(summary = "Create a new category")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Category created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid category data"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "409", description = "Category already exists") })
	@PostMapping
	public ResponseEntity<Void> saveCategoryEntry(@Valid @RequestBody Category category) {

		categoryService.saveCategory(category);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "Update a category")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Category updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid category data"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Category not found"),
			@ApiResponse(responseCode = "409", description = "Category already exists") })
	@PutMapping("/{id}")
	public Category updateCategoryEntry(@PathVariable Long id, @Valid @RequestBody Category category) {

		return categoryService.updateCategory(id, category);
	}

	@Operation(summary = "Delete a category")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Category not found") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategoryEntryById(@PathVariable Long id) {

		categoryService.deleteCategory(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
