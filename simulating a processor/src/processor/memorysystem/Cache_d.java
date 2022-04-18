package processor.memorysystem;
import generic.*;
import processor.Processor;
import configuration.Configuration;
import processor.pipeline.*;
import processor.*;
import processor.Clock;
import java.*;
import java.lang.*;
public class Cache_d implements Element{
	 Processor containingProcessor;
	 int size;
	 int lines;
	 int nset;
	 int[][] data;
	int[][] count;
	String[][] add;
	int nbits;
	
	 public int stringCompare(String str1, String str2) 
	    { 
	    	if(str1 == null || str2 == null) { return 1;}
	  
	        int l1 = str1.length(); 
	        int l2 = str2.length(); 
	        int lmin = Math.min(l1, l2); 
	  
	        for (int i = 0; i < lmin; i++) { 
	            int str1_ch = (int)str1.charAt(i); 
	            int str2_ch = (int)str2.charAt(i); 
	  
	            if (str1_ch != str2_ch) { 
	                return str1_ch - str2_ch; 
	            } 
	        } 
	  
	        // Edge case for strings like 
	        // String 1="Geeks" and String 2="Geeksforgeeks" 
	        if (l1 != l2) { 
	            return l1 - l2; 
	        } 
	  
	        // If none of the above conditions is true, 
	        // it implies both the strings are equal 
	        else { 
	            return 0; 
	        } 
	    } 
	 
	public Cache_d(Processor p)
	{
		size = 128;
		lines = size/4;
		nset = lines/2;
		data = new int[nset][2];
		count = new int[nset][2];
		add = new String[nset][2];
		nbits = log2(nset);
		containingProcessor = p;
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
	         data[index][1] = d;
	         count[index][1] = 1;
	         add[index][1] = a.substring(0,31-nbits+1);
	       /*  if(count[index][0] < 7)
	         {
	              count[index][0]++;
	         }*/
	   }
	   else
	   {

	         data[index][0]= d;
	         count[index][0] = 1;
	         add[index][0] = a.substring(0,31-nbits+1);
	       /*  if(count[index][1] < 7)
	         {
	              count[index][1]++;
	         }*/

	   }
	}
	public void write(String a,int d)
	{
		//a = "0"+a;
		  int len = a.length();
			if(len < 32) {
				for(int i=len;i<32;i++) {
					a = "0" + a;
				}
			
			}
			int h = Integer.parseInt(a,2);
			if(search(h) == -1) { insert(a,d);
			containingProcessor.getMainMemory().setWord(h,d);}
			else {
		containingProcessor.getMainMemory().setWord(h,d);
		 int index;
		 System.out.println("hello  "+a);
		 if(nbits == 0) {
			   index = 0;
			 }
			 else {
				 	index = Integer.parseInt(a.substring(31-nbits+1),2);}
	 
	   if(stringCompare(add[index][0],a.substring(0,31-nbits+1)) ==0)
	    {
	        data[index][0] = d;
	        if(count[index][0]<7) {count[index][0]++;}
	        /*write in main memory*/
	    }
	  else if (stringCompare(add[index][1],a.substring(0,31-nbits+1)) ==0)
	    {

	       data[index][1] = d;
	       if(count[index][1]<7 ){count[index][1]++;}
	       /*write in data memory*/
	    }
	     else
	      {
	    	 
	    	 int ins = containingProcessor.getMainMemory().getWord(h);
	 		
	        insert(a,ins);
	         if(stringCompare(add[index][0],a.substring(0,31-nbits+1)) ==0)
	        {
	           data[index][0] = d;
	           
	        /*write in main memory with data d*/
	        }
	       else if (stringCompare(add[index][1],a.substring(0,31-nbits+1)) ==0)
	        {

	           data[index][1] = d;
	       /*write in data memory with data d*/
	        }

	      }
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
	  if(stringCompare(add[index][0],a.substring(0,31-nbits+1)) ==0)
	  {
	      System.out.println(add[index][0] +  "  that thing in cache mem");
		  return 1;
	  }
	  else if (stringCompare(add[index][1],a.substring(0,31-nbits+1)) ==0)
	  {
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
		 int index;
		 if(nbits == 0) {
			   index = 0;
			 }
			 else {index = Integer.parseInt(a.substring(31-nbits+1),2);}
	  if(stringCompare(add[index][0],a.substring(0,31-nbits+1)) ==0)
	  {
	      h2 = data[index][0];
	      if(count[index][0] < 7) {count[index][0]++;}
	  }
	  else if (stringCompare(add[index][1],a.substring(0,31-nbits+1)) ==0)
	  {
	    h2 =  data[index][1];
	    if(count[index][1] < 7) { count[index][1]++;}
	  }
	  else
	  {
		 h2 = containingProcessor.getMainMemory().getWord(address);
		
			//IF_EnableLatch.setIFbusy(true);
	     insert(a,h2);
	     
	  }
	  return new int[] {h1,h2};
	}
	@Override
	public void handleEvent ( Event e ) {
		if ( e.getEventType ( ) == Event.EventType.Cache_dRead)
		{
		Cache_dReadEvent event = (Cache_dReadEvent)e;
		Simulator. getEventQueue().addEvent(
		new Cache_dResponseEvent (
			Clock.getCurrentTime(),
		this ,
		event.getRequestingElement(),
		readsearch(event.getAddressToReadFrom()))) ;
		}
		else if ( e.getEventType ( ) == Event.EventType.Cache_dWrite)
		{
		Cache_dWriteEvent event = (Cache_dWriteEvent)e;
		Simulator. getEventQueue().addEvent(
		new Cache_dResponseEvent (
			Clock.getCurrentTime(),
		this ,
		event.getRequestingElement(),
		event.getValue())) ;
		}
		else if(e.getEventType ( ) == Event.EventType.MemoryResponse) 
		{
			if( containingProcessor.getIF_OF().isOFbusy ( ) )
    		{
    		e.setEventTime(Clock.getCurrentTime()+ 1 ) ;
    		Simulator.getEventQueue().addEvent (e) ;
    		}
    		else
    		{
    		MemoryResponseEvent event = ( MemoryResponseEvent ) e ;
    		containingProcessor.getIF_OF().setInstruction(event.getValue( ));
    	
    		containingProcessor.getIF_Enable().setIFbusy ( false ) ;
    		}
		}
	}
}