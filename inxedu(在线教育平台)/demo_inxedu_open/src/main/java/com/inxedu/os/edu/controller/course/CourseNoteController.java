package com.inxedu.os.edu.controller.course;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.util.SingletonLoginUtils;
import com.inxedu.os.common.util.StringUtils;
import com.inxedu.os.edu.entity.course.CourseNote;
import com.inxedu.os.edu.service.course.CourseNoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * CourseNote 课程笔记 Controller
 * @author www.inxedu.com
 */
@Controller
public class CourseNoteController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(CourseNoteController.class);

 	@Autowired
    private CourseNoteService courseNoteService;

    /**
     * 查询该用户笔记
     *
     * @return
     */
    @RequestMapping("/courseNote/ajax/querynote")
    public String querynote(HttpServletRequest request, @RequestParam("kpointId") Long kpointId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //通过节点id和用户id查询笔记
            CourseNote courseNote = courseNoteService.getCourseNoteByKpointIdAndUserId(kpointId, Long.valueOf(SingletonLoginUtils.getLoginUserId(request)));
            request.setAttribute("courseNote",courseNote);
            String uuid = StringUtils.createUUID().replace("-", "");
            request.setAttribute("uuid",uuid);
        } catch (Exception e) {
            logger.error("CourseNoteController.querynote()", e);
            return setExceptionRequest(request, e);
        }
        return getViewPath("/web/ucenter/query_note");
    }
    /**
     * 添加笔记
     * 
     * @return
     */
    @RequestMapping("/courseNote/ajax/addnote")
    @ResponseBody
    public Map<String, Object> addnote(HttpServletRequest request, @ModelAttribute("courseNote") CourseNote courseNote) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //添加笔记（如果笔记存在则更新不存在则添加）
            courseNote.setUpdateTime(new Date());
            courseNote.setUserId(Long.valueOf(SingletonLoginUtils.getLoginUserId(request)));
            String falg = courseNoteService.addCourseNote(courseNote);
            map.put("message", falg);
        } catch (Exception e) {
            logger.error("CourseNoteController.addnote", e);
            map.put("message", "false");
            return map;
        }
        return map;
    }
}