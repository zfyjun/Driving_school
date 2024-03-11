package com.example.houduan.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONParser;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.houduan.common.Result;
import com.example.houduan.entity.CoachReservation;
import com.example.houduan.entity.StudentReservation;
import com.example.houduan.mapper.CoachReservationMapper;
import com.example.houduan.mapper.StudentReservationMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coachReservation")
public class CoachReservationController {

    @Resource
    private CoachReservationMapper coachReservationMapper;

    @Resource
    private StudentReservationMapper studentReservationMapper;

    //判断是否存在教练预约时间表
    @PostMapping("/check")
    public Result check(@RequestParam Integer cid){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        //查询条件
        QueryWrapper<CoachReservation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("cid",cid);
        queryWrapper.eq("date",df.format(new Date()));

        //判断是否已有教练时间表
        CoachReservation num= coachReservationMapper.selectOne(queryWrapper);
        if(num!=null){
            return Result.success(num);
        }else {
            //先创建教练时间表，在返回结果
            CoachReservation coachReservation=new CoachReservation();
            coachReservation.setCid(cid);
            coachReservation.setDate(df.format(new Date()));
            coachReservation.setTime1(0);
            coachReservation.setTime2(0);
            coachReservation.setTime3(0);
            coachReservation.setTime4(0);
            coachReservationMapper.insert(coachReservation);
            return Result.success(coachReservationMapper.selectOne(queryWrapper));
        }
    }

    //修改预约人数
    @PostMapping("/reservation")
    public Result reservation(@RequestParam Integer cid,
                              @RequestParam Integer uid,
                              @RequestBody Map map){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        //查询教练
        QueryWrapper<CoachReservation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("cid",cid);
        queryWrapper.eq("date",df.format(new Date()));
        CoachReservation num= coachReservationMapper.selectOne(queryWrapper);


        List list= (List) map.get("time");
        String jsonData= JSON.toJSONString(list);

        ObjectMapper objectMapper = new ObjectMapper();
        int idValue = 0;
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            if (jsonNode.isArray() && jsonNode.size() > 0) {
                JsonNode firstElement = jsonNode.get(0);
                idValue = firstElement.get("id").asInt();

                System.out.println("id:"+idValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        StudentReservation studentReservation=new StudentReservation();
        studentReservation.setUid(uid);
        studentReservation.setCid(cid);
        studentReservation.setDate(df.format(new Date()));

        if (idValue==1){
            num.setTime1(num.getTime1()+1);
            studentReservation.setStartTime("7:00");
            studentReservation.setEndTime("9:00");
            coachReservationMapper.updateById(num);
        } else if (idValue==2) {
            num.setTime2(num.getTime2()+1);
            studentReservation.setStartTime("9:00");
            studentReservation.setEndTime("11:00");
            coachReservationMapper.updateById(num);
        }else if (idValue==3) {
            num.setTime3(num.getTime3()+1);
            studentReservation.setStartTime("14:00");
            studentReservation.setEndTime("16:00");
            coachReservationMapper.updateById(num);
        }else if (idValue==4) {
            num.setTime4(num.getTime4()+1);
            studentReservation.setStartTime("16:00");
            studentReservation.setEndTime("18:00");
            coachReservationMapper.updateById(num);
        }
        studentReservationMapper.insert(studentReservation);
        return Result.success();
    }

    @PostMapping("/cancel")
    public Result cancel(@RequestParam Integer cid,
                         @RequestParam Integer timeId){
        System.out.println(timeId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        CoachReservation coachReservation=new CoachReservation();
        QueryWrapper<CoachReservation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("cid",cid);
        queryWrapper.eq("date",df.format(new Date()));
        coachReservation=coachReservationMapper.selectOne(queryWrapper);
        if(timeId==1){
            coachReservation.setTime1(coachReservation.getTime1()-1);
        } else if (timeId==2) {
            coachReservation.setTime2(coachReservation.getTime2()-1);
        }else if (timeId==3) {
            coachReservation.setTime3(coachReservation.getTime3()-1);
        }else if (timeId==4) {
            coachReservation.setTime4(coachReservation.getTime4()-1);
        }
        coachReservationMapper.updateById(coachReservation);

        return Result.success();
    }

}
