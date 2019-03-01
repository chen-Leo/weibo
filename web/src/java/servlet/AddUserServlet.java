package servlet;

import model.Encrypt;
import model.User;
import dao.UserImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
/**
 * 该接口用户注册功能
 *
 *
 */

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
        String password = request.getParameter("password");
        response.setContentType("text/html;charset=UTF-8");


        try {

            if( userName==null  || password == null){
                response.getWriter().write("请勿空输入");
                return;
            }

            if ( !Encrypt.StringFilter(password) ) {
                response.getWriter().write("请勿输入非法字符，密码必须由数字字母同时组成至少8位");
                return;
            }
            if ( !Encrypt.isRight(userName)  ) {
                response.getWriter().write("请仅用中文，英文,数字命名 不要输入非法字符");
                return;
            }

            userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = new User(null, userName, password, 0, 0, 0, null);

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