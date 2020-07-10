package com.saurabh.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageploadController {

	@Autowired
	ImageRepository imr;
	
	@PostMapping("/upload")
	public String uploadImage(@RequestParam("imageName") MultipartFile file) throws IOException {
		System.out.println("Origianl Image Byte Size:- "+ file.getBytes().length);
		ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
		imr.save(img);
		return "Uploades";
	}

	@PostMapping("/get/{imageName}")
	public ImageModel getImage(@PathVariable("imageName") String imageName)throws IOException{
		final Optional<ImageModel> retrivedImage = imr.findByName(imageName);
		ImageModel img = new ImageModel(retrivedImage.get().getName(), retrivedImage.get().getType(), decompressBytes(retrivedImage.get().getPicByte()));
		
		return img;
	}
	
	
	private byte[] decompressBytes(byte[] data) {
		// TODO Auto-generated method stub
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[500000];
		try {
			while(!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		}catch (IOException e) {
			
			// TODO: handle exception
		}catch (Exception e) {
			// TODO: handle exception
		}
		return outputStream.toByteArray();
	}

	private byte[] compressBytes(byte[] data) throws IOException {
		Deflater deflater =  new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[500000];
		while(!deflater.finished()){
			int count = deflater.deflate(buffer);
			outputStream.write(buffer,0,count);
		}
		try {
			outputStream.close();
		}catch (IOException e) {
			// TODO: handle exception
		}
		System.out.println("Compressed Image Size is:- "+ outputStream.toByteArray().length);
		
		return outputStream.toByteArray();
	}
}
