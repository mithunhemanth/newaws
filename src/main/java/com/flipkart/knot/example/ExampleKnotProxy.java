package com.flipkart.knot.example;

import io.knotx.dataobjects.ClientResponse;
import io.knotx.dataobjects.KnotContext;
import io.knotx.knot.AbstractKnotProxy;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.util.Set;

public class ExampleKnotProxy extends AbstractKnotProxy {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExampleKnotProxy.class);

  private static final String DEFAULT_TRANSITION = "next";

  private final String secret;

  ExampleKnotProxy(String secret) {
    this.secret = secret;
  }

  @Override
  protected Single<KnotContext> processRequest(KnotContext knotContext) {
    LOGGER.trace("This request is processed by me!");
    String value = someBusinessLogic();
    return Single.just(knotContext).map(context -> {
      context.getClientRequest().getParams().add("secret", value);
      context.setTransition(DEFAULT_TRANSITION);
      return context;
    });
  }

  @Override
  protected boolean shouldProcess(Set<String> knots) {
    // our example Knot processes all incoming messages
    return true;
  }

  @Override
  protected KnotContext processError(KnotContext knotContext, Throwable error) {
    HttpResponseStatus statusCode;
    if (error instanceof IllegalArgumentException) {
      statusCode = HttpResponseStatus.BAD_REQUEST;
    } else {
      statusCode = HttpResponseStatus.INTERNAL_SERVER_ERROR;
    }
    knotContext.setClientResponse(new ClientResponse().setStatusCode(statusCode.code()));
    return knotContext;
  }

  private String someBusinessLogic() {
    return secret;
  }
}