package com.example.houduan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("coach_student")
public class CoachStudent {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer cid;
    private Integer uid;
    private Integer subject2;
    private Integer subject3;
    private Integer cl2;
    private Integer cl3;

}
