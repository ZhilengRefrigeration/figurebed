package top.shenyuge.figurebed.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.shenyuge.figurebed.bean.Master;
import top.shenyuge.figurebed.service.MasterService;
import top.shenyuge.figurebed.util.Result;

/**
 * @author 制冷
 * @date 2024/3/28 19:56
 * @description Master
 */
@RestController
@RequestMapping("/master")
public class MasterController {
    private MasterService service;
    private Gson gson;

    @PostMapping("/login")
    public Result<Object> login(@RequestBody Master master){
        LambdaQueryWrapper<Master> masterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        masterLambdaQueryWrapper
                .eq(null != master.getUserName(), Master::getUserName, master.getUserName())
                .eq(null != master.getPassWord(), Master::getPassWord, master.getPassWord());
        Master one = service.getOne(masterLambdaQueryWrapper);
        if(null == one){
            return Result.error(500,"用户名或密码错误");
        }else{
            StpUtil.login(one.getId());
           return Result.success(StpUtil.getTokenInfo());
        }
    }

    @PostMapping("/loginOut")
    public Result<String> loginOut(){
        StpUtil.logout();
        return Result.success("成功退出登录。");
    }

    @Autowired
    public MasterController(MasterService service, Gson gson) {
        this.service = service;
        this.gson = gson;
    }
}
