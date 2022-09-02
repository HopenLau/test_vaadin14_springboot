package com.mytest.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("TestView")
public class TestView extends VerticalLayout {
	private Logger log = LoggerFactory.getLogger(TestView.class);

	public TestView() {
		log.info("init");

		System.out.println(
				"com.mytest.TestView.TestView() {\r\n" + 
				"	[" + new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new java.util.Date()) + "]"
				);
		
		TextArea textArea = new TextArea("請輸入員工編號：");
		textArea.setPlaceholder("Write here ...");
		
		TextField textField = new TextField("Your name");
		textField.addThemeName("bordered");

		add(textArea);
		add(textField);

		System.out.println("} com.mytest.TestView.TestView()\r\n");
	}
	
}
