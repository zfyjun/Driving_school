package com.example.houduan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("video")
public class Video {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String size;
    private String url;
    private String md5;
    private String isDelete;
}
