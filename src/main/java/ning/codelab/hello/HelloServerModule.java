package ning.codelab.hello;

import static com.google.common.collect.ImmutableMap.of;
import ning.codelab.hello.json.JacksonJsonProviderWrapper;
import ning.configamajig.v1.Configamajig;
import ning.jackson.guice.CustomObjectMapperProvider;
import ning.jackson.serializers.DateTimeSerializer;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.container.filter.GZIPContentEncodingFilter;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * This is the root module for the Hello server. It will install other modules
 * and configure what is needed for the hello core. See
 * <a href=http://code.google.com/p/google-guice/>http://code.google.com/p/google-guice/</a>
 * for more information on Google Guice.
 */
public class HelloServerModule extends ServletModule
{
    protected void configureServlets()
    {
        /*
         * Install the Configamajig module which populates objects using System
         * properties (or defaults provided in the annotations).
         */

        /* This should be the most up-to-date way to bind gepo properties (which
         * are passed to JVM as Java system properties) to basic config POJO
         * (or, if missing, Config-object annotation provided defaults!)
         */
        final MyConfig config = new Configamajig(System.getProperties()).configure(MyConfig.class);
        bind(MyConfig.class).toInstance(config);

        // but you may also see this older variant:
        //install(new ConfigModule());
        //bind(MyConfig.class).toProvider(ConfigModule.provide(MyConfig.class)).asEagerSingleton();

        /*
         * These next two bindings configure Jackson
         * ( http://jackson.codehaus.org/ ) for generating JSON, which is our
         * most commonly used representation media type for resources.
         */
        bind(JacksonJsonProvider.class).toProvider(JacksonJsonProviderWrapper.class) // otherwise it would be
                                                                                     // JacksonJsonProviderProvider ...
            .asEagerSingleton();

        /*
         * This configures the Jackson object mapper to convert JodaTime
         * DateTime instances into ISO date/time formats.
         * 
         * ( http://joda-time.sourceforge.net/ )
         */
        bind(ObjectMapper.class).toProvider(new CustomObjectMapperProvider().addSerializer(DateTime.class, new DateTimeSerializer())).asEagerSingleton();

        /*
         * This instructs the filter to serve all requests through
         * GuiceContainer, which is actually just Jersey-via-Guice, and to
         * enable gzip compression in and out.
         */
        serve("*").with(GuiceContainer.class, of("com.sun.jersey.spi.container.ContainerRequestFilters", GZIPContentEncodingFilter.class.getName(), "com.sun.jersey.spi.container.ContainerResponseFilters", GZIPContentEncodingFilter.class.getName()));

        // otherwise, this would do just fine:
        //serve("*").with(GuiceContainer.class);
        
        /*
         * Bind a HelloResource (to an instance that is created by Guice).
         */
        bind(HelloResource.class).asEagerSingleton();
    }
}
