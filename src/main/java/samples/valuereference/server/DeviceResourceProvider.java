package samples.valuereference.server;

import java.util.UUID;

import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Device;
import ca.uhn.fhir.model.dstu2.resource.Organization;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.server.IResourceProvider;
import samples.valuereference.model.ExtendedDevice;

public class DeviceResourceProvider implements IResourceProvider {

	public Class<? extends IBaseResource> getResourceType() {
		return Device.class;
	}
	
	@Search
	public Bundle search() {
		
		Organization org = new Organization();
		org.setId(UUID.randomUUID().toString());
		org.setName("Owner institution");
		
		ExtendedDevice dev = new ExtendedDevice();
		dev.setId(UUID.randomUUID().toString());
		CodingDt devType = new CodingDt();
		
		devType.setSystem("http://devTypeSystem");
		devType.setCode("0");
		dev.getType().addCoding(devType);
		
		ExtensionDt someExt = new ExtensionDt();
		someExt.setUrl("http://extensionsBaseUrl/Device#someExt");
		ResourceReferenceDt orgRef = new ResourceReferenceDt();
		orgRef.setResource(org);
		someExt.setValue(orgRef); //this works
		dev.addUndeclaredExtension(someExt);
		
		dev.getSomeOrg().setResource(org); //this doesn't work
		dev.setSomeOtherOrg(new ResourceReferenceDt(org)); //this almost works, the Organization/ prefix is missing
		
		dev.getOwner().setResource(org); //this works
		
		Bundle bundle = new Bundle();
		bundle.setId(new IdDt(UUID.randomUUID().toString()));
		bundle.addEntry().setResource(dev);
		bundle.addEntry().setResource(org);
		
		return bundle;
	}

}
