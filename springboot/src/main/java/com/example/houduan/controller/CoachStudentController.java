package com.example.houduan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.houduan.common.Result;
import com.example.houduan.entity.CoachStudent;
import com.example.houduan.entity.User;
import com.example.houduan.mapper.CoachStudentMapper;
import com.example.houduan.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coachStudent")
public class CoachStudentController {
    @Resource
    private CoachStudentMapper coachStudentMapper;

    @Resource
    private UserMapper userMapper;

    @PostMapping("/save")
    public Result save(@RequestParam Integer cid,
                       @RequestParam Integer uid){
        CoachStudent coachStudent=new CoachStudent();
        coachStudent.setCid(cid);
        coachStudent.setUid(uid);
        coachStudentMapper.insert(coachStudent);
        return Result.success();
    }

    @PostMapping("/num")
    public Result findnum(@RequestParam Integer cid){
        QueryWrapper<CoachStudent> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("cid",cid);
        return Result.success(coachStudentMapper.selectList(queryWrapper));
    }

    //查询教练对应的所有学员的信息
    @PostMapping("/findstudent")
    public Result findStudent(@RequestParam Integer cid) {
        QueryWrapper<CoachStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("cid",cid);

        List<CoachStudent> list =coachStudentMapper.selectList(queryWrapper);
        System.out.println(list);

        QueryWrapper<User> queryWrapper1=new QueryWrapper<>();

        List<User> list2 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            int uid = list.get(i).getUid();
            queryWrapper1.like("id",uid);

            User user = userMapper.selectById(uid);
            System.out.println(user);
            list2.add(user);
        }
        return Result.success(list2);
    }

    @PostMapping("/setClass")
    public Result setClass(@RequestParam Integer uid,
                           @RequestBody Map map){
        System.out.println(map);
        QueryWrapper<CoachStudent> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        CoachStudent coachStudent = coachStudentMapper.selectOne(queryWrapper);
        String s= (String) map.get("subject");
        System.out.println(s);
        if(s.equals("科目二")){
            Integer sum=0;
            sum=(Integer) map.get("time");
            System.out.println(sum);
            coachStudent.setSubject2(coachStudent.getSubject2()+(Integer) map.get("time"));
            if(coachStudent.getSubject2()>57600000){
                coachStudent.setCl2(1);
            }
        }else if(s.equals("科目三")){
            coachStudent.setSubject3(coachStudent.getSubject3()+(Integer) map.get("time"));
            if (coachStudent.getSubject3()>36000000){
                coachStudent.setCl3(1);
            }
        }
        coachStudentMapper.updateById(coachStudent);

        return Result.success();
    }

    @PostMapping("/findStudentInfo")
    public Result findStudentInfo(@RequestParam Integer uid){
        QueryWrapper<CoachStudent> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        return Result.success(coachStudentMapper.selectOne(queryWrapper));
    }
}
