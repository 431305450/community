package life.dingdang.community.community.controller;

import life.dingdang.community.community.dto.AccessTokenDTO;
import life.dingdang.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
    @RequestParam(name="state") String state //code state 参数的接收
                 ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("cfb462b9a5b940592233");
        accessTokenDTO.setClient_secret("0dd9c996ad22ee28f6948345ff2de6e8d4201684");
        githubProvider.getAccessToken(accessTokenDTO);
        return "index";
    }
}
