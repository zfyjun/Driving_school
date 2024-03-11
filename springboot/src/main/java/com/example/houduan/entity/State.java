package com.example.houduan.entity;

import lombok.Data;

@Data
public class State {
    private Integer id;//视频或者试题的id
    private String answer;//选择题回答
    private Integer state;//完成情况0为未完成1为完成
    private String note;//笔记
    private Integer type;//单选为0多选为1
}
