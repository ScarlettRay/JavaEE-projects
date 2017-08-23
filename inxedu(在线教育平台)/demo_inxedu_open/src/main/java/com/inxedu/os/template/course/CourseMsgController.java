package com.inxedu.os.template.course;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.edu.entity.course.CourseDto;
import com.inxedu.os.edu.entity.course.QueryCourse;
import com.inxedu.os.edu.service.course.CourseService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/23.
 */
@Controller
@RequestMapping("/webapp")
public class CourseMsgController extends BaseController {
    private static Logger logger=Logger.getLogger(CourseMsgController.class);

    @Autowired
    private CourseService courseService;

   /* 返回首页的课程信息*/
    @RequestMapping("/courseList")
    @ResponseBody
    public Map<String, Object> getTeacherList(HttpServletRequest request, @ModelAttribute("page") PageEntity page, int num){
        Map<String, Object> json=new HashMap<String, Object>();
        try {
            Map<String,List<CourseDto>> courses = new HashedMap();
            Map<Integer,String> map = new HashMap<>();
            courses = courseService.queryRecommenCourseList();
            List cutCourses = new ArrayList();
            cutCourses = courses.get("recommen_2");

            if(ObjectUtils.isNotNull(cutCourses)){
                for (int i=0;cutCourses.size()>num;i++){
                    cutCourses.remove(cutCourses.size()-1);
                }
                json = this.setJson(true,"",cutCourses);
            }
            else{
                json = this.setJson(true,"",null);
            }



        }catch (Exception e){
            json = this.setJson(false, "异常", null);
            logger.error("getTeacherList()--error",e);
        }
        return json;
    }
    // 首页精品课程、最新课程、全部课程
    @RequestMapping("/typeCourse")
    @ResponseBody
    public Map<String, Object> querytypeCourse(HttpServletRequest request, String order) {
        Map<String, Object> json=new HashMap<String, Object>();
        try {
            if (order != null && !order.equals("")) {
                QueryCourse queryCourse = new QueryCourse();
                queryCourse.setOrder(order);
                //只查询上架的
                queryCourse.setIsavaliable(1);
                //查询条数
                queryCourse.setQueryLimit(8);
                // 获得精品课程、最新课程、全部课程
                List<CourseDto> courseDtoBNAList = courseService.queryCourse(queryCourse);
                json = this.setJson(true,"",courseDtoBNAList);
            }
        } catch (Exception e) {
            json = this.setJson(false, "异常", null);
            logger.error("querytypeCourse()--error",e);
        }
        return json;
    }
}
