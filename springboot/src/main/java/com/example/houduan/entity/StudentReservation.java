package com.example.houduan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("student_reservation")
public class StudentReservation {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer cid;
    private Integer uid;


    private String startTime;

    private String endTime;

    private String date;
}
