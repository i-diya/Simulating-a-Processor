package generic;

import generic.Event.EventType;

public class Cache_dReadEvent extends Event {

	int addressToReadFrom;
	
	public Cache_dReadEvent(long eventTime, Element requestingElement, Element processingElement, int address){
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
