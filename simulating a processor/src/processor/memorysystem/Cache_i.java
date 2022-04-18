package processor.memorysystem;
import generic.*;
import processor.Processor;
import configuration.Configuration;
import processor.pipeline.*;
import processor.*;
import processor.Clock;
import java.*;
import java.lang.*;
public class Cache_i implements Element{
	 Processor containingProcessor;
	// IF_EnableLatchType IF_EnableLatch;
	// IF_OF_LatchType IF_OF_Latch;
	 int size;
	 int lines;
	 int nset;
	 int[][] data;
	int[][] count;
	String[][] add;
	int nbits;
	

	
	
    public int stringCompare(String str1, String str2) 
    { 
    	System.out.println(str1 + " compare " + str2 );
    	if(str1 == null || str2 == null) { 
    		System.out.println(str1 + " compare null " + str2 );
    		return -1;}
  
        int l1 = str1.length(); 
        int l2 = str2.length(); 
        int lmin = Math.min(l1, l2); 
  
        for (int i = 0; i < lmin; i++) { 
            int str1_ch = (int)str1.charAt(i); 
            int str2_ch = (int)str2.charAt(i); 
  
            if (str1_ch != str2_ch) { 
                return -1; 
            } 
        } 
  
        // Edge case for strings like 
        // String 1="Geeks" and String 2="Geeksforgeeks" 
        if (l1 != l2) { 
            return -1; 
        } 
  
        // If none of the above conditions is true, 
        // it implies both the strings are equal 
        else { 
            return 0; 
        } 
    } 
	
	public Cache_i(Processor p)
	{
		size = 1024;
		lines = size/4;
		nset = lines/2;
		data = new int[nset][2];
		count = new int[nset][2];
		add = new String[nset][2];
		nbits = log2(nset);
		  for(int u=0;u<nset;u++) {
			  for(int h=0;h<2;h++) {
				count[u][h] = 0;
				  
			  }
		  }
		
		containingProcessor = p;
	//	IF_EnableLatch = IF_EnableLatch1;
	//	IF_OF_Latch = IF_OF_Latch1;
		
	}
	public static int log2(int n)
	{
	    if(n <= 0) throw new IllegalArgumentException();
	    return 31 - Integer.numberOfLeadingZeros(n);
	}

	public void insert(String a,int d)
	{
		
		  int len = a.length();
			if(len < 32) {
				for(int i=len;i<32;i++) {
					a = "0" + a;
				}
			
			}
			int index;
	 if(nbits == 0) {
	   index = 0;
	 }
	 else {index = Integer.parseInt(a.substring(31-nbits+1),2);}
	  
	   if(count[index][1] < count[index][0])
	   {
		   System.out.println("in inset");
	         data[index][1] = d;
	         count[index][1] = 1;
	         add[index][1] = a.substring(0,31-nbits+1);
	        /* if(count[index][0] < 7)
	         {
	              count[index][0]++;
	         }*/
	   }
	   else
	   {
		   System.out.println("in ins");
	         data[index][0]= d;
	         count[index][0] = 1;
	         add[index][0] = a.substring(0,31-nbits+1);
	        /* if(count[index][1] < 7)
	         {
	              count[index][1]++;
	         }*/
	         System.out.println("over");

	   }
	}
	public int search(int address)
	{
	  String a = Integer.toBinaryString(address);
	  System.out.println(a + " address");
	  int len = a.length();
		if(len < 32) {
			for(int i=len;i<32;i++) {
				a = "0" + a;
			}
		
		}
	
		int index;
		 if(nbits == 0) {
		   index = 0;
		 }
		 else {index = Integer.parseInt(a.substring(31-nbits+1),2);}
		System.out.println(index + "INDEX AFTER SEARCH");
	  if(stringCompare(add[index][0],a.substring(0,31-nbits+1)) == 0)
	  {
		  System.out.println(add[index][0] + "  add index 0");
	      return 1;
	  }
	  else if (stringCompare(add[index][1],a.substring(0,31-nbits+1)) == 0 )
	  {
		  System.out.println(add[index][1] + "  add index 1");
	    return 1;
	  }
	  else
	  {
		
	     return -1;
	  }
	}

	public int[] readsearch(int address)
	{
		int h1 = search(address);
		int h2;
	
	  String a = Integer.toBinaryString(address);
	  System.out.println(a + " address");
	  int len = a.length();
		if(len < 32) {
			for(int i=len;i<32;i++) {
				a = "0" + a;
			}
		
		}
		 System.out.println(search(address) + " SEARCH");
	if(search(address) == -1) {h2 = containingProcessor.getMainMemory().getWord(address);
		
		//IF_EnableLatch.setIFbusy(true);
	
     insert(a,h2);
	}
	
	System.out.println(address + " ADDRESS OF SEARCHED");
	System.out.println(search(address) + " found or not");
		 int index;
		 if(nbits == 0) {
			   index = 0;
			 }
			 else {index = Integer.parseInt(a.substring(31-nbits+1),2);}
	  if(stringCompare(add[index][0],a.substring(0,31-nbits+1)) ==0)
	  {
	      h2 = data[index][0];
	      if(count[index][0]<7) {count[index][0]++;}
	  }
	  else if (stringCompare(add[index][1],a.substring(0,31-nbits+1)) ==0)
	  {
	    h2 =  data[index][1];
	    if(count[index][1]<7) {count[index][1]++;}
	  }
	  else
	  {
		 h2 = containingProcessor.getMainMemory().getWord(address);
		
			//IF_EnableLatch.setIFbusy(true);
	    // insert(a,h2);
	     
	  }
	  System.out.println(index + " INDEX");
	  for(int u=0;u<nset;u++) {
		  for(int h=0;h<2;h++) {
			  System.out.println(add[u][h]);
			  
		  }
	  }
	  System.out.println(a + " YEAH YEAH");
	  return new int[] {h1,h2};
	}
	
	@Override
	public void handleEvent ( Event e ) {
		if ( e.getEventType ( ) == Event.EventType.Cache_iRead)
		{
		Cache_iReadEvent event = (Cache_iReadEvent)e;
		Simulator. getEventQueue().addEvent(
		new Cache_iResponseEvent (
			Clock.getCurrentTime(),
		this ,
		event.getRequestingElement(),
		readsearch(event.getAddressToReadFrom()))) ;
		}
		/*else if ( e.getEventType ( ) == Event.EventType.MemoryRead)
		{
		MemoryReadEvent event = (MemoryReadEvent)e;
		Simulator. getEventQueue().addEvent(
		new Cache_iResponseEvent (
			Clock.getCurrentTime(),
		this ,
		event.getRequestingElement(),
		readsearch(event.getAddressToReadFrom()))) ;
		}*/
		else if(e.getEventType ( ) == Event.EventType.MemoryResponse) 
		{System.out.println("inside this handl eevent loop 0");
		//System.out.println(IF_OF_Latch.isOFbusy()  + " of stuff");
			if( containingProcessor.getIF_OF().isOFbusy())
    		{
				System.out.println("inside this handl eevent loop ");
    		e.setEventTime(Clock.getCurrentTime()+ 1 ) ;
    		Simulator.getEventQueue().addEvent (e) ;
    		}
    		else
    		{
    			System.out.println("inside this handl eevent loop 2");
    		MemoryResponseEvent event = ( MemoryResponseEvent ) e ;
    		containingProcessor.getIF_OF().setInstruction(event.getValue( ));
    	
    		containingProcessor.getIF_Enable().setIFbusy ( false ) ;
    		}
		}
	}
}