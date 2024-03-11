package com.example.houduan.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.houduan.common.Result;
import com.example.houduan.common.Verification;
import com.example.houduan.entity.Coach;
import com.example.houduan.entity.Code;
import com.example.houduan.entity.User;
import com.example.houduan.mapper.CoachMapper;
import com.example.houduan.mapper.CodeMapper;
import com.example.houduan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/Sms")
public class CodeController {
    private String code;
    private String phonenumber;
    @Resource
    @Autowired
    private CodeMapper codeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CoachMapper coachMapper;

    @PostMapping("loginbyphone")
    public Result loginbyphone(@RequestBody Map map){//验证
        String phone=(String) map.get("phone");
        String codenumber=(String)map.get("code");
        Code code=codeMapper.selectOne(Wrappers.<Code>lambdaQuery().eq(Code::getPhone,phone));
        if(codenumber.equals(code.getCode())&&code.getType()==1){//学生
            User user=new User();
            user=userMapper.selectById(code.getUid());
            return Result.success(user);
        }
        else if(codenumber.equals(code.getCode())&&code.getType()==2){//教练
            Coach coach=new Coach();
            coach=coachMapper.selectById(code.getUid());
            return Result.success(coach);
        }
        return Result.error("500","验证码错误！");
    }
    @PostMapping("setphone")
    public Result setphone(@RequestBody Map map){//绑定手机并发送验证码
        String phone=(String) map.get("phone");
        Verification verification=new Verification(phone);
        phonenumber=phone;
        code=verification.makecode();//获取验证码
        verification.sms();//发送验证码
        if(code!=null){
            System.out.println(code);
        }
        else {
            return Result.error("500","发送验证码失败");
        }
        return Result.success();
    }

    @PostMapping("comcode")
    public Result comcode(@RequestBody Map map){//绑定手机并验证验证码
        String phone=(String) map.get("phone");
        String codenumber=(String)map.get("code");
        String idnumber=(String)map.get("idnumber");
        if(phone.equals(phonenumber)&&codenumber.equals(code)){
            User user=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getIdnumber,idnumber));
            Integer uid=user.getId();
            user.setPhone(phone);
            userMapper.updateById(user);
            Code code1=new Code();
            code1.setUid(uid);
            code1.setPhone(phone);
            codeMapper.insert(code1);
            phonenumber="";
            code="";
            return Result.success();
        }
        return Result.error("500","验证码错误！");
    }

    @PostMapping("sendsms")
    public Result sentsms(@RequestBody Map map){//设置并发送验证码
        String phone=(String) map.get("phone");
        Verification verification=new Verification(phone);
        Code code=getIdByPhoen(phone);
        if(code!=null){
            String p_code=verification.makecode();//获取验证码
            code.setCode(p_code);//设置验证码
            codeMapper.updateById(code);
            verification.sms();//发送验证码
            System.out.println(code.getCode());
        }
        else {
            return Result.error("500","当前电话号码未注册或绑定账户");
        }
        return Result.success();
    }
    //根据电话号码获取验证码表
    public Code getIdByPhoen(String phone){
        Integer id=0;
        //是学生的情况
        Code code=codeMapper.selectOne(Wrappers.<Code>lambdaQuery().eq(Code::getPhone,phone));
        if(code!=null){
            return code;
        }
        return null;
    }
}
