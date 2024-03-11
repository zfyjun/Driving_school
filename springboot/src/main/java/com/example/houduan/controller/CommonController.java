package com.example.houduan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.houduan.common.Result;
import com.example.houduan.entity.*;
import com.example.houduan.mapper.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Common")
public class CommonController {
    @Resource
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private XztMapper xztMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ProgressMapper progressMapper;

    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private ExamPaperMapper examPaperMapper;

    @PostMapping("getxzt")
    public Result getxzt(@RequestBody Map map){//获取选择题（根据id获取）
        Integer id=(Integer) map.get("id");
        Xzt xzt =xztMapper.selectById(id);
        if(xzt!=null){
            return Result.success(xzt);
        }
        return Result.error("500","当前题目不存在！（可能被管理员删除或者编号改变）");
    }
    @PostMapping("gettask")
    public Result gettask(@RequestBody Map map){//获取科目任务(包括视频和试题)
        String subject=(String) map.get("subject");
        Task tasks=taskMapper.selectOne(Wrappers.<Task>lambdaQuery().eq(Task::getSubject,subject));
        if(tasks!=null){
            return Result.success(tasks);
        }
        return Result.error("500","获取任务表失败！");
    }

    @PostMapping("getexam")
    public Result getexam(@RequestBody Map map){//获取科目任务(包括视频和试题)
        String subject=(String) map.get("subject");
        Exam_paper exam_paper=examPaperMapper.selectOne(Wrappers.<Exam_paper>lambdaQuery().eq(Exam_paper::getSubject,subject));
        if(exam_paper!=null){
            return Result.success(exam_paper);
        }
        return Result.error("500","获取任务表失败！");
    }

    @PostMapping("getstate")
    public Result getstate(@RequestBody Map map){//获取科目任务完成情况（包括视频和题目）
        Integer uid=(Integer) map.get("uid");
        Integer tid=(Integer) map.get("tid");
        Integer type=(Integer) map.get("type");
        Progress progress=progressMapper.selectOne(Wrappers.<Progress>lambdaQuery().eq(Progress::getUid,uid).eq(Progress::getTid,tid));
        if(type==1){
            JSONArray objects = JSON.parseArray(progress.getTeststate());
            List<State> list=objects.toJavaList(State.class);
            if(list!=null){
                return Result.success(list);
            }
        }
        else  {
            JSONArray objects = JSON.parseArray(progress.getVideostate());
            List<State> list=objects.toJavaList(State.class);
            if(list!=null){
                return Result.success(list);
            }
        }
        return Result.error("500","获取历史状态失败！");
    }

    @PostMapping("getexamstate")
    public Result getexamstate(@RequestBody Map map){//获取考试任务完成情况
        Integer uid=(Integer) map.get("uid");
        Integer tid=(Integer) map.get("tid");
        Exam exam=examMapper.selectOne(Wrappers.<Exam>lambdaQuery().eq(Exam::getUid,uid).eq(Exam::getId,tid));
        JSONArray objects = JSON.parseArray(exam.getState());
        List<State> list=objects.toJavaList(State.class);
        if(list!=null){
            return Result.success(list);
        }
        return Result.error("500","获取考试状态失败！");
    }

    @PostMapping("getexamstates")
    public Result getexamstates(@RequestBody Map map){//获取考试任务完成情况
        Integer uid=(Integer) map.get("uid");
        Integer tid=(Integer) map.get("tid");
        Exam exam=examMapper.selectOne(Wrappers.<Exam>lambdaQuery().eq(Exam::getUid,uid).eq(Exam::getId,tid));
        if(exam!=null){
            Integer flag=exam.getType();
            return Result.success(flag);
        }
        return Result.error("500","获取考试状态失败！");
    }

    @PostMapping("getsubjectstate")
    public Result getsubjectstate(@RequestBody Map map){//获取科目任务完成状态(包括视频和试题)
        Integer uid=(Integer) map.get("uid");
        Integer tid=(Integer) map.get("tid");
        Progress progress=progressMapper.selectOne(Wrappers.<Progress>lambdaQuery().eq(Progress::getUid,uid).eq(Progress::getTid,tid));
        JSONArray objects = JSON.parseArray(progress.getTeststate());
        List<State> list=objects.toJavaList(State.class);
        if(list!=null){
            return Result.success(list);
        }
        return Result.error("500","获取历史状态失败！");
    }

