package com.projects.expense_tracker_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ExpenseRequestDto {

	@NotBlank
	private String title;

	@NotNull
	@Positive
	private Double amount;
	private String description;

	@NotNull
	private Long categoryId;

	public ExpenseRequestDto() {
		super();
	}

	public ExpenseRequestDto(String title, Double amount, String description, Long categoryId) {
		super();
		this.title = title;
		this.amount = amount;
		this.description = description;
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "ExpenseRequestDto [title=" + title + ", amount=" + amount + ", description=" + description
				+ ", categoryId=" + categoryId + "]";
	}

}
