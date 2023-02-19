package next.controller.qna;

import core.mvcframework.controller.Controller;
import core.mvcframework.view.JspView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.qna.dto.QuestionCreateDto;
import next.service.QuestionService;

public class QuestionCreateController implements Controller {
    private final QuestionService questionService = new QuestionService();

    @Override
    public JspView execute(final HttpServletRequest request, final HttpServletResponse response) {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto(
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents")
        );
        questionService.insertNewQuestion(questionCreateDto.toModel());
        return new JspView("redirect:/");
    }
}
