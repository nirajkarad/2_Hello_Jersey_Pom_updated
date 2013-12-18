package test.ning.codelab.hello;

import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ning.codelab.hello.HelloResource;
import ning.codelab.hello.HelloServerModule;

/**
 * HelloResource TestNG module.
 */
public class HelloResourceTest
{
    private HelloResource useGuiceToInstantiateTheHelloResource()
    {
        Injector injector = Guice.createInjector(new HelloServerModule());
        HelloResource theHello = injector.getInstance(HelloResource.class);
        return theHello;
    }

    @Test
    public void testHelloDefault()
    {
        HelloResource theHello = useGuiceToInstantiateTheHelloResource();
        assert "hello, world".equals(theHello.getMessage());
    }

    @Test
    public void testHelloSystemProperty()
    {
        String helloPropertyName = "xn.hello.message"; // see MyConfig class
        String emergencyBroadcastSystem = "this is only a test";
        System.setProperty(helloPropertyName, emergencyBroadcastSystem);

        HelloResource theHello = useGuiceToInstantiateTheHelloResource();
        assert emergencyBroadcastSystem.equals(theHello.getMessage());
    }
}
