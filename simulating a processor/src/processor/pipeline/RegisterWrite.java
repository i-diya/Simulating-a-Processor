package processor.pipeline;


import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_MA_LatchType EX_MA_Latch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, 
			IF_EnableLatchType iF_EnableLatch,IF_OF_LatchType iF_OF_Latch,EX_MA_LatchType eX_MA_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
	}
	
	public void performRW()
	{	
		/*if(containingProcessor.getOFUnit().hazardOF_EX != 1 && containingProcessor.getOFUnit().hazardOF_MA != 1 &&
				containingProcessor.getOFUnit().hazardOF_RW != 1)
			{
				return;
			}*/
		
		if(MA_RW_Latch.RW_enable == false) { IF_EnableLatch.setRW_bubble(true); }
		if(MA_RW_Latch.isRW_enable())
		{IF_EnableLatch.setRW_bubble(false);
			//TODO
			
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			if(MA_RW_Latch.isRW_busy() == true)
			{
				System.out.println("in correct loop");
				EX_MA_Latch.setMA_busy(true);
				IF_EnableLatch.setrun(false);
				return;
			}
		
		
			IF_EnableLatch.setisimm(MA_RW_Latch.getisimm());
			IF_EnableLatch.setrd(MA_RW_Latch.getrd());
			IF_EnableLatch.setrs1(MA_RW_Latch.getrs1());
			IF_EnableLatch.setrs2(MA_RW_Latch.getrs2());
			IF_EnableLatch.setopcode(MA_RW_Latch.getopcode());
		
			int rd = MA_RW_Latch.getrd();
			int ldresult = MA_RW_Latch.getLoadresult();
			int aluresult = MA_RW_Latch.getAluresult();
			int is_store = MA_RW_Latch.getIsstore();
			int is_load = MA_RW_Latch.getIsload();
			if(MA_RW_Latch.getopcode() == 6 || MA_RW_Latch.getopcode() == 7) {
				int g= MA_RW_Latch.getx();
				containingProcessor.getRegisterFile().setValue(31, g);
			}
		
			if(MA_RW_Latch.getopcode() == 29)
			{
				//int pc = containingProcessor.getRegisterFile().getProgramCounter() + 1;
				containingProcessor.getRegisterFile().setProgramCounter(MA_RW_Latch.getend()+1);
				IF_EnableLatch.setIF_enable(false);
				Simulator.setSimulationComplete(true);
			} 
			
			if(is_load == 1) { 
				containingProcessor.getRegisterFile().setValue(rd, ldresult);
				//System.out.println(containingProcessor.getRegisterFile().getValue(31) + " VALUE IN REGISTER 31 register write 1");
				//containingProcessor.setRegisterFile(r);
			}
			else if(MA_RW_Latch.getisbranch() == 0 && is_store == 0 && MA_RW_Latch.getopcode() != 24 
					&& MA_RW_Latch.getopcode() != 25
					&& MA_RW_Latch.getopcode() != 26
					&& MA_RW_Latch.getopcode() != 27
					&& MA_RW_Latch.getopcode() != 28) {
				containingProcessor.getRegisterFile().setValue(rd, aluresult);
				//System.out.println(containingProcessor.getRegisterFile().getValue(31) + " VALUE IN REGISTER 31 register write 2");
			}
			
			
			System.out.println(" OPCODE " + MA_RW_Latch.getopcode());
			System.out.println("rd in RW " + rd);
			//System.out.println("ldresult in RW " + ldresult);
			System.out.println("aluresult in RW " + aluresult);
			//System.out.println(IF_EnableLatch.getcheck()+ " NO OF OF STALLS");
			System.out.println(containingProcessor.getRegisterFile().getValue(31) + " VALUE IN REGISTER 31 register write");
			System.out.println(containingProcessor.getRegisterFile().getValue(30) + " VALUE IN REGISTER 30 register write");
			System.out.println(containingProcessor.getRegisterFile().getValue(5) + " VALUE IN REGISTER 5 register write");
			System.out.println(containingProcessor.getRegisterFile().getValue(7) + " VALUE IN REGISTER 7 register write");
		
			System.out.println("rw over \n\n\n");
			
			
			
			MA_RW_Latch.setRW_enable(false);
		//	EX_MA_Latch.setMA_enable(true);
			IF_EnableLatch.setIF_enable(true);
			IF_EnableLatch.setrun(true);
			//if(containingProcessor.getEXUnit().isOF() == 1) { IF_OF_Latch.setOF_enable(true); }
		}
	}

}
