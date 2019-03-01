package servlet;


import dao.UserImpl;
import model.User;
import model.toJson.UserJson;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * 该接口实现个人简介修改功能
 *
 *
 */


@WebServlet("/weibo/perStatementUpdateServlet")
public class PerStatementUpdateServlet extends HttpServlet {

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        response.setContentType("text/html;charset=UTF-8");
        String perStatement = request.getParameter("perStatement");

        response.setContentType("text/html;charset=UTF-8");

        if (AddAttentionsServlet.isUserNULL(response, user)) return;
        if(perStatement==null) return;

        if (userImpl.perStatementUpdate(user.getName(), perStatement)) {

            user.setPersonalStatement(perStatement);
            session.setMaxInactiveInterval(15 * 60);//设置有效期为15分钟
            //把用户数据保存在session域对象中
            session.setAttribute("user", user);
            UserJson userJson = new UserJson();
            userJson.ToUserJson(user, userJson);
            JSONObject returnUser = JSONObject.fromObject(userJson);

            try {
                response.getWriter().write(returnUser.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.getWriter().write("更改失败");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
