package com.projects.expense_tracker_api.mapper;

import org.springframework.stereotype.Component;

import com.projects.expense_tracker_api.dto.ExpenseRequestDto;
import com.projects.expense_tracker_api.dto.ExpenseResponseDto;
import com.projects.expense_tracker_api.entity.Expense;

@Component
public class ExpenseMapper {

	public Expense ConvertRequestToExpense(ExpenseRequestDto dto) {
		Expense expense = new Expense();

		expense.setTitle(dto.getTitle());
		expense.setAmount(dto.getAmount());
		expense.setDescription(dto.getDescription());

		return expense;
	}

	public ExpenseResponseDto ConvertExpenseToResponse(Expense expense) {

		ExpenseResponseDto dto = new ExpenseResponseDto();

		dto.setId(expense.getId());
		dto.setTitle(expense.getTitle());
		dto.setAmount(expense.getAmount());
		dto.setDescription(expense.getDescription());

		if (expense.getCategory() != null) {
			dto.setCategoryId(expense.getCategory().getId());
		}

		return dto;
	}
}