    @PostMapping("saveandcommit")
    public Result saveandcommit(@RequestBody Map map){//保存和提交（包括视频和任务）
        Integer uid=(Integer) map.get("uid");
        Integer tid=(Integer) map.get("tid");
        Integer type=(Integer)map.get("type");
        List list=(ArrayList)map.get("state");
        String state= JSON.toJSONString(list);
        Progress progress=progressMapper.selectOne(Wrappers.<Progress>lambdaQuery().eq(Progress::getUid,uid).eq(Progress::getTid,tid));
        if(type==1){//试题
            progress.setTeststate(state);
        }
        else if(type==2) {//视频
            progress.setVideostate(state);
        }
        Integer flag=progressMapper.update(progress,Wrappers.<Progress>lambdaQuery().eq(Progress::getUid,uid).eq(Progress::getTid,tid));
        if(flag==1){
            return Result.success();
        }
        return Result.error("500","数据更新失败！");
    }

    @PostMapping("saveandcommit2")
    public Result saveandcommit2(@RequestBody Map map){//提交试卷）
        Integer uid=(Integer) map.get("uid");
        Integer tid=(Integer) map.get("tid");
        List list=(ArrayList)map.get("state");
        String state= JSON.toJSONString(list);
        Exam exam=examMapper.selectOne(Wrappers.<Exam>lambdaQuery().eq(Exam::getUid,uid).eq(Exam::getId,tid));
        exam.setState(state);
        exam.setType(1);
        Integer flag=examMapper.update(exam,Wrappers.<Exam>lambdaQuery().eq(Exam::getUid,uid).eq(Exam::getId,tid));
        if(flag==1){
            setcode(uid,tid,state);//设置分数
            return Result.success();

        }
        return Result.error("500","提交失败！");
    }
    @PostMapping("getvideo")
    public Result getvideo(@RequestBody Map map){//获取科目视频（根据id）
        Integer id=(Integer) map.get("id");
        Video video =videoMapper.selectById(id);
        if(video!=null){
            return Result.success(video);
        }
        return Result.error("500","当前题目不存在！（可能被管理员删除或者编号改变）");
    }

    @PostMapping("setout")
    public Result setout(@RequestBody Map map){//设置考试退出次数
        Integer uid=(Integer) map.get("uid");
        Integer tid=(Integer) map.get("tid");
        Integer out=(Integer) map.get("out");
        Exam exam=examMapper.selectOne(Wrappers.<Exam>lambdaQuery().eq(Exam::getUid,uid).eq(Exam::getId,tid));
        exam.setOutdata(out);
        Integer flag=examMapper.update(exam,Wrappers.<Exam>lambdaQuery().eq(Exam::getUid,uid).eq(Exam::getId,tid));
        if(flag==1){
            return Result.success();
        }
        return Result.error("500","设置退出次数失败");
    }

    @PostMapping("getout")
    public Result getout(@RequestBody Map map){//获取考试退出次数
        Integer uid=(Integer) map.get("uid");
        Integer tid=(Integer) map.get("tid");
        Exam exam=examMapper.selectOne(Wrappers.<Exam>lambdaQuery().eq(Exam::getUid,uid).eq(Exam::getId,tid));
        if(exam!=null){
            return Result.success(exam.getOutdata());
        }
        return Result.error("500","获取退出次数失败");
    }

    @PostMapping("gettime")
    public Result gettime(@RequestBody Map map){//获取考试时间的flag
        Integer tid=(Integer) map.get("tid");
        Exam_paper exam_paper=examPaperMapper.selectById(tid);
        LocalDateTime now=LocalDateTime.now();
        Integer flag=2;
        if(exam_paper!=null) {
            if (now.isAfter(exam_paper.getBtime()) && now.isBefore(exam_paper.getEtime())) {
                flag=1;
            }
            else if(now.isBefore(exam_paper.getBtime())){
                flag=2;
            }
            else if(now.isAfter(exam_paper.getEtime())){
                flag=3;
            }
            return Result.success(flag);
        }
        return Result.error("500","获取时间失败");
    }
    @PostMapping("gettimeset")
    public Result gettimeset(){//获取考试时间
        List<Exam_paper> exam_paper=examPaperMapper.selectList(null);
        List<Map> maps=new ArrayList<>();
        for(int i=0;i<exam_paper.size();i++){
            Map map=new HashMap<>();
            map.put("id",exam_paper.get(i).getId());
            map.put("btime",exam_paper.get(i).getBtime());
            map.put("etime",exam_paper.get(i).getEtime());
            maps.add(map);
        }
        return Result.success(maps);
    }

