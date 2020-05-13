package life.dingdang.community.community.controller;

import com.sun.net.httpserver.HttpServer;
import life.dingdang.community.community.dto.AccessTokenDTO;
import life.dingdang.community.community.dto.GithubUser;
import life.dingdang.community.community.mapper.UserMapper;
import life.dingdang.community.community.model.User;
import life.dingdang.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

   /* @Value("$(github.client.id)")
    private  String clintId;
    @Value("$(github.client.secret)")
    private  String clintSecret;
    @Value("$(github.redirect.uri)")
    private  String redirectUri;*/
   @Autowired
   private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state") String state, //code state 参数的接收
                           HttpServletRequest request
                 ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("cfb462b9a5b940592233");
        accessTokenDTO.setClient_secret("0dd9c996ad22ee28f6948345ff2de6e8d4201684");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
       if(githubUser !=null){
           User user = new User();
           user.setToken(UUID.randomUUID().toString());
           user.setName(githubUser.getName());
           user.setAccountId(String.valueOf((githubUser.getId())));
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
           request.getSession().setAttribute("user",githubUser);
           return "redirect:index";
           //登录成功,写cookie和session
       }else{
           return "redirect:index";
           //登录失败，重新登录
       }
    }
}
