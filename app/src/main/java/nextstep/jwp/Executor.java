package nextstep.jwp;

import nextstep.jwp.handler.ModelAndView;
import nextstep.jwp.http.request.HttpRequest;
import nextstep.jwp.http.request.RequestLine;
import nextstep.jwp.http.response.HttpResponse;
import nextstep.jwp.mapper.HandlerMappers;
import nextstep.jwp.view.View;
import nextstep.jwp.view.ViewResolver;

public class Executor {

    private final HandlerMappers handlerMappers = new HandlerMappers();
    private final ViewResolver viewResolver = new ViewResolver();

    public HttpResponse service(HttpRequest httpRequest) {
        ModelAndView modelAndView = handlerMappers.handle(httpRequest);
        View view = viewResolver.resolve(modelAndView.getViewName());
        return view.render(modelAndView.getModel());
    }
}