    @PostMapping("getcode")
    public Result getCode(@RequestBody Map map){//获取各学科分数
        Integer uid=(Integer) map.get("uid");
        Integer tid=(Integer) map.get("tid");
        Exam exam=examMapper.selectOne(Wrappers.<Exam>lambdaQuery().eq(Exam::getUid,uid).eq(Exam::getId,tid));
        String codes=exam.getCodestate();
        if(codes.equals("")||codes.equals(null)){
            return Result.error("500","获取分数失败");
        }
        return Result.success(codes);
    }

    @PostMapping("getprogress")
    public Result getprogress(@RequestBody Map map){//获取学习任务点进度
        Integer id=(Integer) map.get("id");
        List<point> list=new ArrayList<>();
        List<Progress> progresses=progressMapper.selectList(Wrappers.<Progress>lambdaQuery().eq(Progress::getUid,id));
        if(progresses!=null){
            for(int i=0;i<progresses.size();i++){
                point point1=new point();
                JSONArray objects=new JSONArray();
                if(progresses.get(i).getTeststate()!=null&&progresses.get(i).getTeststate()!=""){
                    objects = JSON.parseArray(progresses.get(i).getTeststate());//试题
                }
                List<State> lists=objects.toJavaList(State.class);
                point1.setSum(lists.size());
                point1.setTid(progresses.get(i).getTid());
                point1.setType(0);
                Integer num=0;
                for(int j=0;j<lists.size();j++){
                    if(lists.get(j).getState()==1){
                        num++;
                    }
                }
                point1.setNum(num);
                list.add(point1);
                if(progresses.get(i).getTid()==1) {//如果是科目一，要多一个视频
                    point point2 = new point();
                    JSONArray objects2 = JSON.parseArray(progresses.get(i).getVideostate());//试题
                    List<State> lists2 = objects2.toJavaList(State.class);
                    point2.setSum(lists2.size());
                    point2.setTid(progresses.get(i).getTid());
                    point2.setType(1);
                    Integer num2=0;
                    for (int j = 0; j < lists2.size(); j++) {
                        if (lists2.get(j).getState()==1) {
                            num2++;
                        }
                    }
                    point2.setNum(num2);
                    list.add(point2);
                }
            }
            return Result.success(list);
        }
        return Result.error("500","获取学习进度失败");
    }

    public void setcode(Integer uid,Integer tid,String state){//计算分数
        JSONArray objects = JSON.parseArray(state);
        List<State> list=objects.toJavaList(State.class);
        Exam exam=examMapper.selectOne(Wrappers.<Exam>lambdaQuery().eq(Exam::getUid,uid).eq(Exam::getId,tid));
        List<code> codes=new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            Xzt xzt = xztMapper.selectById(list.get(i).getId());//找到选择题\
            State state1 = list.get(i);
            code code1 = new code();
            if (xzt.getType().equals("单选题")) {//单选
                if (state1.getAnswer().equals(xzt.getAnswer())) {//正确
                    code1.setCode(5);
                } else {
                    code1.setCode(0);
                }
            } else {//多选
                if (state1.getAnswer().equals("") || state1.getAnswer().equals("[]") || state1.getAnswer().equals(null)) {//未回答
                    code1.setCode(0);
                } else if (state1.getAnswer().length() == 1 && !state1.getAnswer().contains("[")) {//只选了一个
                    if (xzt.getAnswer().contains(state1.getAnswer())) {
                        code1.setCode(3);
                    } else {
                        code1.setCode(0);
                    }
                } else {
                    JSONArray objects1 = JSON.parseArray(state1.getAnswer());
                    Integer flag = 0;
                    for (int j = 0; j < objects1.size(); j++) {
                        if (!xzt.getAnswer().contains((String) objects1.get(j))) {//选错
                            flag = 1;
                            code1.setCode(0);
                        }
                    }
                    if (flag == 0 && objects1.size() < xzt.getAnswer().length()) {//未选完
                        code1.setCode(3);
                    } else if (flag == 0 && objects1.size() == xzt.getAnswer().length()) {//选完
                        code1.setCode(5);
                    }
                }
            }
            codes.add(code1);
        }
        Integer num=0;
        for(int i=0;i<codes.size();i++){
            num=num+codes.get(i).getCode();
        }
        String codeses= JSON.toJSONString(codes);
        exam.setCode(num);
        exam.setCodestate(codeses);
        examMapper.update(exam,Wrappers.<Exam>lambdaQuery().eq(Exam::getUid,uid).eq(Exam::getId,tid));
    }


}
@Data
class point{
    public Integer tid;//任务id
    public Integer type;//0为试题1为视频
    public Integer sum;//总任务数
    public Integer num;//当前完成任务数量
}
    @Data
    class code{
        public Integer code;//分数
    }