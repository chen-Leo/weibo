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
 * 该接口实现搜索相关微博功能
 *
 *
 */
@WebServlet("/weibo/WeiboFindServlet")
public class WeiboFindServelt extends HttpServlet {

    WeiboContentImpl weiboContentImpl;
    DateFormat time;
    DiscussImpl discussImpl;
    LikeWeiboImpl likeWeibompl;

    public void init() {
        weiboContentImpl = new WeiboContentImpl();
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

        String weiboContentFind = request.getParameter("weiboContentFind");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        ArrayList<WeiboTotalJson> weiboTotalJsons = new ArrayList<>();
        ArrayList<WeiboContent> weiboContents = weiboContentImpl.weiboSearch(weiboContentFind);

        int isLike = -1;
        Boolean isBe = true;
        if (user == null) {
            isLike = 0;
            isBe = false;
        }

        weiboTotalJsonsReturn(user, weiboTotalJsons, weiboContents, isLike, isBe, time, discussImpl, likeWeibompl);


        JSONArray returnJsons = JSONArray.fromObject(weiboTotalJsons);

        try {
            response.getWriter().write(returnJsons.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    //将ArrayList<WeiboContent>处理为封装好的 ArrayList<WeiboTotalJson>
    static void weiboTotalJsonsReturn(User user, ArrayList<WeiboTotalJson> weiboTotalJsons,
                                      ArrayList<WeiboContent> weiboContents,
                                      int isLike, Boolean isBe,
                                      DateFormat time, DiscussImpl discussImpl, LikeWeiboImpl likeWeibompl) {
        for (WeiboContent wbContent : weiboContents) {
            WeiboTotalJson wbTotalJson = new WeiboTotalJson();
            //构建一个待返回的微博json
            wbTotalJson.setWeiboId(wbContent.getWeiboId());
            wbTotalJson.setWeiboPhoto(wbContent.getWeiboPhoto());
            wbTotalJson.setMainContent(wbContent.getMainContent());
            wbTotalJson.setWeiboUserName(wbContent.getWeiboUserName());
            wbTotalJson.setWeiboTime(time.format(wbContent.getWeiboTime()));
            //调用DiscussImpl的mainToString方法将 ArrayList<Discuss> 中的评论主要内容(MainDisscuss)转化为String[]格式
            wbTotalJson.setWeiboContents(discussImpl.mainToString(discussImpl.discussAll(wbContent.getWeiboId())));
            //判断用户是否点过赞
            if (isBe) {
                isLike = likeWeibompl.findIsLike(user.getName(), wbContent.getWeiboId());
            }
            wbTotalJson.setIsLike(isLike);
            weiboTotalJsons.add(wbTotalJson);
        }
    }
}
