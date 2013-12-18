package ning.codelab.hello;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customer")
public class Customer {
 
	String name;
	int pin;
 
	@XmlElement
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
}
