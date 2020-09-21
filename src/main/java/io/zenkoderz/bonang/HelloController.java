/*
 * Copyright 2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.zenkoderz.bonang;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import javax.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/local")
@Validated
public class HelloController {

  private final HelloService helloService;

  @Inject
  public HelloController(HelloService helloService) {
    this.helloService = helloService;
  }

  @Get(uri = "/hello/{name}", produces = MediaType.APPLICATION_JSON)
  public Mono<Greeting> hello(@Parameter("name") final String name) {
    return this.helloService.hello(new Greeting(name));
  }

  @Get(uri = "/greetings/{names}", produces = MediaType.APPLICATION_JSON)
  public Flux<Greeting> greetings(@Parameter("names") final String names) {
    return Flux.just(names.split(","))
        .flatMap(name -> this.helloService.hello(new Greeting(name)));
  }
}
