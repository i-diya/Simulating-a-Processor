package generic;

import generic.Event.EventType;

public class Cache_iWriteEvent extends Event {

	int addressToWriteTo;
	int value;
	
	public Cache_iWriteEvent(long eventTime, Element requestingElement, Element processingElement, int address, int value) {
		super(eventTime, EventType.Cache_iWrite, requestingElement, processingElement);
		this.addressToWriteTo = address;
		this.value = value;
	}

	public int getAddressToWriteTo() {
		return addressToWriteTo;
	}

	public void setAddressToWriteTo(int addressToWriteTo) {
		this.addressToWriteTo = addressToWriteTo;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
