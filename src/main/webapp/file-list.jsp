<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Files</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/file-upload.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-10">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <div class="d-flex justify-content-between align-items-center">
                            <h4 class="mb-0">My Files</h4>
                            <a href="${pageContext.request.contextPath}/files/upload" class="btn btn-light">Upload New</a>
                        </div>
                    </div>
                    <div class="card-body">
                        <!-- Success/Error Messages -->
                        <c:if test="${not empty message}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                ${message}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>

                        <!-- Files Table -->
                        <c:choose>
                            <c:when test="${empty files}">
                                <div class="alert alert-info">No files uploaded yet.</div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>File Name</th>
                                                <th>Type</th>
                                                <th>Size</th>
                                                <th>Uploaded</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${files}" var="file">
                                                <tr>
                                                    <td>${file.originalName}</td>
                                                    <td>${file.fileType}</td>
                                                    <td>${file.fileSize / 1024} KB</td>
                                                    <td>${file.uploadDate}</td>
                                                    <td>
                                                        <div class="btn-group">
                                                            <a href="${pageContext.request.contextPath}/files?action=download&id=${file.fileId}" 
                                                               class="btn btn-sm btn-outline-primary">Download</a>
                                                            <a href="${pageContext.request.contextPath}/files?action=delete&id=${file.fileId}" 
                                                               class="btn btn-sm btn-outline-danger" 
                                                               onclick="return confirm('Are you sure?')">Delete</a>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>