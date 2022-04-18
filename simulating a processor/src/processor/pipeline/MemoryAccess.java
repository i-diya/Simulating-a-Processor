package processor.pipeline;
import java.lang.*;
import java.*;
import generic.*;
import processor.Clock;
import processor.Processor;
import processor.memorysystem.*;
import configuration.Configuration;
import processor.Clock;
import processor.Processor;

public class MemoryAccess implements Element{
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, 
			MA_RW_LatchType mA_RW_Latch,IF_OF_LatchType iF_OF_Latch,OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	public int check =0;
	public int busy = 0;
	public int y = 0;
	public int x = 0;
	
	public void performMA()
	{
		check++;
		System.out.println(check + "   CHECK  ");
		System.out.println(busy + "   BUSY  ");
		System.out.println(y + "  Y  ");
		//TODO
		//EX_MA_LatchType EX_MA_Latch = new EX_MvsA_LatchType();
		//if(EX_MA_Latch.isMA_enable()) {
		/*if(containingProcessor.getOFUnit().hazardOF_EX != 1 && containingProcessor.getOFUnit().hazardOF_MA != 1 &&
				containingProcessor.getOFUnit().hazardOF_RW != 1)
			{
				return;
			}*/
		if(EX_MA_Latch.IsMA_busy() == true && y == (Configuration.Cache_dLatency) && EX_MA_Latch.MA_enable == false && Clock.getCurrentTime() == EX_MA_Latch.getclk()+Configuration.Cache_dLatency && EX_MA_Latch.getlate() == 1)
		{
			System.out.println("in ma in correct loop");
			EX_MA_Latch.setlate(0);
		}
		if(EX_MA_Latch.IsMA_busy() == true && y == (Configuration.Cache_dLatency) && EX_MA_Latch.MA_enable == false && Clock.getCurrentTime() == EX_MA_Latch.getclk()+Configuration.Cache_dLatency+40 && EX_MA_Latch.getlate() == 2) {
			System.out.println("in ma in correct loop2");
			EX_MA_Latch.setlate(0);
		}
		if(EX_MA_Latch.IsMA_busy() == true && y == (Configuration.Cache_dLatency) && EX_MA_Latch.MA_enable == false && EX_MA_Latch.getlate() == 1) {
			
			EX_MA_Latch.setMA_busy(false);
			MA_RW_Latch.setRW_busy(false);
			y = 0;
			MA_RW_Latch.setMA_bubble(true); 
			OF_EX_Latch.setEX_busy(false);
			
		}
		
		if(EX_MA_Latch.IsMA_busy() == true && y == (40 + Configuration.Cache_dLatency) && EX_MA_Latch.MA_enable == false && EX_MA_Latch.getlate() == 2)
			{
			EX_MA_Latch.setMA_busy(false);
			MA_RW_Latch.setRW_busy(false);
			y = 0;
			MA_RW_Latch.setMA_bubble(true); 
			OF_EX_Latch.setEX_busy(false);
		}
		else if(EX_MA_Latch.IsMA_busy() == true && EX_MA_Latch.MA_enable == false) {
			MA_RW_Latch.setMA_bubble(true);  
			MA_RW_Latch.setRW_busy(true);
			y++;
			return ;
		}
		if(EX_MA_Latch.MA_enable == true) {
			MA_RW_Latch.setMA_bubble(false);
		}
		
		if(EX_MA_Latch.IsMA_busy() == true && EX_MA_Latch.MA_enable == false)
   		{
			busy ++;
			System.out.println("MA is busy 1\n\n");
			MA_RW_Latch.setMA_bubble(true); 
			//MA_RW_Latch.setRW_busy(true);
			EX_MA_Latch.setMA_busy(false);
			return ;
		}
		else {
			OF_EX_Latch.setEX_busy(false);
		//	MA_RW_Latch.setMA_bubble(false);
		}
		if(EX_MA_Latch.MA_enable == false)
		{
			MA_RW_Latch.setMA_bubble(true); 			
			
		}
		if(EX_MA_Latch.isMA_enable()) {
		/*	if(EX_MA_Latch.IsMA_busy() && y == (Configuration.Cache_dLatency) && EX_MA_Latch.getlate() == 1)
	   		{
				 EX_MA_Latch.setMA_busy(false);
				 MA_RW_Latch.setMA_bubble(false);
				 OF_EX_Latch.setEX_busy(false);
					MA_RW_Latch.setRW_busy(false);
				    y = 0;
				    OF_EX_Latch.setEX_busy(false);}
			else if(EX_MA_Latch.IsMA_busy()) {
				busy++;
				y++;
				MA_RW_Latch.setMA_bubble(false);
				System.out.println("MA is busy\n\n");
				OF_EX_Latch.setEX_busy(true);
				MA_RW_Latch.setRW_busy(true);
			return ;
			}*/
			if(EX_MA_Latch.IsMA_busy() && y == (40 + Configuration.Cache_dLatency) )
				//&& EX_MA_Latch.getlate() == 2)
	   		{
				 EX_MA_Latch.setMA_busy(false);
				 MA_RW_Latch.setMA_bubble(false);
				 OF_EX_Latch.setEX_busy(false);
					MA_RW_Latch.setRW_busy(false);
				    y = 0;
				    OF_EX_Latch.setEX_busy(false);}
			else if(EX_MA_Latch.IsMA_busy()) {
				busy++;
				y++;
				MA_RW_Latch.setMA_bubble(false);
				System.out.println("MA is busy\n\n");
				OF_EX_Latch.setEX_busy(true);
				MA_RW_Latch.setRW_busy(true);
			return ;
			}
			else { 
				
				
				OF_EX_Latch.setEX_busy(false);
			MA_RW_Latch.setRW_busy(false);}
			MA_RW_Latch.setMA_bubble(false);
		int op1 = EX_MA_Latch.getop1();
		int op2 = EX_MA_Latch.getop2();
		int aluresult = EX_MA_Latch.getaluresult();
		int is_load = EX_MA_Latch.getis_load();
		int is_store = EX_MA_Latch.getis_store();
		//MainMemory m = new MainMemory();
		int load_result=0;
		
		
		MainMemory m=containingProcessor.getMainMemory();
		
		
		if(is_load == 1) {
			System.out.println("IS LOAD  " + load_result);
			//System.out.println("latency =   "+Configuration.mainMemoryLatency);
			//System.out.println("bal bla  "+Clock.getCurrentTime()+Configuration.mainMemoryLatency);
			int load1[] =  new int[2];
			load1 = containingProcessor.getcache_d().readsearch(aluresult);
			if(load1[0] == 1)
			{
				 
				load_result = load1[1];
				Simulator.getEventQueue().addEvent(new Cache_dReadEvent(Clock.getCurrentTime()+Configuration.Cache_dLatency,
					this,
					containingProcessor.getcache_d(),
					aluresult));
				EX_MA_Latch.setMA_busy(true);
				EX_MA_Latch.setlate(1);
				EX_MA_Latch.setclk(Clock.getCurrentTime());
				System.out.println("ma_busy is set to true");
			}
			else 
			{
				load_result = load1[1];
				String k = Integer.toBinaryString(aluresult);
				containingProcessor.getcache_d().insert(k,load_result);
				//System.out.println("after insert search cached "+containingProcessor.getcache_d().search(aluresult));
				Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime()+Configuration.mainMemoryLatency+Configuration.Cache_dLatency,
						this,
						containingProcessor.getMainMemory(),
						aluresult));
					EX_MA_Latch.setMA_busy(true);
					EX_MA_Latch.setlate(2);
					EX_MA_Latch.setclk(Clock.getCurrentTime());
					System.out.println("ma_busy is set to true 1");
			}
			System.out.println("after insert search cached outside load loop "+containingProcessor.getcache_d().search(aluresult));
		}
		
