package com.example.houduan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.houduan.common.Result;
import com.example.houduan.entity.StudentReservation;
import com.example.houduan.mapper.StudentReservationMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/studentReservation")
public class StudentReservationController {

    @Resource
    private StudentReservationMapper studentReservationMapper;

    @PostMapping("/studentInfo")
    public Result studentInfo(@RequestParam Integer uid){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        //查询学员是否已经预约
        QueryWrapper<StudentReservation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        queryWrapper.eq("date",df.format(new Date()));

        StudentReservation student=studentReservationMapper.selectOne(queryWrapper);
        return Result.success(student);

    }

    @PostMapping("/cancel")
    public Result cancel(@RequestParam Integer uid){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        //查询学员是否已经预约
        QueryWrapper<StudentReservation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        queryWrapper.eq("date",df.format(new Date()));
        int id=studentReservationMapper.selectOne(queryWrapper).getId();

        studentReservationMapper.deleteById(id);
        return Result.success();

    }


}
