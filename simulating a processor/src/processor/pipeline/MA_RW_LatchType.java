package processor.pipeline;

public class MA_RW_LatchType {
	
	int isload;
	int isstore;
	int loadresult;
	int aluresult;
	int opcode;
	int op1;
	int rd;
	int isbranch;
	int isimm;
	int rs1;
	int rs2;
	int end;
	boolean bubble = false;
	int x;
	boolean RW_busy = false;
	
	boolean RW_enable;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
	}
	public boolean isRW_busy() {
		return RW_busy;
	}

	public void setRW_busy(boolean a) {
    	this.RW_busy = a;
    }
	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}
	public void setIsload(int isloaD) {
		this.isload = isloaD;
	}
	public int getIsload() {
		return isload;
	}
	public void setIsstore(int isStore) {
		this.isstore = isStore;
	}
	public int getIsstore() {
		return isstore;
	}
	public void setLoadresult(int lr) {
		this.loadresult = lr;
	}
	public int getLoadresult() {
		return loadresult;
	}
	public void setAluresult(int ar) {
		this.aluresult = ar;
	}
	public int getAluresult() {
		return aluresult;
	}
	public void setopcode(int opc) {
		this.opcode = opc;
	}
	public int getopcode() {
		return opcode;
	}
	public void setop1(int Op1) {
		this.op1 = Op1;
	}
	public int getop1() {
		return op1;
	}
	public void setrd(int RD) {
		this.rd = RD;
	}
	public int getrd() {
		return rd;
	}
	public void setisbranch(int Isbranch) {
		this.isbranch = Isbranch;
	}
	public int getisbranch() {
		return isbranch;
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
    public void setMA_bubble(boolean Bubble) {
    	this.bubble = Bubble;
    }
    public boolean getMA_bubble() {
    	return bubble;
    }
    public void setend(int e) {
    	this.end = e;
    }
    public int getend() {
    	return end;
    }
    public void setx(int v) {
    	this.x = v;
    }
    public int getx() {
    	return x;
    }
}
