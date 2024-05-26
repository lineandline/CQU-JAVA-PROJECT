package servlet;

import cache.SelfCache;
import dao.DBOperate;
import model.TGT;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取输入信息
        String username,password,backUrl;
        username = req.getParameter("username");
        password = req.getParameter("password");
        backUrl = req.getParameter("backUrl");
        //判断输入信息是否有效
        DBOperate dbOperate = new DBOperate();
        User user = dbOperate.JudgeUser(username,password);

        //查询为空则判断用户输入错误
        if(user == null) {
            //设置输入错误弹窗内容并记录url
            req.setAttribute("wrongInfo","账号或密码输入错误，请重新输入！！！");
            req.setAttribute("backUrl",backUrl);
            req.getRequestDispatcher("/index.jsp").forward(req,resp);
            return;
        } else {
            req.getSession().setAttribute("user",user.getUsername());
            System.out.println(req.getSession().getId());
            //生成随机Token，处理生成32位随机连续字符串
            String Token = UUID.randomUUID().toString().replace("-", "");
            //生成cookie
            Cookie cookie = new Cookie("CAS-TGC",Token);
            cookie.setPath("/");
            resp.addCookie(cookie);

            //签发ST并生成票据
            String st = UUID.randomUUID().toString().replace("-","");
            SelfCache.stCache.put(st,user.getUsername());
            //生成TGT并缓存用户信息
            TGT tgt = new TGT();
            tgt.user = user;
            tgt.safeUrl.put(st,backUrl);
            SelfCache.tgtCache.put(Token,tgt);
            //装入session中
            req.getSession().setAttribute("TGC",Token);
            req.getSession().setAttribute("TGT",tgt);
            System.out.println((String)req.getSession().getAttribute("user"));
            if(backUrl!=null &&backUrl.length()!=0) {//判断从web1或web2跳转而来
                System.out.println(backUrl+"?ST="+st);
                Date nowdate = new Date();
                String jsonstr=null;
                if(backUrl.indexOf("web1")>=0){
                    SelfCache.web1Cache.put(username,nowdate);
                } else if(backUrl.indexOf("web2")>=0){
                    SelfCache.web2Cache.put(username,nowdate);
                } else {
                    System.out.println("未获取到当前用户系统类别");
                }
                resp.sendRedirect(backUrl+"/index.jsp?ST="+st);
            } else {//否则返回主界面（还未添加，暂时设置为登录界面）
                resp.sendRedirect("/LoginServlet");
                System.out.println("返回server主界面");
            }
        }
    }
}
