package nextstep.jwp.handler.resource;

import java.net.URL;
import java.util.Objects;
import nextstep.jwp.ServerConfig;
import nextstep.jwp.handler.modelandview.ModelAndView;
import nextstep.jwp.http.request.HttpMethod;
import nextstep.jwp.http.request.HttpRequest;
import nextstep.jwp.http.request.SourcePath;
import nextstep.jwp.http.response.HttpResponse;
import nextstep.jwp.http.response.HttpStatus;

public class ResourceHandlerImpl implements ResourceHandler {

    private static final String RESOURCE_BASE_PATH = ServerConfig.RESOURCE_BASE_PATH;

    public boolean mapping(HttpRequest httpRequest) {
        HttpMethod httpMethod = httpRequest.httpMethod();
        return (httpMethod.isGet() && isExistResource(httpRequest.sourcePath()));
    }

    @Override
    public ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        SourcePath sourcePath = httpRequest.sourcePath();
        if (isExistResource(sourcePath)) {
            return ModelAndView.of(sourcePath.getValue(), HttpStatus.OK);
        }
        return ModelAndView.of("/404.html", HttpStatus.NOT_FOUND);
    }

    public boolean isExistResource(SourcePath sourcePath) {
        final URL resourceUrl = getClass().getResource(RESOURCE_BASE_PATH + sourcePath.getValue());
        return !Objects.isNull(resourceUrl);
    }
}
