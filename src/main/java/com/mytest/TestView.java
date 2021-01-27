package com.mytest;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("TestView")
public class TestView extends VerticalLayout {
	
	public TestView() {
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
