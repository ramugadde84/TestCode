package org.springframework.samples.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileDownloadController {
	@RequestMapping("/download")
	public void download(HttpServletRequest request,HttpServletResponse response){
		File file = new File("C:\\eula.1028.txt");
		response.setContentLength((int) file.length());
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		try {
			OutputStream outputStream = response.getOutputStream();
			try {
				InputStream ins = new FileInputStream(file);
				if (ins != null) {
					byte[] outputByte = new byte[4096];
					while (ins.read(outputByte, 0, 4096) != -1) {
						outputStream.write(outputByte, 0, 4096);
					}
					ins.close();
				}
			} finally {
				outputStream.flush();
				outputStream.close();
			}
		} catch (Exception e) {
			//logger.error(e.toString());
		}
		
	}

}
