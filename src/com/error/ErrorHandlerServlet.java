package com.error;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

public class ErrorHandlerServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        handleException(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        handleException(request, response);
    }
    
    private void handleException(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get error information from request attributes
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        
        if (servletName == null) {
            servletName = "Unknown";
        }
        if (requestUri == null) {
            requestUri = "Unknown";
        }
        
        // HTML Error Page
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Error Page</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 50px; }");
        out.println(".error-container { background-color: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
        out.println("h1 { color: #d9534f; }");
        out.println(".error-details { background-color: #f8d7da; padding: 15px; border-left: 4px solid #d9534f; margin-top: 20px; }");
        out.println(".error-info { margin: 10px 0; }");
        out.println(".back-link { display: inline-block; margin-top: 20px; color: #007bff; text-decoration: none; }");
        out.println(".back-link:hover { text-decoration: underline; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='error-container'>");
        out.println("<h1>⚠ Application Error</h1>");
        out.println("<p>We're sorry, but something went wrong while processing your request.</p>");
        
        out.println("<div class='error-details'>");
        out.println("<h3>Error Details:</h3>");
        
        if (statusCode != null) {
            out.println("<div class='error-info'><strong>Status Code:</strong> " + statusCode + "</div>");
        }
        
        out.println("<div class='error-info'><strong>Servlet Name:</strong> " + servletName + "</div>");
        out.println("<div class='error-info'><strong>Request URI:</strong> " + requestUri + "</div>");
        out.println("<div class='error-info'><strong>Timestamp:</strong> " + new Date() + "</div>");
        
        if (throwable != null) {
            out.println("<div class='error-info'><strong>Exception Type:</strong> " + throwable.getClass().getName() + "</div>");
            out.println("<div class='error-info'><strong>Exception Message:</strong> " + throwable.getMessage() + "</div>");
            
            // Print stack trace for debugging
            out.println("<details>");
            out.println("<summary><strong>Stack Trace (Click to expand)</strong></summary>");
            out.println("<pre>");
            throwable.printStackTrace(out);
            out.println("</pre>");
            out.println("</details>");
        }
        
        out.println("</div>");
        
        out.println("<a href='login.html' class='back-link'>← Back to Login</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        
        out.close();
    }
}
