package com.flipkart.knot.example;

import io.knotx.proxy.KnotProxy;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;

public class ExampleKnot extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExampleKnot.class);

  private MessageConsumer<JsonObject> consumer;

  private ExampleKnotConfiguration configuration;

  private ServiceBinder serviceBinder;

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);
    configuration = new ExampleKnotConfiguration(config());
  }

  @Override
  public void start() throws Exception {
    LOGGER.info("Starting <{}>", this.getClass().getSimpleName());

    //register the service proxy on event bus
    serviceBinder = new ServiceBinder(getVertx());
    consumer = serviceBinder
        .setAddress(configuration.getAddress())
        .register(KnotProxy.class, new ExampleKnotProxy(configuration.getSecret()));
  }

  @Override
  public void stop() throws Exception {
    serviceBinder.unregister(consumer);
  }
}