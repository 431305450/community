package life.dingdang.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
@Controller //所述@GetMapping注释可以确保HTTP GET请求到/greeting被映射到greeting()方法。
public class IndexController {
    @GetMapping("/")
    public String index() { //@RequestParam将查询字符串参数的值绑定name到方法的name参数greeting()。该查询字符串参数不是required。如果请求中不存在defaultValue，World则使用of 。name参数的值将添加到Model对象，最终使视图模板可以访问它。
       // modle.addAttribute("name",name);
        return "index";
    }
}
