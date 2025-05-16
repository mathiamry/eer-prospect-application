package com.cbao.eerprospect;

import com.cbao.eerprospect.config.AsyncSyncConfiguration;
import com.cbao.eerprospect.config.EmbeddedRedis;
import com.cbao.eerprospect.config.EmbeddedSQL;
import com.cbao.eerprospect.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { EerProspectApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedSQL
public @interface IntegrationTest {
}
