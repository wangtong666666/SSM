package ky.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

public class SecurityServlet extends HttpServlet implements Filter {
    private static final long serialVersionUID = 1L;


    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();

        HttpSession session = request.getSession();


        //判断获取的路径不为空且不是访问登录页面或执行登录操作时跳转
        if (session.getAttribute("user") != null
                || request.getRequestURI().contains(
                "/login/login.action")
                || request.getRequestURI().contains(
                "/mobilenumber/getnumber.action")
                || request.getRequestURI().contains(
                "/verifycode/verifyCode.action")
                || request.getRequestURI().contains(
                "/login/main.action")
                || request.getRequestURI().contains(
                "/login/updatePwd2.action")
                || request.getRequestURI().contains(
                "/app.jsp")

        ) {

        } else {
            if (request.getRequestURI().contains(".jsp")) {
                //			response.sendRedirect("../../login/login.action?loginpath=app");
            } else {
                //			response.sendRedirect("../login/login.action?loginpath=app");
            }
            //		return;
        }


        arg2.doFilter(arg0, arg1);
        return;
    }

    public void init(FilterConfig arg0) throws ServletException {

    }
}
