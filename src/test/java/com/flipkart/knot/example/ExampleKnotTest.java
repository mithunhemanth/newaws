package com.flipkart.knot.example;

import io.knotx.dataobjects.ClientRequest;
import io.knotx.dataobjects.KnotContext;
import io.knotx.junit.rule.KnotxConfiguration;
import io.knotx.junit.rule.Logback;
import io.knotx.junit.rule.TestVertxDeployer;
import io.knotx.reactivex.proxy.KnotProxy;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.core.Vertx;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import rx.functions.Action1;


@RunWith(VertxUnitRunner.class)
public class ExampleKnotTest {

  private final static String ADAPTER_ADDRESS = "knotx.knot.example";

  //Test Runner Rule of Verts
  private RunTestOnContext vertx = new RunTestOnContext();

  //Test Runner Rule of Knotx
  private TestVertxDeployer knotx = new TestVertxDeployer(vertx);

  //Junit Rule, sets up logger, prepares verts, starts verticles according to the config (supplied in annotation of test method)
  @Rule
  public RuleChain chain = RuleChain.outerRule(new Logback()).around(vertx).around(knotx);


  @Test
  @KnotxConfiguration("test-config.json")
  public void integrationTestToBeWrittenHere(TestContext context) {
    callWithAssertions(context,
        adapterResponse -> {
          // assertions here
        },
        error -> context.fail(error.getMessage()));
  }

  private void callWithAssertions(TestContext context,
      Action1<KnotContext> onSuccess,
      Action1<Throwable> onError) {
    KnotContext knotContext = payload();
    Async async = context.async();

    KnotProxy knot = KnotProxy.createProxy(new Vertx(vertx.vertx()), ADAPTER_ADDRESS);

    knot.rxProcess(knotContext)
        .subscribe(
            success -> {
              try {
                onSuccess.call(success);
              } catch (Throwable e) {
                context.fail(e);
              } finally {
                async.complete();
              }
            },
            err -> {
              onError.call(err);
              async.complete();
            }
        );
  }

  private KnotContext payload() {
    return new KnotContext().setClientRequest(new ClientRequest());
  }

}