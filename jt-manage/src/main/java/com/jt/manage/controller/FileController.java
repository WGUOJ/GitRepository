package com.jt.manage.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;

	//实现图片上传的入门案例
	@RequestMapping("/File")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		//1.判断文件夹是否存在
		File fileDir=new File("D:/test");
		if (!fileDir.exists()) {
			fileDir.mkdirs();//文件夹不存在则创建新的文件夹
		}
		//2.获取文件名
		//abc.jpg
		String fileName=fileImage.getOriginalFilename();
		
		//实现文件的上传
		fileImage.transferTo(new File("D:/test/"+fileName));
		
		//使用重定向的技术
		return "redirect:/File.jsp";
		
		//return "forword:/File.jsp";//转发
	}
	
	//实现文件上传
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult uploadFile(MultipartFile uploadFile) {
		return fileService.uploadFile(uploadFile);
	}
}
