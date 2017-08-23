package com.inxedu.os.common.controller;

import com.inxedu.os.common.constants.CommonConstants;
import com.inxedu.os.common.util.DateUtils;
import com.inxedu.os.common.util.FileUploadUtils;
import com.inxedu.os.common.util.PDFUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * @author www.inxedu.com
 *
 */
@Controller
@RequestMapping("/video")
public class VideoUploadController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(ImageUploadController.class);
	private List<String> fileTypeList;

	/**
	 *	获得项目根目录
     */
	private String getProjectRootDirPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");

	}

	/**
	 * 视频上传
	 */
	@RequestMapping(value="/uploadvideo",method={RequestMethod.POST})
	public String gok4(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="uploadfile" ,required=true) MultipartFile uploadfile,
			@RequestParam(value="param",required=false) String param,
			@RequestParam(value="fileType",required=true) String fileType){
		try{

			String[] type = fileType.split(",");
			//设置图片类型
			setFileTypeList(type);
			//获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
			String ext = FileUploadUtils.getSuffix(uploadfile.getOriginalFilename());
			if(!fileType.contains(ext)){
				return responseErrorData(response,1,"文件格式错误，上传失败。");
			}
			//获取文件路径
			String filePath = getPath(request,ext,param);
			File file = new File(getProjectRootDirPath(request)+filePath);

			//如果目录不存在，则创建
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			//保存文件
			uploadfile.transferTo(file);
			//返回数据

			return responseData(filePath,0,"上传成功",response);
		}catch (Exception e) {
			logger.error("gok4()--error",e);
			return responseErrorData(response,2,"系统繁忙，上传失败");
		}
	}




	//--------------------------------------------------------------------------------------

	/**
	 * 获取文件保存的路径
	 * @param request
	 * @param ext 文件后缀
	 * @param param 传入参数
	 * @return 返回文件路径
	 */
	private String getPath(HttpServletRequest request,String ext,String param){
		String filePath = "/images/upload/";
		if(param!=null && param.trim().length()>0){
			filePath+=param;
		}else{
			filePath+=CommonConstants.projectName;
		}
		filePath+="/"+ DateUtils.toString(new Date(), "yyyyMMdd")+"/"+System.currentTimeMillis()+"."+ext;
		return filePath;
	}

	/**
	 * 返回数据
	 * @param path 文件路径
	 * @param error 状态 0成功 其状态均为失败
	 * @param message 提示信息
	 * @return 回调路径
	 */
	public String responseData(String path,int error,String message,HttpServletResponse response) throws IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("url", path);
		map.put("error", error);
		map.put("message", message);
		response.getWriter().write(gson.toJson(map));
		return null;
	}

	/**
	 * 返回错误数据
	 * @param error 状态 0成功 其状态均为失败
	 * @param message 提示信息
	 */
	public String responseErrorData(HttpServletResponse response,int error,String message){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("error", error);
			map.put("message", message);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(gson.toJson(map));
			response.getWriter().flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置图片类型
	 */
	public void setFileTypeList(String[] type){
		fileTypeList = new ArrayList<String>();
		for(String _t : type){
			fileTypeList.add(_t);
		}
	}




}
