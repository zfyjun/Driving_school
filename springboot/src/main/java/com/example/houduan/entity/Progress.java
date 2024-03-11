package com.example.houduan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("progress")
public class Progress {
    private Integer uid;
    private Integer tid;
    private String videostate;
    private String filestate;
    private String teststate;
    private Integer drivestate;
    private Integer type;
}
