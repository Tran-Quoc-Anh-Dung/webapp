package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());


        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        boolean isAuthPage = path.equals("/login.jsp") || path.equals("/register.jsp") || 
                             path.equals("/login") || path.equals("/register") || path.equals("/");
        boolean isStaticResource = path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/");

        if (loggedIn || isAuthPage || isStaticResource) {

            if (loggedIn && (path.equals("/login.jsp") || path.equals("/register.jsp") || path.equals("/"))) {
                resp.sendRedirect(contextPath + "/student?action=list");
            } else if (!loggedIn && path.equals("/")) {

                resp.sendRedirect(contextPath + "/login.jsp");
            } else {
                chain.doFilter(request, response);
            }
        } else {

            req.setAttribute("error", "Vui lòng đăng nhập để truy cập hệ thống!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}
