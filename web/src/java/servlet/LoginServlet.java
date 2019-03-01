package servlet;

import dao.UserImpl;
import model.Encrypt;
import model.User;
import model.toJson.UserJson;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 该接口实现登陆功能
 *
 *
 */


@WebServlet("/weibo/LoginServlet")
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
        response.setContentType("text/html;charset=UTF-8");



        try {

            if(userName==null|| password==null){
                response.getWriter().write("请勿空输入");
                return;
            }
            if (!Encrypt.StringFilter(password) ) {
                response.getWriter().write("非法输入");
                return;
            }
            if (!Encrypt.isRight(userName)  ) {
                response.getWriter().write("非法输入");
                return;
            }
            //编码转换，支持中文
            userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");

            if (userImpl.checkLogin(userName, password)) {
                User user = userImpl.userMessage(userName);
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(15 * 60);//设置有效期为15分钟
                //把用户数据保存在session域对象中
                session.setAttribute("user", user);

                //构建一个返回的用户json格式
                UserJson userJson = new UserJson();
                userJson.ToUserJson(user,userJson);
                JSONObject returnUser = JSONObject.fromObject(userJson);
                response.getWriter().write(returnUser.toString());

            } else response.getWriter().write("密码错误或用户名不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}