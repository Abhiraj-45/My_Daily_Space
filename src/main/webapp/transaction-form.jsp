<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${transaction == null ? 'Add' : 'Edit'} Transaction - Daily Space</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/transactions.css" rel="stylesheet">
</head>
<body>
    <div class="container py-4">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-header">
                        <h3 class="mb-0">${transaction == null ? 'Add New' : 'Edit'} Transaction</h3>
                    </div>
                    <div class="card-body">
                        <form action="transactions" method="post">
                            <input type="hidden" name="action" 
                                   value="${transaction == null ? 'insert' : 'update'}">
                            <c:if test="${transaction != null}">
                                <input type="hidden" name="id" value="${transaction.id}">
                            </c:if>
                            
                            <div class="row g-3">
                                <!-- Type Selection -->
                                <div class="col-md-6">
                                    <label class="form-label">Type</label>
                                    <div class="btn-group w-100" role="group">
                                        <input type="radio" class="btn-check" name="type" 
                                               id="typeIncome" value="INCOME" 
                                               ${transaction == null || transaction.type == 'INCOME' ? 'checked' : ''}>
                                        <label class="btn btn-outline-success" for="typeIncome">Income</label>
                                        
                                        <input type="radio" class="btn-check" name="type" 
                                               id="typeExpense" value="EXPENSE"
                                               ${transaction != null && transaction.type == 'EXPENSE' ? 'checked' : ''}>
                                        <label class="btn btn-outline-danger" for="typeExpense">Expense</label>
                                    </div>
                                </div>
                                
                                <!-- Date -->
                                <div class="col-md-6">
                                    <label for="date" class="form-label">Date</label>
                                    <input type="date" class="form-control" id="date" name="date" 
                                           value="${transaction != null ? transaction.date : ''}" required>
                                </div>
                                
                                <!-- Title -->
                                <div class="col-12">
                                    <label for="title" class="form-label">Title</label>
                                    <input type="text" class="form-control" id="title" name="title" 
                                           value="${transaction != null ? transaction.title : ''}" required>
                                </div>
                                
                                <!-- Amount -->
                                <div class="col-md-6">
                                    <label for="amount" class="form-label">Amount</label>
                                    <div class="input-group">
                                        <span class="input-group-text">₹</span>
                                        <input type="number" step="0.01" class="form-control" id="amount" 
                                               name="amount" value="${transaction != null ? transaction.amount : ''}" required>
                                    </div>
                                </div>
                                
                                <!-- Category -->
                                <div class="col-md-6">
                                    <label for="category" class="form-label">Category</label>
                                    <select class="form-select" id="category" name="category" required>
									    <option value="">Select Category</option>
									    <!-- Income Categories -->
									    <optgroup label="Income" class="income-options">
									        <option value="Salary">Salary</option>
									        <option value="Bonus">Bonus</option>
									    </optgroup>
									    <!-- Expense Categories -->
									    <optgroup label="Expense" class="expense-options">
									        <option value="Food">Food</option>
									        <option value="Rent">Rent</option>
									        <option value="Transport">Transport</option>
									        <option value="Other">Other Expenses</option> <!-- NEW CATEGORY -->
									    </optgroup>
									</select>
                                </div>
                                
                                <!-- Notes -->
                                <div class="col-12">
                                    <label for="notes" class="form-label">Notes</label>
                                    <textarea class="form-control" id="notes" name="notes" rows="3">${transaction != null ? transaction.notes : ''}</textarea>
                                </div>
                                
                                <!-- Form Actions -->
                                <div class="col-12 mt-4">
                                    <div class="d-flex justify-content-end gap-2">
                                        <a href="transactions" class="btn btn-secondary">Cancel</a>
                                        <button type="submit" class="btn btn-primary">
                                            ${transaction == null ? 'Add Transaction' : 'Update Transaction'}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Dynamic category filtering
        document.querySelectorAll('input[name="type"]').forEach(radio => {
            radio.addEventListener('change', function() {
                const isIncome = this.value === 'INCOME';
                document.querySelectorAll('.income-options option').forEach(opt => {
                    opt.hidden = !isIncome;
                });
                document.querySelectorAll('.expense-options option').forEach(opt => {
                    opt.hidden = isIncome;
                });
                
                // Reset category if incompatible
                const categorySelect = document.getElementById('category');
                const selectedOptGroup = categorySelect.options[categorySelect.selectedIndex]?.parentElement;
                if ((isIncome && selectedOptGroup?.classList.contains('expense-options')) || 
                    (!isIncome && selectedOptGroup?.classList.contains('income-options'))) {
                    categorySelect.value = '';
                }
            });
        });

        // Initialize form based on current type
        const initialType = document.querySelector('input[name="type"]:checked').value;
        document.dispatchEvent(new CustomEvent('typeChange', { detail: initialType }));
    </script>
</body>
</html>