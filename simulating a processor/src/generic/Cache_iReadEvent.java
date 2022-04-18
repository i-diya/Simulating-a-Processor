package generic;

import generic.Event.EventType;

public class Cache_iReadEvent extends Event {

	int addressToReadFrom;
	
	public Cache_iReadEvent(long eventTime, Element requestingElement, Element processingElement, int address){
		super(eventTime, EventType.Cache_iRead, requestingElement, processingElement);
		this.addressToReadFrom = address;
	}

	public int getAddressToReadFrom() {
		return addressToReadFrom;
	}

	public void setAddressToReadFrom(int addressToReadFrom) {
		this.addressToReadFrom = addressToReadFrom;
	}
}