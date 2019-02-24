package servlet;

import dao.UserImpl;
import model.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet("/weibo/LoginServelt")
public class LoginServlet extends HttpServlet {
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
        String password = request.getParameter("password");
        //编码转换，支持中文
        try {
            userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //登陆
        if (userImpl.checkLogin(userName, password)) {

            User user = userImpl.userMessage(userName);
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(15 * 60);//设置有效期为15分钟
            //把用户数据保存在session域对象中
            session.setAttribute("user", user);
            user.setPassword(null);
            JSONObject returnUser = JSONObject.fromObject(user);


        }
    }
}