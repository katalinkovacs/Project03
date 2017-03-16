package processors;

import employee2.Employee2;
import employee3.Employee3;
import org.apache.camel.Exchange;


public class Processor3 {

    public void processor3Method (Exchange ex) throws Exception {

        ex.getIn().setHeader("CamelFileName", "Employee2toEmployee3.json");

        Employee2 e2 = (Employee2) ex.getIn().getBody();
        String lastName = e2.getFamilyname();
        String firstName = e2.getGivenname();


        Employee3 e3 = new Employee3();
        e3.setLastname(lastName);
        e3.setFirstname(firstName);

        ex.getIn().setBody(e3);
    }
}
