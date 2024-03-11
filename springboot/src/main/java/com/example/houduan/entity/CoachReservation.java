package com.example.houduan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName("coach_reservation")
public class CoachReservation {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer cid;

    private Integer time1;
    private Integer time2;
    private Integer time3;
    private Integer time4;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String date;
}
