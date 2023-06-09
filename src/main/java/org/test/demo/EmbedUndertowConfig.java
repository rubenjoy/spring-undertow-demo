package org.test.demo;

import io.undertow.Undertow;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.UndertowServletWebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.test.demo.undertow.UndertowBuilderCustomizer;
import org.test.demo.undertow.UndertowDeploymentInfoCustomizer;
import org.test.demo.undertow.UndertowServletWebServerFactory;
import org.xnio.SslClientAuthMode;

import javax.servlet.Servlet;
import java.util.Collection;
import java.util.stream.Collectors;

@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnClass({Servlet.class, Undertow.class, SslClientAuthMode.class})
@ConditionalOnMissingBean(
    value = {ServletWebServerFactory.class},
    search = SearchStrategy.CURRENT
)
public class EmbedUndertowConfig {

  @Bean
  UndertowServletWebServerFactory undertowServletWebServerFactory(ObjectProvider<UndertowDeploymentInfoCustomizer> deploymentInfoCustomizers, ObjectProvider<UndertowBuilderCustomizer> builderCustomizers) {
    UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
    factory.getDeploymentInfoCustomizers().addAll((Collection)deploymentInfoCustomizers.orderedStream().collect(Collectors.toList()));
    factory.getBuilderCustomizers().addAll((Collection)builderCustomizers.orderedStream().collect(Collectors.toList()));
    return factory;
  }

  @Bean
  UndertowServletWebServerFactoryCustomizer undertowServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
    return new UndertowServletWebServerFactoryCustomizer(serverProperties);
  }
}
