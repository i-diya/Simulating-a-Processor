package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	int instruction;
	int pc;
	int con;
	boolean OFbusy=false;

	
	public IF_OF_LatchType()
	{
		OF_enable = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}
	public void setOFbusy(boolean j) {
		this.OFbusy = j;
	}
	public boolean isOFbusy() {
		return OFbusy;
	}


	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}
	public int getPC() {
		return pc;
	}

	public void setPC(int in) {
		this.pc = in;
	}
	public int getcon() {
		return con;
	}

	public void seton(int cin) {
		this.con = cin;
	}

}
