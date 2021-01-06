package ca.allaxis.config;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pierrefroy
 */
@WebServlet(name = "ConfigServlet", urlPatterns = {"/ConfigServlet"})
public class ConfigServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ConfigServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConfigServlet at " + request.getContextPath() + "</h1>");

            out.println("<h2>Environment</h2>");
            out.println("<ul>");
            out.println("<li>JAVA_HOME: " + System.getenv("JAVA_HOME") + "</li>");
            out.println("<li>HOMEPATH: " + System.getenv("HOMEPATH") + "</li>");
            out.println("<li>USERPROFILE: " + System.getenv("USERPROFILE") + "</li>");
            out.println("<li>TMP: " + System.getenv("TMP") + "</li>");
            out.println("<li>ENVIRONMENT: " + System.getenv("ENVIRONMENT") + "</li>");
            out.println("</ul>");
            out.println("<h2>Properties</h2>");
            out.println("<ul>");
            out.println("<li>catalina.base: " + System.getProperty("catalina.base") + "</li>");
            out.println("<li>user.dir: " + System.getProperty("user.dir") + "</li>");
            out.println("<li>applicationName: " + System.getProperty("applicationName") + "</li>");
            out.println("<li>environment: " + System.getProperty("environment") + "</li>");
            out.println("<li>jndiName: " + System.getProperty("jndiName") + "</li>");
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
