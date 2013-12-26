package ning.codelab.hello;

import org.skife.config.ConfigurationObjectFactory;

import com.google.inject.Provider;

public class ConfigProvider implements Provider<MyConfig>{

	@Override
	public MyConfig get() {
		ConfigurationObjectFactory c = new ConfigurationObjectFactory(System.getProperties());
		return c.build(MyConfig.class);
	}
}
