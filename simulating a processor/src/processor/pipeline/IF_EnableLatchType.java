package processor.pipeline;

public class IF_EnableLatchType {
	
	boolean IF_enable;
	int first;
	boolean j = true;
	boolean bubble = false;
	int rd,rs1,rs2,isimm,opcode;
	int check = 0;
	boolean IFbusy = false;
	boolean run = false;

	
	public IF_EnableLatchType()
	{
		IF_enable = true;
	}
	public void setIFbusy(boolean j) {
		this.IFbusy = j;
	}
	public boolean isIFbusy() {
		return IFbusy;
	}
	public void setrun(boolean j1) {
		this.run = j1;
	}
	public boolean isrun() {
		return run;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setj(boolean iF_enable) {
		j = iF_enable;
	}
	public boolean isj() {
		return j;
	}

	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}
   
    public void j() {
    	j = true;
    }
    public void setRW_bubble(boolean Bubble) {
    	this.bubble = Bubble;
    }
    public boolean getRW_bubble() {
    	return bubble;
    }
    public void setrd(int rD){
		this.rd = rD;
	}
	public int getrd(){
		return rd;
	}
	public void setrs1(int r) {
		this.rs1 = r;
	}
	public int getrs1() {
		return rs1;
	}
	public void setrs2(int r2) {
		this.rs2 = r2;
	}
	public int getrs2() {
		return rs2;
	}
	public void setisimm(int is) {
		this.isimm = is;
	}
	public int getisimm() {
		return isimm;
	}
	public void setopcode(int op) {
		this.opcode =op;
	}
	public int getopcode() {
		return opcode;
	}
	public void setcheck(int c) {
		this.check = c;
	}
	public int getcheck() {
		return check;
	}

}
