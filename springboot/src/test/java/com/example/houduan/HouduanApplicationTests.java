package com.example.houduan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.houduan.common.Result;
import com.example.houduan.entity.*;
import com.example.houduan.mapper.*;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MapperScan("com.example.houduan.mapper")
@SpringBootTest
class HouduanApplicationTests {
	@Resource
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private XztMapper xztMapper;
	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private ProgressMapper progressMapper;

	@Autowired
	private VideoMapper videoMapper;
	@Autowired
	private ExamMapper examMapper;
	@Autowired
	private ExamPaperMapper examPaperMapper;
	@Test
	void contextLoads() {
		Exam_paper exam_paper=examPaperMapper.selectById(2);
		LocalDateTime now=LocalDateTime.now();
		if(now.isAfter(exam_paper.getBtime())&&now.isBefore(exam_paper.getEtime())){
			System.out.println("在此之后,在那之前");
		}
	}

}
@Data
class Xid{
	private Integer id;
}
@Data
class codess{
	private Integer code;
}
