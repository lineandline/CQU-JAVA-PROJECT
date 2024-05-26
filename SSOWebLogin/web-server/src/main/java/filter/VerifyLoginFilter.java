package filter;

import cache.SelfCache;
import model.TGT;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@WebFilter(urlPatterns = { "/LoginPage"})
public class VerifyLoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //对退出和登录请求进行过滤
        System.out.println("当前请求url为："+request.getRequestURI());
        //服务端系统查询是否已登陆
        String backUrl = request.getParameter("backUrl");
        System.out.println("enter "+backUrl);
        Cookie[] cookieArray = request.getCookies();
        // 得到request中的TGC
        String token = "";
        if (cookieArray != null) {//查询cookie获取到Token
            for (Cookie cookie : cookieArray) {
                System.out.println(cookie.getName()+cookie.getValue());
                if ("CAS-TGC".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if(token == null || token.length() == 0) {
            filterChain.doFilter(request, response);
        } else {
            System.out.println("浏览器存储的token值为"+token);
            // 通过cookie中存储的token查询tgtCache,获取TGT
            if(SelfCache.tgtCache.containsKey(token)){
                // 如果有，表示用户已经在CAS端登录，则拿到TGT
                TGT tgt = SelfCache.tgtCache.get(token);
                if(backUrl!=null){
                    // 如果需要跳转，则使用TGT签发ST，并缓存ST
                    //签发ST并生成票据
                    String st = UUID.randomUUID().toString().replace("-","");
                    SelfCache.stCache.put(st,tgt.user.getUsername());
                    //服务器与当前客户端建立信任
                    tgt.safeUrl.put(st,backUrl);
                    //将当前web添加到cache中
                    Date nowdate = new Date();
                    if(backUrl.indexOf("web1")>=0){
                        SelfCache.web1Cache.put(tgt.user.getUsername(),nowdate);
                    } else if(backUrl.indexOf("web2")>=0){
                        SelfCache.web2Cache.put(tgt.user.getUsername(),nowdate);
                    }
                    //返回服务端
                    response.sendRedirect(backUrl+"/index.jsp?ST="+st);
                    return;
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } else {
                // 如果token为空或没有在tgtCache查询到登录信息，未登录则不返回ST
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    public void destroy() {

    }
}
