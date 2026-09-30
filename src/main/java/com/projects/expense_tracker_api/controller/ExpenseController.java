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

import com.projects.expense_tracker_api.dto.ExpenseRequestDto;
import com.projects.expense_tracker_api.dto.ExpenseResponseDto;
import com.projects.expense_tracker_api.service.ExpenseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/expenses")
@SecurityRequirement(name = "bearerAuth")
public class ExpenseController {

	private final ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	@Operation(summary = "Get current user's expenses")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized") })
	@GetMapping
	public List<ExpenseResponseDto> getAllExpenses() {
		return expenseService.getAllExpense();
	}

	@Operation(summary = "Create a new expense")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Expense created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid expense data"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Category not found") })
	@PostMapping
	public ResponseEntity<Void> enterExpenses(@Valid @RequestBody ExpenseRequestDto expense) {

		expenseService.saveExpense(expense);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "Get an expense by ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Expense found"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Expense not found") })
	@GetMapping("/{id}")
	public ExpenseResponseDto getExpenseByIds(@PathVariable Long id) {
		return expenseService.getExpenseById(id);
	}

	@Operation(summary = "Update an expense")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Expense updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid expense data"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Expense or category not found") })
	@PutMapping("/{id}")
	public ExpenseResponseDto updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseRequestDto expense) {

		return expenseService.updateExpense(id, expense);
	}

	@Operation(summary = "Delete an expense")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Expense deleted successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Expense not found") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteExpenses(@PathVariable Long id) {

		expenseService.deleteExpense(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
