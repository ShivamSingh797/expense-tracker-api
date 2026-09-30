package com.projects.expense_tracker_api.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.projects.expense_tracker_api.dto.ExpenseRequestDto;
import com.projects.expense_tracker_api.dto.ExpenseResponseDto;
import com.projects.expense_tracker_api.entity.Category;
import com.projects.expense_tracker_api.entity.Expense;
import com.projects.expense_tracker_api.entity.User;
import com.projects.expense_tracker_api.exception.ResourceNotFoundException;
import com.projects.expense_tracker_api.mapper.ExpenseMapper;
import com.projects.expense_tracker_api.repository.CategoryRepository;
import com.projects.expense_tracker_api.repository.ExpenseRepository;
import com.projects.expense_tracker_api.repository.UserRepository;

@Service
public class ExpenseService {

	private final ExpenseRepository expenseRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final ExpenseMapper expenseMapper;

	public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository,
			CategoryRepository categoryRepository, ExpenseMapper expenseMapper) {

		this.expenseRepository = expenseRepository;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.expenseMapper = expenseMapper;
	}

	private User getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	public ExpenseResponseDto saveExpense(ExpenseRequestDto expense) {

		Expense expense_1 = expenseMapper.ConvertRequestToExpense(expense);

		User user = getCurrentUser();

		expense_1.setUser(user);

		Category category = categoryRepository.findById(expense.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		expense_1.setCategory(category);

		Expense expense_2 = expenseRepository.save(expense_1);

		return expenseMapper.ConvertExpenseToResponse(expense_2);
	}

	public List<ExpenseResponseDto> getAllExpense() {

		User user = getCurrentUser();

		return expenseRepository.findByUser(user).stream().map(expenseMapper::ConvertExpenseToResponse).toList();
	}

	public ExpenseResponseDto getExpenseById(Long id) {

		User user = getCurrentUser();

		Expense expense = expenseRepository.findByIdAndUser(id, user)
				.orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

		return expenseMapper.ConvertExpenseToResponse(expense);
	}

	public ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto updatedExpense) {

		User user = getCurrentUser();

		Expense expense = expenseRepository.findByIdAndUser(id, user)
				.orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

		expense.setTitle(updatedExpense.getTitle());
		expense.setAmount(updatedExpense.getAmount());
		expense.setDescription(updatedExpense.getDescription());

		Category category = categoryRepository.findById(updatedExpense.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		expense.setCategory(category);

		Expense expense_1 = expenseRepository.save(expense);

		return expenseMapper.ConvertExpenseToResponse(expense_1);
	}

	public void deleteExpense(Long id) {

		User user = getCurrentUser();

		Expense expense = expenseRepository.findByIdAndUser(id, user)
				.orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

		expenseRepository.delete(expense);
	}

	List<Expense> findByAmountGreaterThan(Double amount) {
		return expenseRepository.findByAmountGreaterThan(amount);
	}

	List<Expense> findByAmountLessThan(Double amount) {
		return expenseRepository.findByAmountLessThan(amount);
	}

	public Double CalculateExpense() {
		User user = getCurrentUser();
		List<Expense> expense = expenseRepository.findByUser(user);
		return expense.stream().mapToDouble(Expense::getAmount).sum();
	}

	public Long totalExpenseEntry() {
		User user = getCurrentUser();

		return expenseRepository.countByUser(user);
	}
}
