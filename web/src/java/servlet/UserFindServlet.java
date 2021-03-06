package servlet;

import dao.UserImpl;
import model.User;
import model.toJson.UserJson;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import java.util.ArrayList;
/**
 * 该接口实现搜索相关用户功能
 *
 *
 */
@WebServlet("/weibo/UserFindServlet")
public class UserFindServlet extends HttpServlet {

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

        String userNameFind = request.getParameter("usernameFind");
        response.setContentType("text/html;charset=UTF-8");


        ArrayList<User> users = new ArrayList<>(userImpl.userFind(userNameFind));
        ArrayList<UserJson> userJsons =  new ArrayList<>();


        for (User user: users ) {
            UserJson userJson =  new UserJson();
           userJson.ToUserJson(user,userJson);
            userJsons.add(userJson);
        }


        JSONArray returnJsons = JSONArray.fromObject(userJsons);

        try {
            response.getWriter().write(returnJsons.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
