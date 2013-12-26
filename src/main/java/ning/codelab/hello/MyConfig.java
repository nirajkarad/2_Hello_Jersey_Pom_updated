package ning.codelab.hello;

import org.skife.config.Config;
import org.skife.config.Default;

//import ning.configamajig.v1.Property;

/**
 * Configamajig configuration class. The method bodies are ignored at runtime.
 * We make it abstract to help folks not accidentally instantiate it. See
 * {@link HelloServerModule#configureServlets()} body for using Configamajig
 * to instantiate this.
 */
public interface MyConfig
{
    //@Property(value = "xn.hello.message", missing = "hello, world")
    @Config("xn.hello.message")
    @Default("hello, world")
    public abstract String getMessage();
}
