package com.userPortal.controller;

import com.userPortal.dao.FileDAO;
import com.userPortal.model.UserFile;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "FileServlet", urlPatterns = {"/files"})
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class FileServlet extends HttpServlet {
    private FileDAO fileDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        fileDAO = new FileDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String userEmail = (String) request.getSession().getAttribute("email");
        if (userEmail == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Part filePart = request.getPart("file");
            String description = request.getParameter("description");

            // Validate file
            if (filePart == null || filePart.getSize() == 0) {
                throw new ServletException("No file uploaded");
            }

            // Create upload directory if not exists
            String uploadDir = getServletContext().getRealPath("/uploads");
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String storedName = UUID.randomUUID().toString() + "_" + originalName;
            Path filePath = uploadPath.resolve(storedName);

            // Save file to disk
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Save to database
            UserFile file = new UserFile();
            file.setUserEmail(userEmail);
            file.setOriginalName(originalName);
            file.setStoredName(storedName);
            file.setFilePath(filePath.toString());
            file.setFileSize(filePart.getSize());
            file.setFileType(filePart.getContentType());
            file.setDescription(description);

            if (fileDAO.saveFile(file)) {
                request.setAttribute("message", "File uploaded successfully!");
            } else {
                throw new ServletException("Failed to save file metadata");
            }

        } catch (Exception e) {
            request.setAttribute("error", "Error uploading file: " + e.getMessage());
        }

        request.getRequestDispatcher("file-upload.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String userEmail = (String) request.getSession().getAttribute("email");

        if (userEmail == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            if ("download".equals(action)) {
                downloadFile(request, response);
            } else if ("delete".equals(action)) {
                deleteFile(request, response);
            } else {
                listFiles(request, response, userEmail);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listFiles(HttpServletRequest request, HttpServletResponse response, String userEmail) 
            throws ServletException,SQLException, IOException {
        List<UserFile> files = fileDAO.getUserFiles(userEmail);
        request.setAttribute("files", files);
        request.getRequestDispatcher("file-list.jsp").forward(request, response);
    }

    private void downloadFile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException,SQLException, IOException {
        int fileId = Integer.parseInt(request.getParameter("id"));
        UserFile file = fileDAO.getFileById(fileId);

        if (file == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        File downloadFile = new File(file.getFilePath());
        if (!downloadFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(file.getFileType());
        response.setContentLengthLong(file.getFileSize());
        response.setHeader("Content-Disposition", 
            "attachment; filename=\"" + file.getOriginalName() + "\"");

        try (InputStream in = new FileInputStream(downloadFile);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private void deleteFile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException,SQLException, IOException {
        int fileId = Integer.parseInt(request.getParameter("id"));
        UserFile file = fileDAO.getFileById(fileId);

        if (file != null) {
            // Delete from filesystem
            Files.deleteIfExists(Paths.get(file.getFilePath()));
            // Delete from database
            fileDAO.deleteFile(fileId);
            request.setAttribute("message", "File deleted successfully");
        } else {
            request.setAttribute("error", "File not found");
        }

        listFiles(request, response, (String) request.getSession().getAttribute("email"));
    }
}