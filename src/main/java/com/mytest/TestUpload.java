package com.mytest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.IOUtils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route("TestUpload")
public class TestUpload extends VerticalLayout {

	public TestUpload() {
		System.out.println(
				"com.mytest.TestUpload.TestUpload() {\r\n" + 
				"	[" + new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new java.util.Date()) + "]"
				);
		
		MemoryBuffer buffer = new MemoryBuffer();
		Upload upload = new Upload(buffer);
		Div output = new Div();

		upload.addSucceededListener(event -> {
			// 上傳成功。
			System.out.println("event.getMIMEType() -->" + event.getMIMEType() + "<--");
		    Component component = createComponent(event.getMIMEType(), event.getFileName(), buffer.getInputStream());
		    showOutput(event.getFileName(), component, output);

			try {
				copyToFile(buffer.getInputStream(), "D:\\demo_20210107.txt");
				String str_result = IOUtils.toString(buffer.getInputStream(), StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		add(upload, output);
		
		System.out.println("} com.mytest.TestUpload.TestUpload()\r\n");
	}

	
	
	private Component createComponent(String mimeType, String fileName, InputStream stream) {
		if (mimeType.startsWith("text")) {
			return createTextComponent(stream);
		} else if (mimeType.startsWith("image")) {
			Image image = new Image();
			try {

				byte[] bytes = IOUtils.toByteArray(stream);
				image.getElement().setAttribute("src",
						new StreamResource(fileName, () -> new ByteArrayInputStream(bytes)));
				try (ImageInputStream in = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes))) {
					final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
					if (readers.hasNext()) {
						ImageReader reader = readers.next();
						try {
							reader.setInput(in);
							image.setWidth(reader.getWidth(0) + "px");
							image.setHeight(reader.getHeight(0) + "px");
						} finally {
							reader.dispose();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return image;
		}
		Div content = new Div();
		String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'", mimeType,
				MessageDigestUtil.sha256(stream.toString()));
		content.setText(text);
		return content;

	}

	private Component createTextComponent(InputStream stream) {
		System.out.println(
				"com.mytest.TestUpload.createTextComponent() {\r\n" + 
				"	[" + new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new java.util.Date()) + "]"
				);
	    String text;
	    try {
	        text = IOUtils.toString(stream, StandardCharsets.UTF_8);
	    } catch (IOException e) {
	        text = "exception reading stream";
	    }
		System.out.println("} com.mytest.TestUpload.createTextComponent()\r\n");
	    return new Text(text);
	}

	private void showOutput(String text, Component content, HasComponents outputContainer) {
		HtmlComponent p = new HtmlComponent(Tag.P);
		p.getElement().setText(text);
		outputContainer.add(p);
		outputContainer.add(content);
	}
	
	public void getFile(InputStream is,String fileName) throws IOException{
		fileName = "D:\\demo_20210107.txt";
	    BufferedInputStream in=null;
	    BufferedOutputStream out=null;
	    in=new BufferedInputStream(is);
	    out=new BufferedOutputStream(new FileOutputStream(fileName));
	    int len=-1;
	    byte[] b=new byte[1024];
	    while((len=in.read(b))!=-1){
	        out.write(b,0,len);
	    }
	    in.close();
	    out.close();
	}
	
	
	public void copyToFile(InputStream inputStream, String fileName) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		} else {
			file.createNewFile();
		}

	    try(OutputStream outputStream = new FileOutputStream(file)) {
	        IOUtils.copy(inputStream, outputStream);
	    }
	}  
}

