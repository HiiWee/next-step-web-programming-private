package core.mvcframework;

import java.util.HashMap;
import java.util.Map;
import next.controller.ShowFormController;
import next.controller.CreateUserController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginUserController;
import next.controller.LogoutUserController;
import next.controller.ProfileController;
import next.controller.QuestionFormController;
import next.controller.UpdateUserController;
import next.controller.UpdateUserFormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
    private static final Map<String, Controller> handlerMapping = new HashMap<>();

    static {
        // 홈
        handlerMapping.put("/", new HomeController());

        // 유저 단순 포워딩
        handlerMapping.put("/users/form", new ForwardController("user/form"));
        handlerMapping.put("/users/loginForm", new ForwardController("user/login"));
        handlerMapping.put("/users/loginFailed", new ForwardController("user/login_failed"));

        // 질문/응답 단순 포워딩
        handlerMapping.put("/qna/questionForm", new ForwardController("/qna/form"));
        handlerMapping.put("/qna/showForm", new ForwardController("/qna/show"));

        // 유저
        handlerMapping.put("/users/create", new CreateUserController());
        handlerMapping.put("/users", new ListUserController());
        handlerMapping.put("/users/login", new LoginUserController());
        handlerMapping.put("/users/logout", new LogoutUserController());
        handlerMapping.put("/users/profile", new ProfileController());
        handlerMapping.put("/users/update", new UpdateUserController());
        handlerMapping.put("/users/updateForm", new UpdateUserFormController());

        // 질문/응답
        handlerMapping.put("/qna/question", new QuestionFormController());
        handlerMapping.put("/qna/show", new ShowFormController());
    }

    public Controller getHandlerMapping(final String requestURI) {
        log.debug("requestURI={}", requestURI);
        return handlerMapping.get(requestURI);
    }
}
