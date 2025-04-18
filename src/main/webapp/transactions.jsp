<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transaction Manager - Daily Space</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/transactions.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <!-- Chart.js loaded in head for better performance -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <div class="container-fluid">
        <!-- Header -->
        
		<header class="d-flex justify-content-between align-items-center py-3 mb-4 border-bottom">
		    <h1 class="h4">Transaction Manager</h1>
		    <div>
		        <a href="dashboard.jsp" class="btn btn-outline-secondary me-2">
		            <i class="bi bi-house-door"></i> Home
		        </a>
		        <a href="transactions?action=new" class="btn btn-primary">
		            <i class="bi bi-plus-lg"></i> Add Transaction
		        </a>
		    </div>
		</header>

        <!-- Analytics Dashboard Section -->
        <div class="row mb-4">
            <div class="col-md-4">
                <div class="card text-white bg-primary mb-3 summary-card">
                    <div class="card-body p-3">
                        <h6 class="card-title mb-1">Income</h6>
                        <h4 class="mb-0">₹<span id="totalIncome">0.00</span></h4>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-white bg-danger mb-3 summary-card">
                    <div class="card-body p-3">
                        <h6 class="card-title mb-1">Expense</h6>
                        <h4 class="mb-0">₹<span id="totalExpense">0.00</span></h4>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-white bg-success mb-3 summary-card">
                    <div class="card-body p-3">
                        <h6 class="card-title mb-1">Balance</h6>
                        <h4 class="mb-0">₹<span id="balance">0.00</span></h4>
                    </div>
                </div>
            </div>
        </div>

        <!-- Chart Container -->
        <div class="card mb-4">
            <div class="card-header">
                <h5>Spending by Category</h5>
            </div>
            <div class="card-body position-relative" style="height: 300px">
                <canvas id="categoryChart"></canvas>
            </div>
        </div>

        <!-- Filter Section -->
        <div class="card mb-4">
            <div class="card-body">
                <form id="filterForm" method="get" action="transactions">
                    <div class="row g-3">
                        <div class="col-md-2">
                            <select name="type" class="form-select">
                                <option value="">All Types</option>
                                <option value="INCOME" ${param.type == 'INCOME' ? 'selected' : ''}>Income</option>
                                <option value="EXPENSE" ${param.type == 'EXPENSE' ? 'selected' : ''}>Expense</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <select name="category" class="form-select">
                                <option value="">All Categories</option>
                                <option value="Salary" ${param.category == 'Salary' ? 'selected' : ''}>Salary</option>
                                <option value="Food" ${param.category == 'Food' ? 'selected' : ''}>Food</option>
                                <option value="Rent" ${param.category == 'Rent' ? 'selected' : ''}>Rent</option>
                                <option value="Transport" ${param.category == 'Transport' ? 'selected' : ''}>Transport</option>
                                <option value="Other" ${param.category == 'Other' ? 'selected' : ''}>Other</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <div class="input-group">
                                <input type="date" name="from" class="form-control" 
                                       value="${param.from}">
                                <span class="input-group-text">to</span>
                                <input type="date" name="to" class="form-control" 
                                       value="${param.to}">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <input type="text" name="search" class="form-control" 
                                   placeholder="Search..." value="${param.search}">
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-outline-primary w-100">
                                <i class="bi bi-funnel"></i> Filter
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Transactions Table -->
        <div class="card">
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Date</th>
                                <th>Title</th>
                                <th>Amount</th>
                                <th>Type</th>
                                <th>Category</th>
                                <th>Notes</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${transactions}" var="t">
                                <tr class="${t.type == 'INCOME' ? 'table-success' : 'table-danger'}">
                                    <td>${t.date}</td>
                                    <td>${t.title}</td>
                                    <td>${t.type == 'INCOME' ? '+' : '-'}₹${t.amount}</td>
                                    <td>
                                        <span class="badge bg-${t.type == 'INCOME' ? 'success' : 'danger'}">
                                            ${t.type}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="badge bg-info">${t.category}</span>
                                    </td>
                                    <td>${t.notes}</td>
                                    <td>
                                        <a href="transactions?action=edit&id=${t.id}" 
                                           class="btn btn-sm btn-outline-primary me-1">
                                            Edit
                                        </a>
                                        <a href="transactions?action=delete&id=${t.id}" 
                                           class="btn btn-sm btn-outline-danger"
                                           onclick="return confirm('Delete this transaction?')">
                                            Delete
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        const currentMonth = new Date().toISOString().slice(0, 7);
        
        fetch('${pageContext.request.contextPath}/analytics?month=' + currentMonth)
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(data => {
                // Update summary cards
                document.getElementById('totalIncome').textContent = data.income.toFixed(2);
                document.getElementById('totalExpense').textContent = data.expense.toFixed(2);
                document.getElementById('balance').textContent = data.balance.toFixed(2);
                
                // Initialize chart
                new Chart(
                    document.getElementById('categoryChart'),
                    {
                        type: 'doughnut',
                        data: {
                            labels: data.expenseCategories.map(item => item.category),
                            datasets: [{
                                data: data.expenseCategories.map(item => item.total),
                                backgroundColor: [
                                    '#FF6384', // Food
                                    '#36A2EB', // Rent
                                    '#FFCE56', // Transport
                                    '#4BC0C0'  // Other
                                ],
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            plugins: {
                                legend: {
                                    position: 'right',
                                }
                            },
                            cutout: '60%'
                        }
                    }
                );
            })
            .catch(error => {
                console.error('Error:', error);
                document.querySelector('#categoryChart').closest('.card-body').innerHTML = 
                    '<div class="alert alert-danger">Failed to load chart data</div>';
            });
    });
    </script>
</body>
</html>