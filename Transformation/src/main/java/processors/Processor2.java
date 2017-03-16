package processors;

import employee1.Employee1;
import employee2.Employee2;
import org.apache.camel.Exchange;


public class Processor2 {

    public void processor2Method (Exchange ex) throws Exception {

        ex.getIn().setHeader("CamelFileName", "Employee1toEmployee2.json");

        Employee1 e1 = (Employee1) ex.getIn().getBody();
        String firstName = e1.getFirstname();
        String lastName = e1.getLastname();

        Employee2 e2 = new Employee2();
        e2.setGivenname(firstName);
        e2.setFamilyname(lastName);

        ex.getIn().setBody(e2);
    }

}
