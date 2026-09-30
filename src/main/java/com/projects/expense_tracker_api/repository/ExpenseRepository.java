package com.projects.expense_tracker_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.expense_tracker_api.entity.Expense;
import com.projects.expense_tracker_api.entity.User;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	List<Expense> findByAmountGreaterThan(Double amount);

	List<Expense> findByAmountLessThan(Double amount);

	List<Expense> findByUser(User user);

	Optional<Expense> findByIdAndUser(Long id, User user);

	long countByUser(User user);
}
