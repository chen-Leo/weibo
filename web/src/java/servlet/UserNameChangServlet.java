package servlet;

import dao.LikeUserImpl;
import dao.LikeWeiboImpl;
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
 * 该接口实现用户姓名（昵称）改名功能
 *
 *
 */

@WebServlet("/weibo/UserNameChangServlet")
public class UserNameChangServlet extends HttpServlet {

    UserImpl userImpl;
    LikeUserImpl likeUserImpl;
    LikeWeiboImpl likeWeiboImpl;
    public void init() {
        userImpl = new UserImpl();
        likeUserImpl = new LikeUserImpl();
        likeWeiboImpl = new LikeWeiboImpl();
    }


    @Override
    public void doGet(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        doPost(requset, response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String userNewName = request.getParameter("userNewName");
        response.setContentType("text/html;charset=UTF-8");
        boolean flag = true;


        if(userNewName == null ){
                response.getWriter().write("请勿空输入");
                return;
        }
        if (AddAttentionsServlet.isUserNULL(response, user)) return;
        if (!userImpl.checkName(userNewName)) { response.getWriter().write("用户名重名"); return;}


        if (likeUserImpl.isUserExist(user.getName())) {
            if (!likeUserImpl.userNameChange(userNewName, user.getName())) flag = false;
            }
            if (likeUserImpl.isLikeUserExist(user.getName())) {
                if (!likeUserImpl.likeUserNameChange(userNewName, user.getName())) flag = false;
            }
            if (likeWeiboImpl.isUserExist(user.getName())) {
                if (!likeWeiboImpl.userNameChange(userNewName, user.getName())) flag = false;
            }

            if (!flag) {
                response.getWriter().write("修改失败");
                return;
            }

            if (userImpl.userNameChange(userNewName, user.getName())) {
                user.setName(userNewName);
                session.setMaxInactiveInterval(15 * 60);//设置有效期为15分钟
                //把用户数据保存在session域对象中
                session.setAttribute("user", user);
                UserJson userJson = new UserJson();
                userJson.ToUserJson(user, userJson);
                JSONObject returnUser = JSONObject.fromObject(userJson);
                response.getWriter().write(returnUser.toString());
            } else response.getWriter().write("修改失败");
    }
}

