package com.projects.expense_tracker_api.dto;

public class ExpenseResponseDto {

	private Long id;
	private String title;
	private Double amount;
	private String description;
	private Long categoryId;

	public ExpenseResponseDto() {
		super();
	}

	public ExpenseResponseDto(Long id, String title, Double amount, String description, Long categoryId) {

		this.id = id;
		this.title = title;
		this.amount = amount;
		this.description = description;
		this.categoryId = categoryId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Double getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	@Override
	public String toString() {
		return "ExpenseResponseDto [id=" + id + ", title=" + title + ", amount=" + amount + ", description="
				+ description + ", categoryId=" + categoryId + "]";
	}

}
