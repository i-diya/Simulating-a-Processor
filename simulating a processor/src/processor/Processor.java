package processor;

import processor.memorysystem.MainMemory;
import processor.pipeline.EX_IF_LatchType;
import processor.pipeline.EX_MA_LatchType;
import processor.pipeline.Execute;
import processor.pipeline.IF_EnableLatchType;
import processor.pipeline.IF_OF_LatchType;
import processor.pipeline.InstructionFetch;
import processor.pipeline.MA_RW_LatchType;
import processor.pipeline.MemoryAccess;
import processor.pipeline.OF_EX_LatchType;
import processor.pipeline.OperandFetch;
import processor.pipeline.RegisterFile;
import processor.pipeline.RegisterWrite;
import processor.memorysystem.*;

public class Processor {
	
	RegisterFile registerFile;
	MainMemory mainMemory;
	Cache_i Cache_il; 
	Cache_d Cache_id;
	
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	InstructionFetch IFUnit;
	OperandFetch OFUnit;
	Execute EXUnit;
	MemoryAccess MAUnit;
	RegisterWrite RWUnit;
	
	public Processor()
	{
		registerFile = new RegisterFile();
		mainMemory = new MainMemory();
		
		
		IF_EnableLatch = new IF_EnableLatchType();
		IF_OF_Latch = new IF_OF_LatchType();
		OF_EX_Latch = new OF_EX_LatchType();
		EX_MA_Latch = new EX_MA_LatchType();
		EX_IF_Latch = new EX_IF_LatchType();
		MA_RW_Latch = new MA_RW_LatchType();
		Cache_il = new Cache_i(this); 
		Cache_id = new Cache_d(this); 
		IFUnit = new InstructionFetch(this, IF_EnableLatch, IF_OF_Latch, EX_IF_Latch,MA_RW_Latch);
		OFUnit = new OperandFetch(this, IF_OF_Latch, OF_EX_Latch,EX_MA_Latch,MA_RW_Latch,IF_EnableLatch);
		EXUnit = new Execute(this, OF_EX_Latch, EX_MA_Latch, EX_IF_Latch,IF_OF_Latch,MA_RW_Latch,IF_EnableLatch);
		MAUnit = new MemoryAccess(this,EX_MA_Latch, MA_RW_Latch,IF_OF_Latch,OF_EX_Latch);
		RWUnit = new RegisterWrite(this, MA_RW_Latch, IF_EnableLatch,IF_OF_Latch,EX_MA_Latch);
	}
	
	public void printState(int memoryStartingAddress, int memoryEndingAddress)
	{
		System.out.println(registerFile.getContentsAsString());
		
		System.out.println(mainMemory.getContentsAsString(memoryStartingAddress, memoryEndingAddress));		
	}

	public RegisterFile getRegisterFile() {
		return registerFile;
	}

	public void setRegisterFile(RegisterFile registerFile) {
		this.registerFile = registerFile;
	}

	public MainMemory getMainMemory() {
		return mainMemory;
	}

	public void setMainMemory(MainMemory mainMemory) {
		this.mainMemory = mainMemory;
	}
	public Cache_i getcache() {
		return Cache_il;
	}

	public void setcache(Cache_i Cache_il) {
		this.Cache_il = Cache_il;
	}

	public Cache_d getcache_d() {
		return Cache_id;
	}
	public void setcache_d(Cache_d cache) {
		this.Cache_id = cache;
	}
	public InstructionFetch getIFUnit() {
		return IFUnit;
	}

	public OperandFetch getOFUnit() {
		return OFUnit;
	}

	public Execute getEXUnit() {
		return EXUnit;
	}

	public MemoryAccess getMAUnit() {
		return MAUnit;
	}

	public RegisterWrite getRWUnit() {
		return RWUnit;
	}
	public IF_OF_LatchType getIF_OF(){
		return IF_OF_Latch;
	}
	public OF_EX_LatchType getOF_EX() {
		return OF_EX_Latch;
	}
	public EX_MA_LatchType getEX_MA() {
		return EX_MA_Latch;
	}
	public MA_RW_LatchType getMA_RW() {
		return MA_RW_Latch;
	}
	public IF_EnableLatchType getIF_Enable() {
		return IF_EnableLatch;
	}
}
