package processors;

import employee1.Employee1;
import employee2.Employee2;
import org.apache.camel.Exchange;


public class Processor1 {

    // processor1Method method --> argument (Exchange ex)

    public void processor1Method (Exchange ex) throws Exception {

        ex.getIn().setHeader("CamelFileName", "Employee2toEmployee1.xml");

        Employee2 e2 = (Employee2) ex.getIn().getBody();
        String firstName = e2.getGivenname();
        String lastName = e2.getFamilyname();

        Employee1 p2 = new Employee1();
        p2.setFirstname(firstName);
        p2.setLastname(lastName);

        ex.getIn().setBody(p2);
    }

}
