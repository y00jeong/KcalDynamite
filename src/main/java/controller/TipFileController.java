package controller;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;

@Controller
@RequestMapping("/tipFile")
public class TipFileController {
	
	@RequestMapping(value="/imgUpload", method=RequestMethod.POST)
	@ResponseBody
	public String fileUpload(HttpServletRequest req,
							 HttpServletResponse resp,
							 MultipartHttpServletRequest multiFile) throws Exception {
		
		JsonObject json = new JsonObject();
		PrintWriter printWriter = null;
		MultipartFile file = multiFile.getFile("upload");
		
		if (file != null) {
			
			if (file.getSize() > 0 && StringUtils.isNotBlank(file.getName())) {

				try {
					String fileName = file.getName();
					byte[] bytes = file.getBytes();
					file.transferTo(new File("C:/filesave", file.getOriginalFilename()));			
					
					printWriter = resp.getWriter();
					resp.setContentType("text/html");
					String fileUrl = req.getContextPath() + "/upload/" + file.getOriginalFilename();
					
					json.addProperty("uploaded", 1);
					json.addProperty("fileName", file.getOriginalFilename());
					json.addProperty("url", fileUrl);
					
					printWriter.println(json);
					
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if(printWriter != null) {
							printWriter.close();
						}
					}

			}
		}
		
		return null;
	}
}
	
//	public @ResponseBody String multipleFileUploadWithAjax(
//			MultipartFile[] uploadFile) throws Exception {
//		
//		for (MultipartFile file : uploadFile) {
//			if (!file.getOriginalFilename().isEmpty()) {
//				file.transferTo(new File("C:/fileTest", file.getOriginalFilename()));
//				System.out.println(file.getOriginalFilename() + "저장완료");
//			} else {
//				System.out.println("파일이 존재하지 않습니다.");
//			}
//		}
//		
//		System.out.println("모든 데이터가 저장되었습니다.");
//		return "test5에서 파일을 잘 받았습니다.";
//	}	
//}









































