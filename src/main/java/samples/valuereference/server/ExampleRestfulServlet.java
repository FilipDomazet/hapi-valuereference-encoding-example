package samples.valuereference.server;

import javax.servlet.ServletException;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.EncodingEnum;
import ca.uhn.fhir.rest.server.RestfulServer;

public class ExampleRestfulServlet extends RestfulServer {

	private static final long serialVersionUID = 1L;

	@Override
	protected void initialize() throws ServletException {
		setFhirContext(FhirContext.forDstu2());
		
		setResourceProviders(new DeviceResourceProvider());
		
		setDefaultPrettyPrint(true);
		setDefaultResponseEncoding(EncodingEnum.JSON);
	}
	
	
	
}
