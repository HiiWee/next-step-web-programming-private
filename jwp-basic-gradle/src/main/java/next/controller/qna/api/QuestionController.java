package next.controller.qna.api;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractAnnotationController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.exception.CannotDeleteQuestionException;
import next.model.Result;
import next.model.User;
import next.service.QuestionService;
import next.utils.SessionUtil;

@Controller
public class QuestionController extends AbstractAnnotationController {

    private final QuestionService questionService = new QuestionService();

    @RequestMapping(value = "/api/questions/delete", method = RequestMethod.POST)
    public ModelAndView delete(final HttpServletRequest request, HttpServletResponse response) {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        User user = SessionUtil.getLoginObject(request.getSession(), "user");
        try {
            questionService.deleteQuestion(questionId, user);
            return jsonView().addObject("result", Result.ok());
        } catch (CannotDeleteQuestionException exception) {
            return jsonView().addObject("result", Result.fail(exception.getMessage()));
        }
    }

    @RequestMapping(value = "/api/questions", method = RequestMethod.GET)
    public ModelAndView list(final HttpServletRequest request, final HttpServletResponse response) {
        return jsonView().addObject("questions", questionService.findAll());
    }
}
