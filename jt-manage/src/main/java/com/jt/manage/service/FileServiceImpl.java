package com.jt.manage.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

@Service
public class FileServiceImpl implements FileService {

	//文件保存位置
	private String dirPath="D:/test/";
	
	//设置虚拟路径
	private String urlPath="http://image.jt.com/";
	/**
	 * 思路:
	 * 	1.校验上传的文件是否为图片 	jpg/png/gif
	 * 	2.判断文件是否为恶意程序 		exe/rpm
	 * 	3.为了提高检索效率,一般采用分文件存储,层级最好不要超过五级
	 * 	4.为了防止文件名称重复,采用动态方式获取文件名称
	 */
	@Override
	public PicUploadResult uploadFile(MultipartFile uploadFile) {
		PicUploadResult result=new PicUploadResult();
		//1.校验上传的文件是否为图片
		//>>获取文件名称
		String fileName=uploadFile.getOriginalFilename();
		fileName=fileName.toLowerCase();//将数据转化为小写
		/**正则表达式:^开始 $结束 .除了回车/r换行/n之外任意元素 
		 *  +至少一个  *没有或者多个
		 *  ()组		|或者
		 */
		if (!fileName.matches("^.*\\.(jpg|png|gif)$")) {
			result.setError(1);//0表示正常,1表示错误
		}
		
		//2.判断是否为恶意程序
//		BufferedImage是Image的一个子类，
//		Image和BufferedImage的主要作用就是将一副图片加载到内存中
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(uploadFile.getInputStream());
			int width=bufferedImage.getWidth();
			int height=bufferedImage.getHeight();
			if (width==0||height==0) {
				result.setError(1);//表示不是图片
				return result;//提前返回
			}
			//程序执行到这里表示是图片
			//3.为了提高检索效率,采用分文件存储
			String dateDir=new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			//4.保证文件不重名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			int random=new Random().nextInt(1000);
			String fileType=fileName.substring(fileName.lastIndexOf("."));
			String uuidFileName=uuid+random+fileType;
			
			//定义文件夹路径
			String fileDirPath=dirPath+dateDir;
			File fileDir=new File(fileDirPath);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			
			//5.实现文件上传
			 File realFilePath=new File(fileDirPath+"/"+uuidFileName);
			 uploadFile.transferTo(realFilePath);
			 result.setHeight(height+"");
			 result.setWidth(width+"");
			 //为了实现图片访问,拼接虚拟路径
			 String realUrlPath=urlPath+dateDir+"/"+uuidFileName;
			 result.setUrl(realUrlPath);
		} catch (IOException e) {
			e.printStackTrace();
			result.setError(1);
		}
		return result;
	}

}
