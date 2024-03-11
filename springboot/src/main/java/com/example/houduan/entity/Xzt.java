package com.example.houduan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("xzt")
public class Xzt {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String question;
    private String a;
    private String b;
    private String c;
    private String d;
    private String answer;
    private String type;
    private String analysis;
    private String subject;
}
