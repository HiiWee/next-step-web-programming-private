package next.controller.qna;

import core.mvcframework.controller.Controller;
import core.mvcframework.view.JspView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.controller.qna.dto.AnswerCreateDto;
import next.model.User;
import next.service.AnswerService;
import next.utils.SessionUtil;

public class AnswerCreateController implements Controller {
    private final AnswerService answerService = new AnswerService();

    @Override
    public JspView execute(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!SessionUtil.isLogined(session, "user")) {
            return new JspView("redirect:/users/loginForm");
        }
        AnswerCreateDto answerCreateDto = createAnswerDto(request, session);
        answerService.insertNewAnswer(answerCreateDto.toModel());
        return new JspView("redirect:/qna/showForm?questionId=" + answerCreateDto.getQuestionId());
    }

    private AnswerCreateDto createAnswerDto(final HttpServletRequest request, final HttpSession session) {
        User user = (User) session.getAttribute("user");
        return new AnswerCreateDto(
                user.getUserId(),
                request.getParameter("contents"),
                Long.parseLong(request.getParameter("questionId"))
        );
    }
}