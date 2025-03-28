package com.flipkart.knot.example;

import io.vertx.core.json.JsonObject;

class ExampleKnotConfiguration {

  private String address;

  private String secret;

  ExampleKnotConfiguration(JsonObject config) {
    address = config.getString("address");
    secret = config.getString("secret");
  }

  String getAddress() {
    return address;
  }

  public String getSecret() {
    return secret;
  }
}