		else if(is_store == 1) {
		//	System.out.println("IS STORE");
		
				String k = Integer.toBinaryString(aluresult);
				containingProcessor.getcache_d().write(k, op1);
		//		System.out.println("after insert search  "+containingProcessor.getcache_d().search(aluresult));
			Simulator.getEventQueue1().addEvent(new Cache_dWriteEvent(Clock.getCurrentTime()+Configuration.mainMemoryLatency+Configuration.Cache_dLatency,
					this,
					containingProcessor.getcache_d(),
					aluresult,op1));
			EX_MA_Latch.setMA_busy(true);
			EX_MA_Latch.setlate(2);
			EX_MA_Latch.setclk(Clock.getCurrentTime());
			System.out.println("ma_busy is set to true 2");
		
		
			System.out.println("after insert search store cached outside loop "+containingProcessor.getcache_d().search(aluresult));
		}
		
		
		//MA_RW_LatchType MA_RW_Latch = new MA_RW_LatchType();
		
		//System.out.println(EX_MA_Latch.getis_load());
		
		MA_RW_Latch.setAluresult(aluresult);
		MA_RW_Latch.setIsload(is_load);
		MA_RW_Latch.setIsstore(is_store);
		MA_RW_Latch.setLoadresult(load_result);
		MA_RW_Latch.setopcode(EX_MA_Latch.getopcode());
		MA_RW_Latch.setop1(EX_MA_Latch.getop1());
		MA_RW_Latch.setrd(EX_MA_Latch.getrd());
		MA_RW_Latch.setisbranch(EX_MA_Latch.getisbranch());
		MA_RW_Latch.setisimm(EX_MA_Latch.getisimm());
		MA_RW_Latch.setrs1(EX_MA_Latch.getrs1());
		MA_RW_Latch.setrs2(EX_MA_Latch.getrs2());
		
		/*System.out.println("aluresult in MA "+aluresult);
		System.out.println("isload in MA "+is_load);
		System.out.println("isstore in MA "+is_store);
		System.out.println("loadresult in MA "+load_result);
		System.out.println(containingProcessor.getRegisterFile().getValue(31) + " VALUE IN REGISTER 31 memory access");
		*/
		EX_MA_Latch.setMA_enable(false);
		
		//OF_EX_Latch.setEX_enable(true);
		MA_RW_Latch.setRW_enable(true);
		System.out.println("OPCODE " + EX_MA_Latch.getopcode());
		System.out.println("cache_d latency =   "+Configuration.Cache_dLatency);
		System.out.println("cache_i latency =   "+Configuration.Cache_iLatency);
		System.out.println("ma over \n\n\n");
		//if(containingProcessor.getEXUnit().isOF() == 1) { IF_OF_Latch.setOF_enable(true); }
		}
	}
	@Override
    public void handleEvent( Event e){
		if(MA_RW_Latch.isRW_busy())
		{
		e.setEventTime(Clock.getCurrentTime()+ 1 ) ;
		Simulator.getEventQueue1().addEvent (e) ;
		}
		else if(e.getEventType ( ) == Event.EventType.MemoryResponse)
		{
		MemoryResponseEvent event = ( MemoryResponseEvent ) e ;
		EX_MA_Latch.setMA_busy(false);
	//	MA_RW_Latch.setRW_busy(false);
		}
		else if(e.getEventType ( ) == Event.EventType.Cache_dResponse)
		{
			Cache_dResponseEvent event = ( Cache_dResponseEvent ) e ;
    		EX_MA_Latch.setMA_busy(false);
    	//	MA_RW_Latch.setRW_busy(false);
		}
    }
}
