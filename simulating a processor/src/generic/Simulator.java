package generic;

import processor.Clock;
import processor.Processor;
import processor.memorysystem.*;
import processor.pipeline.*;
import processor.pipeline.RegisterFile;
import java.io.*;
import java.lang.Math;

public class Simulator {
	
	IF_EnableLatchType IF_EnableLatch;
	/*static IF_OF_LatchType IF_OF;
	static OF_EX_LatchType OF_EX;
	static EX_MA_LatchType EX_MA;
	static MA_RW_LatchType MA_RW;*/
	static Processor processor;
	static EventQueue eventQueue;
	static EventQueue eventQueue1;
	static boolean simulationComplete;
	public static int noi = 0;
	public static void setnoi(int k)
	{
		noi = k;
	}
	public static int getnoi()
	{
		return noi;
	}
	public static EventQueue getEventQueue(){
		return eventQueue;
	}
	public static EventQueue getEventQueue1(){
		return eventQueue1;
	}
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
		eventQueue = new EventQueue();
		eventQueue1 = new EventQueue();
	}
	
	int firstinst1;
	
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		//RegisterFile x = new RegisterFile();
		
		processor.getRegisterFile().setValue(0, 0);
	    processor.getRegisterFile().setValue(1,65535);
	    processor.getRegisterFile().setValue(2,65535);
	    int firstinst = 0;
	    
	    
	       File f = new File(assemblyProgramFile);
	       
	        // Get the length of the file
	    //    System.out.println("length: "
	      //                     + f.length());
	       // MainMemory m = new MainMemory();
	        int add = 0;
	try { 
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(
	       assemblyProgramFile)));
	   for(int i=0; i<f.length(); i++)
	   { 
		  int h = dis.readInt();
	      if(i==0) { 
	    	  firstinst = h;
	          processor.getRegisterFile().setProgramCounter(firstinst);
	               }
	      else {
	      processor.getMainMemory().setWord(add,h);
	      add++;
	      
	      
	           }
	   }
	   
	   for (int j=0;j<f.length();j++) {
		   System.out.println(processor.getMainMemory().getWord(j));
	   }
	   dis.close();
	   
	  
	}
	catch(EOFException abc) {
	System.out.println(abc);
	}

	catch (IOException ioe) {
	System.out.println(ioe);
	}
	 	
	}
	

	
	public static void simulate()
	{
		//System.out.println("inside simulate");
				//IF_EnableLatch.isIF_enable());
		/*IF_OF_LatchType IF_OF_Latch;
		OF_EX_LatchType OF_EX_Latch1;
		OF_EX_LatchType OF_EX_Latch2;
		EX_MA_LatchType EX_MA_Latch;
		MA_RW_LatchType MA_RW_Latch;
		
		int conflict = 0;*/
		
		while(simulationComplete == false)
		{   
			
		//	Clock.incrementClock();
		//	if(simulationComplete == true) {break;}
			
		//	Clock.incrementClock();
		//	Clock.incrementClock();
			
		//	Clock.incrementClock();

			
			eventQueue1.processEvents();
			processor.getRWUnit().performRW();
			if(simulationComplete == true) { Clock.incrementClock(); break;}
			processor.getMAUnit().performMA();
			//OF_EX_Latch1 = processor.getOF_EX();
			processor.getEXUnit().performEX();
			eventQueue.processEvents();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			//processor.getlatch();
			Clock.incrementClock();
			//if(Clock.getCurrentTime() == 400) {break;}
			
			
		/*	IF_OF_Latch = processor.getIF_OF();
			OF_EX_Latch2 = processor.getOF_EX();
			EX_MA_Latch = processor.getEX_MA();
			MA_RW_Latch = processor.getMA_RW();*/
			
			/*if(OF_EX_Latch1.getrs1() == OF_EX_Latch2.getrd()) { conflict = 1; }
			if(OF_EX_Latch1.getrs1() == EX_MA_Latch.getrd()) { conflict = 1; }
			if(OF_EX_Latch1.getrs1() == MA_RW_Latch.getrd()) { conflict = 1; }
			if(OF_EX_Latch1.getrs2() == EX_MA_Latch.getrd() && OF_EX_Latch1.getIsimm() == 0) {conflict = 1;}
			if(OF_EX_Latch1.getrs2() == MA_RW_Latch.getrd() && OF_EX_Latch1.getIsimm() == 0) {conflict = 1;}*/
			
		}
		
		
		// TODO
		// set statistics
		long clockcount = Clock.getCurrentTime();
	//	long instructioncount = (Clock.getCurrentTime());
		Statistics statistics = new Statistics();
		statistics.setNumberOfCycles((int)clockcount);
	//	statistics.setNumberOfInstructions((int)instructioncount);
		System.out.println((int)clockcount + " -clockcycles_count");
		System.out.println("no of instructions :" + noi );
	//	System.out.println((int)instructioncount + " -instructions_count");
		
	}
	
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
