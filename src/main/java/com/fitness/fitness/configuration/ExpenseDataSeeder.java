package com.fitness.fitness.configuration;

import com.fitness.fitness.model.Expense;
import com.fitness.fitness.repository.ExpenseRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExpenseDataSeeder implements CommandLineRunner {

    private final ExpenseRepo expenseRepo;

    public ExpenseDataSeeder(ExpenseRepo expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (expenseRepo.count() == 0) {
            seedExpenses();
        }
    }

    private void seedExpenses() {
        List<Expense> expenses = new ArrayList<>();

        Expense electricityExpense = new Expense("Electricity", 500.0, LocalDate.of(2024, 4, 1));
        Expense waterExpense = new Expense("Water Bill", 300.0, LocalDate.of(2024, 4, 5));
        Expense gasExpense = new Expense("Gas Bill", 200.0, LocalDate.of(2024, 4, 10));
        Expense operationalExpense = new Expense("Operational Expenditure", 1500.0, LocalDate.of(2024, 4, 15));
        Expense additionalExpense1 = new Expense("Setting CCTV", 100.0, LocalDate.of(2024, 4, 20));
        Expense additionalExpense2 = new Expense("Fixing GYM Machines", 200.0, LocalDate.of(2024, 4, 25));

        expenses.add(electricityExpense);
        expenses.add(waterExpense);
        expenses.add(gasExpense);
        expenses.add(operationalExpense);
        expenses.add(additionalExpense1);
        expenses.add(additionalExpense2);

        expenseRepo.saveAll(expenses);
    }
}
