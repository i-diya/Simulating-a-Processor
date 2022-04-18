package generic;

import generic.Event.EventType;

public class Cache_iResponseEvent extends Event {

	int[] value;
	
	public Cache_iResponseEvent(long eventTime, Element requestingElement, Element processingElement, int[] value) {
		super(eventTime, EventType.Cache_iResponse, requestingElement, processingElement);
		this.value = value;
	}

	public int[] getValue() {
		return value;
	}

	public void setValue(int[] value) {
		this.value = value;
	}

}
