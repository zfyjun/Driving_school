package com.example.houduan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.houduan.common.Result;
import com.example.houduan.entity.*;
import com.example.houduan.mapper.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/User")
public class UserController {
    @Resource

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private XztMapper xztMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ProgressMapper progressMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private ExamPaperMapper examPaperMapper;
    @PostMapping ("/login")
    public Result login(@RequestBody Map map){//登录
        String idnumber=(String)map.get("idnumber") ;
        String password=(String)map.get("password") ;
        System.out.println(idnumber+" "+password);
        User user=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getIdnumber,idnumber).eq(User::getPassword,password));
        if(user==null){
            return Result.error("500","账户或密码错误");
        }
        return Result.success(user);
    }
    @PostMapping("/register")//注册
    public Result register(@RequestBody Map user){//前端传后端
        User user1=new User();
        User res=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getIdnumber,user.get("idcard")));
        if(res!=null){
            return Result.error("-1","该身份证号码已被注册！");
        }
        user1.setAge(Integer.parseInt((String) user.get("age")));
        user1.setName((String) user.get("name"));
        user1.setSex((String) user.get("sex"));
        user1.setIdnumber((String) user.get("idcard"));
        user1.setPassword((String) user.get("password"));
        userMapper.insert(user1);
        //后续注册
        String states1=gettask(1,1);//试题
        String states2=gettask(1,2);//视频
        String states3=gettask(2,1);//试题
        String states4=getexam(1);//考试科目一
        String states5=getexam(2);//考试科目二
        //
        Progress progress=new Progress();
        progress.setUid(user1.getId());
        progress.setTid(1);
        progress.setTeststate(states1);
        progress.setVideostate(states2);
        progressMapper.insert(progress);
        Progress progress1=new Progress();
        progress1.setUid(user1.getId());
        progress1.setTid(2);
        progress1.setTeststate(states3);
        progressMapper.insert(progress1);
        //
        Exam exam=new Exam();
        exam.setId(1);
        exam.setUid(user1.getId());
        exam.setState(states4);
        examMapper.insert(exam);
        Exam exam1=new Exam();
        exam1.setId(2);
        exam1.setUid(user1.getId());
        exam1.setState(states5);
        examMapper.insert(exam1);
        return Result.success();
    }
    @PostMapping("/checkphone")//检查电话号码
    public Result register(@RequestBody String phone){//前端传后端
        User user=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone,phone));
        if(user==null){
            return Result.success();
        }
        return Result.error("500","该电话号码已被注册");
    }


    public String gettask(Integer tid,Integer flag){
        List<State> states=new ArrayList<>();
        Task task=taskMapper.selectOne(Wrappers.<Task>lambdaQuery().eq(Task::getId,tid));
        JSONArray objects = new JSONArray();
        if(flag==1){//试题id
            objects=JSON.parseArray(task.getTest());
        }
        else if(flag==2){//视频id
            objects=JSON.parseArray(task.getVideo());
        }
        List<Map> list=objects.toJavaList(Map.class);
        for(int i=0;i<list.size();i++){
            State state=new State();
            state.setState(0);
            state.setId((Integer) list.get(i).get("id"));
            if(flag==1){
                Xzt xzt=xztMapper.selectById((Integer)list.get(i).get("id"));
                if(xzt.getType().equals("单选题")){
                    state.setType(0);
                }
                else{
                    state.setType(1);
                }
            }
            state.setNote("");
            state.setAnswer("");
            states.add(state);
        }
        String state= JSON.toJSONString(states);
        return state;
    }

    public String getexam(Integer tid){
        List<State> states=new ArrayList<>();
        Exam_paper task=examPaperMapper.selectOne(Wrappers.<Exam_paper>lambdaQuery().eq(Exam_paper::getId,tid));
        JSONArray objects=JSON.parseArray(task.getXid());
        List<Map> list=objects.toJavaList(Map.class);
        for(int i=0;i<list.size();i++){
            State state=new State();
            state.setState(0);
            state.setId((Integer) list.get(i).get("id"));
                Xzt xzt=xztMapper.selectById((Integer)list.get(i).get("id"));
                if(xzt.getType().equals("单选题")){
                    state.setType(0);
                }
                else{
                    state.setType(1);
                }
            state.setNote("");
            state.setAnswer("");
            states.add(state);
        }
        String state= JSON.toJSONString(states);
        return state;
    }
}
