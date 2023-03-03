package core.mvcframework.controller;

import core.mvcframework.ModelAndView;
import core.mvcframework.view.JsonView;
import core.mvcframework.view.JspView;

public class AbstractAnnotationController {

    protected ModelAndView jspView(String forwardUri) {
        return new ModelAndView(new JspView(forwardUri, "/WEB-INF/views/", ".jsp"));
    }

    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }

}
