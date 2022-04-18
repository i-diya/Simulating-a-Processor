    package processor.pipeline;
    import java.lang.*;
    import generic.*;
    import configuration.Configuration;
import processor.Clock;
import java.*;
import processor.Processor;

    public class InstructionFetch implements Element{
    	
    	Processor containingProcessor;
    	IF_EnableLatchType IF_EnableLatch;
    	IF_OF_LatchType IF_OF_Latch;
    	EX_IF_LatchType EX_IF_Latch;
    	MA_RW_LatchType MA_RW_Latch;
    	
    	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, 
    			IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch,MA_RW_LatchType mA_RW_Latch)
    	{
    		this.containingProcessor = containingProcessor;
    		this.IF_EnableLatch = iF_EnableLatch;
    		this.IF_OF_Latch = iF_OF_Latch;
    		this.EX_IF_Latch = eX_IF_Latch;
    		this.MA_RW_Latch = mA_RW_Latch;
    	}

    	public long time =0;	
    	
    	public void performIF ()
    	{
    		/*if(containingProcessor.getOFUnit().hazardOF_EX == 1 && containingProcessor.getOFUnit().hazardOF_MA == 1 &&
    				containingProcessor.getOFUnit().hazardOF_RW == 1)
    			{
    				return;
    			}
    		*/
    		System.out.println("if_busy = " +IF_EnableLatch.isIFbusy());
    		int currentPC1;
   	 int currentPC2;
   	 int PC;
   	int[] newInstruction = new int[2];
   	 if(IF_EnableLatch.isIF_enable()) {
   		 System.out.println("CYCLE  : " + Clock.getCurrentTime());
   		if(IF_EnableLatch.isIFbusy())
   		{
   			System.out.println("IF is busy\n\n");
		return ;
		}
   		Simulator.setnoi(Simulator.getnoi() + 1);
   		time = Clock.getCurrentTime();
   		if(Clock.getCurrentTime() != 0)
    		{
    			
    			currentPC1 = containingProcessor.getRegisterFile().getProgramCounter();
    			/*System.out.println("isbranch =  " + EX_IF_Latch.getisbranch());
    			System.out.println("branchtarget =  " + EX_IF_Latch.getbranchtarget());
    			System.out.println("next two  " + IF_OF_Latch.isOF_enable() + " " + IF_OF_Latch.getcon());*/
    			if(EX_IF_Latch.getisbranch() == 1 && IF_OF_Latch.isOF_enable() == false && containingProcessor.getEXUnit().isbranch == 1) {
    				//containingProcessor.getEXUnit().isbranch =0;
    				containingProcessor.getRegisterFile().setProgramCounter( EX_IF_Latch.getbranchtarget());
        			
        			
        			int g = containingProcessor.getRegisterFile().getProgramCounter();
        			
        			currentPC1 = g;
        			IF_OF_Latch.setPC(g);
        			
        			int newInstruction1;
        			newInstruction = containingProcessor.getcache().readsearch(g);
        			if(newInstruction[0] == 1) {
        				
        			Simulator.getEventQueue().addEvent(new Cache_iReadEvent(Clock.getCurrentTime()+Configuration.Cache_iLatency,
        					this,
        					containingProcessor.getcache(),
        					containingProcessor.getRegisterFile().getProgramCounter()));
        			IF_EnableLatch.setIFbusy(true);}
        			else {newInstruction[1] = containingProcessor.getMainMemory().getWord(g);
        			String j = Integer.toBinaryString(g);
        			containingProcessor.getcache().insert(j,newInstruction[1]);
        			System.out.println("after insert search  "+containingProcessor.getcache().search(g));
        			Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime()+Configuration.mainMemoryLatency+Configuration.Cache_iLatency,
        					this,
        					containingProcessor.getMainMemory(),
        					g));
        			containingProcessor.getIF_Enable().setIFbusy(true);
        				
        			}
        			
        			System.out.println("current  PC " +  EX_IF_Latch.getbranchtarget());
    			}
    			else if(EX_IF_Latch.getisbranch() == 1 && IF_OF_Latch.isOF_enable() == false && IF_OF_Latch.getcon() == 0)
    			{containingProcessor.getRegisterFile().setProgramCounter( EX_IF_Latch.getbranchtarget());
    			
    			
    			int g = containingProcessor.getRegisterFile().getProgramCounter();
    			newInstruction = containingProcessor.getcache().readsearch(g);
    			currentPC1 = g;
    			IF_OF_Latch.setPC(g);
    			System.out.println("SEARCH IN CACHE   " + containingProcessor.getcache().search(g));
    			if(newInstruction[0] == 1) {
    				newInstruction = containingProcessor.getcache().readsearch(g);
    			Simulator.getEventQueue().addEvent(new Cache_iReadEvent(Clock.getCurrentTime()+Configuration.Cache_iLatency,
    					this,
    					containingProcessor.getcache(),
    					containingProcessor.getRegisterFile().getProgramCounter()));
    			IF_EnableLatch.setIFbusy(true);}
    			else {newInstruction[1] = containingProcessor.getMainMemory().getWord(g);
    			String j = Integer.toBinaryString(g);
    			containingProcessor.getcache().insert(j,newInstruction[1]);
    			System.out.println("after insert search  "+containingProcessor.getcache().search(g));
    			Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime()+Configuration.mainMemoryLatency+Configuration.Cache_iLatency,
    					this,
    					containingProcessor.getMainMemory(),
    					g));
    			containingProcessor.getIF_Enable().setIFbusy(true);
    				
    			}
    			
    			System.out.println("current  PC " +  EX_IF_Latch.getbranchtarget());}
    			else {containingProcessor.getRegisterFile().setProgramCounter(currentPC1 + 1);
    			
    			//System.out.println("next PC " + (currentPC1 + 1));
    			IF_OF_Latch.setPC(currentPC1 + 1);
    			int g  = currentPC1 + 1;
    			newInstruction = containingProcessor.getcache().readsearch(g);
    			if(newInstruction[0] == 1) {
    				
    			Simulator.getEventQueue().addEvent(new Cache_iReadEvent(Clock.getCurrentTime()+Configuration.Cache_iLatency,
    					this,
    					containingProcessor.getcache(),
    					containingProcessor.getRegisterFile().getProgramCounter()));
    			IF_EnableLatch.setIFbusy(true);}
    			else {newInstruction[1] = containingProcessor.getMainMemory().getWord(g);
    			String j = Integer.toBinaryString(g);
    			containingProcessor.getcache().insert(j,newInstruction[1]);
    			System.out.println("after insert search  "+containingProcessor.getcache().search(g));
    			Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime()+Configuration.mainMemoryLatency+Configuration.Cache_iLatency,
    					this,
    					containingProcessor.getMainMemory(),
    					g));
    			containingProcessor.getIF_Enable().setIFbusy(true);
    				
    			}}
    			
    			System.out.println(currentPC1 + 1  + " this is the current pc instruction fetched" );
    		}
    	
    		else { 
    			
    			currentPC2 = containingProcessor.getRegisterFile().getProgramCounter();
    			 System.out.println("hello");
            newInstruction = containingProcessor.getcache().readsearch(currentPC2);
            System.out.println("hello1");
           // System.out.println(currentPC2 + " this is the current pc instruction fetched FIRST ");
            IF_OF_Latch.setPC(currentPC2);
          
            if(newInstruction[0] == 1) {
				
			Simulator.getEventQueue().addEvent(new Cache_iReadEvent(Clock.getCurrentTime()+Configuration.Cache_iLatency,
					this,
					containingProcessor.getcache(),
					containingProcessor.getRegisterFile().getProgramCounter()));
			IF_EnableLatch.setIFbusy(true);}
			else {newInstruction[1] = containingProcessor.getMainMemory().getWord(currentPC2);
			String j = Integer.toBinaryString(currentPC2);
			containingProcessor.getcache().insert(j,newInstruction[1]);
			System.out.println("after insert search  "+containingProcessor.getcache().search(currentPC2));
			Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime()+Configuration.mainMemoryLatency+Configuration.Cache_iLatency,
					this,
					containingProcessor.getMainMemory(),
					currentPC2));
			containingProcessor.getIF_Enable().setIFbusy(true);
				
			}
            
			System.out.println("if_busy = " +IF_EnableLatch.isIFbusy());
    		}
    			    			
    			IF_OF_Latch.setInstruction(newInstruction[1]);
    			
    			String op = Integer.toBinaryString(newInstruction[1]);	
    			
    			//String ins = "";
    			int len = op.length();
    			if(len < 32) {
    				for(int i=len;i<32;i++) {
    					op = "0" + op;
    				}
    			
    			}
    			int opcode = Integer.parseUnsignedInt(op.substring(0,5),2);
    			
    			if(opcode == 29) {
    				MA_RW_Latch.setend( containingProcessor.getRegisterFile().getProgramCounter());
    				
    			}
    			
    			//System.out.println(opcode + " INSTRUCTION STARTED NEXT PC GOT PRINTED ABOVE");

    			//System.out.println(containingProcessor.getRegisterFile().getValue(31) + " VALUE IN REGISTER 31 instruction fetxh");
    			
    			IF_EnableLatch.setIF_enable(false);
    			IF_OF_Latch.setOF_enable(true);
    			System.out.println("if over \n\n\n");
    			//MA_RW_Latch.setRW_enable(true);
    			if(EX_IF_Latch.getisbranch() == 1) {
    				IF_OF_Latch.setOF_enable(true);
    			}
    		
    	}
    	}
    	@Override
        public void handleEvent( Event e){
    		if( IF_OF_Latch.isOFbusy ( ) )
    		{
    		e.setEventTime(Clock.getCurrentTime()+ 1 ) ;
    		Simulator.getEventQueue().addEvent (e) ;
    		System.out.println("yes adding events");
    		}
    		else if(e.getEventType ( ) == Event.EventType.MemoryResponse) 
    		{//System.out.println("inside this handl eevent loop 0");
    		//System.out.println(IF_OF_Latch.isOFbusy()  + " of stuff");
    			if( IF_OF_Latch.isOFbusy())
        		{
    				//System.out.println("inside this handl eevent loop ");
        		e.setEventTime(Clock.getCurrentTime()+ 1 ) ;
        		Simulator.getEventQueue().addEvent (e) ;
        		}
        		else
        		{
        		//	System.out.println("inside this handl eevent loop 2");
        		MemoryResponseEvent event = ( MemoryResponseEvent ) e ;
        		IF_OF_Latch.setInstruction(event.getValue( ));
        	
        		IF_EnableLatch.setIFbusy ( false ) ;
        		}
    		}
    		else
    		{
    		Cache_iResponseEvent event = ( Cache_iResponseEvent ) e ;
    		IF_OF_Latch.setInstruction(event.getValue()[1]);
    	
    		IF_EnableLatch.setIFbusy ( false ) ;
    		}
        }
    }