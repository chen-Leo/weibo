package servlet;

import model.User;
import dao.UserImpl;
import org.apache.commons.io.IOExceptionWithCause;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@WebServlet("/weibo/AddUserServlet")
public class AddUserServlet extends HttpServlet {

    UserImpl userImpl;

    public void init() {
        userImpl = new UserImpl();
    }

    @Override
    public void doGet(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        doPost(requset, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String userName = request.getParameter("username");
        String password = request.getParameter("password1");
        response.setContentType("text/html;charset=UTF-8");

        try {
            userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        User user = new User(null, userName, password, 0, 0, 0);

        try {
            if (!userImpl.checkName(userName)) {
                response.getWriter().write("用户名已经被使用");
            } else {
                if (userImpl.add(user)) {
                    response.getWriter().write("注册成功");
                } else {
                    response.getWriter().write("注册失败");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}