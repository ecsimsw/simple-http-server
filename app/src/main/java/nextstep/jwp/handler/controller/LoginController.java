package nextstep.jwp.handler.controller;

import nextstep.jwp.db.InMemoryUserRepository;
import nextstep.jwp.handler.modelandview.Model;
import nextstep.jwp.handler.modelandview.ModelAndView;
import nextstep.jwp.http.request.HttpRequest;
import nextstep.jwp.http.request.QueryParams;
import nextstep.jwp.http.response.HttpResponse;
import nextstep.jwp.http.response.HttpStatus;
import nextstep.jwp.http.session.HttpSession;

public class LoginController extends AbstractController {

    @Override
    protected ModelAndView doGet(HttpRequest request, HttpResponse response) {
        HttpSession session = request.getSession();
        if (session.contains("user")) {
            response.addHeader("Location", "index.html");
            return ModelAndView.of(HttpStatus.FOUND);
        }
        return ModelAndView.of("/login.html", HttpStatus.OK);
    }

    @Override
    protected ModelAndView doPost(HttpRequest request, HttpResponse response) {
        QueryParams queryParams = request.requestParam();

        String account = queryParams.get("account");
        String password = queryParams.get("password");

        if (isValidUser(account, password)) {
            Model model = new Model();
            model.addAttribute("errorMessage", "유효하지 않은 사용자 정보입니다.");
            return ModelAndView.of(model, "/401.html", HttpStatus.UNAUTHORIZED);
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", account);

        response.addSession(session);
        response.addHeader("Location", "index.html");

        return ModelAndView.of(HttpStatus.FOUND);
    }

    private boolean isValidUser(String account, String password) {
        return InMemoryUserRepository.findByAccount(account)
                .map(user -> user.checkPassword(password))
                .orElse(false);
    }
}
