package servlet;

import dao.DiscussImpl;
import dao.LikeWeiboImpl;
import dao.WeiboContentImpl;
import model.User;
import model.WeiboContent;
import model.toJson.WeiboTotalJson;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/**
 * 该接口实现返回某一用户的所有微博功能
 *
 *
 */

@WebServlet("/weibo/userWeiboServlet")
public class UserWeiboServlet extends HttpServlet {

    WeiboContentImpl weiboContentImpl;
    DateFormat time;
    DiscussImpl discussImpl;
    LikeWeiboImpl likeWeibompl;
    public void init(){
        weiboContentImpl =  new WeiboContentImpl();
        time = new SimpleDateFormat("yyyy-MM-dd");
        discussImpl = new DiscussImpl();
        likeWeibompl = new LikeWeiboImpl();
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

        String userName = request.getParameter("username");
        if (userName==null) return;

        ArrayList<WeiboTotalJson> weiboTotalJsons = new ArrayList<>();
        ArrayList<WeiboContent> weiboContents = weiboContentImpl.weiboAll(userName);

        int isLike = -1;
        Boolean isBe = true;
        if (user == null) {
            isLike = 0;
            isBe = false;
        }

        WeiboFindServelt.weiboTotalJsonsReturn(user, weiboTotalJsons, weiboContents, isLike, isBe, time, discussImpl, likeWeibompl);

        JSONArray returnJsons = JSONArray.fromObject(weiboTotalJsons);

        try {
            response.getWriter().write(returnJsons.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    }




