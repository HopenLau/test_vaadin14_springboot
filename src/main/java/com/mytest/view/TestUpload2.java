package com.mytest.view;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.web.util.UriUtils;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;

import elemental.json.Json;

@Route("TestUpload2")
public class TestUpload2 extends VerticalLayout {
	public TestUpload2() {
		System.out.println("com.mytest.TestUpload2.TestUpload2() {\r\n" + "	["
				+ new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new java.util.Date()) + "]");

		MemoryBuffer buffer = new MemoryBuffer();
		Upload upload = new Upload(buffer);
		Div output = new Div();

//		upload.addSucceededListener(event -> {
//			Component component = createComponent(event.getMIMEType(), event.getFileName(), buffer.getInputStream());
//			showOutput(event.getFileName(), component, output);
//		});

		Image imagePreview;
		imagePreview = new Image();
		imagePreview.setWidth("100%");

		ByteArrayOutputStream uploadBuffer = new ByteArrayOutputStream();
		upload.setAcceptedFileTypes("image/*");
		upload.setReceiver((fileName, mimeType) -> {
			return uploadBuffer;
		});
		upload.addSucceededListener(e -> {
			String mimeType = e.getMIMEType();
			String base64ImageData = Base64.getEncoder().encodeToString(uploadBuffer.toByteArray());
			String dataUrl = "data:" + mimeType + ";base64,"
					+ UriUtils.encodeQuery(base64ImageData, StandardCharsets.UTF_8);
			upload.getElement().setPropertyJson("files", Json.createArray());
			imagePreview.setSrc(dataUrl);
			uploadBuffer.reset();
		});
		
		imagePreview.setVisible(false);

		add(upload, output);
		
		

		System.out.println(TestUpload2.class.getResource("/").getPath());
		

		System.out.println("} com.mytest.TestUpload2.TestUpload2()\r\n");
	}

	private void attachImageUpload(Upload upload, Image preview) {
		ByteArrayOutputStream uploadBuffer = new ByteArrayOutputStream();
		upload.setAcceptedFileTypes("image/*");
		upload.setReceiver((fileName, mimeType) -> {
			return uploadBuffer;
		});
		upload.addSucceededListener(e -> {
			String mimeType = e.getMIMEType();
			String base64ImageData = Base64.getEncoder().encodeToString(uploadBuffer.toByteArray());
			String dataUrl = "data:" + mimeType + ";base64,"
					+ UriUtils.encodeQuery(base64ImageData, StandardCharsets.UTF_8);
			upload.getElement().setPropertyJson("files", Json.createArray());
			preview.setSrc(dataUrl);
			uploadBuffer.reset();
		});
		preview.setVisible(false);
	}

	
	
	
	
	
	
	
}
