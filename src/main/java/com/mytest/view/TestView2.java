package com.mytest.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;

@Route("TestView2")
public class TestView2 extends VerticalLayout implements BeforeEnterObserver {
	private Logger log = LoggerFactory.getLogger(TestView2.class);

	public TestView2() {
		log.info("init");

		TextArea textArea = new TextArea("請輸入員工編號：");
		textArea.setPlaceholder("Write here ...");

		TextField textField = new TextField("Your name");
		textField.addThemeName("bordered");

		add(textArea);
		add(textField);

	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		VaadinSession session = UI.getCurrent().getSession();
		VaadinRequest req = VaadinService.getCurrentRequest();
		VaadinResponse resp = VaadinService.getCurrentResponse();
		log.info(req.getParameter("pa"));// 獲得參數pa的值。
		log.info(req.getParameter("pb"));// 獲得參數pb的值。
		WrappedSession wappedSession = UI.getCurrent().getSession().getSession();
	}

}
