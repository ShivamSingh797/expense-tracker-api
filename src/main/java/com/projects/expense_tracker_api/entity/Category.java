package com.projects.expense_tracker_api.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String type;

	public Category() {
		super();
	}

	public Category(Long id, String type, List<Expense> expenses) {
		super();
		this.id = id;
		this.type = type;
		this.expenses = expenses;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", type=" + type + ", expenses=" + expenses + "]";
	}

	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private List<Expense> expenses;
}
