package processor.pipeline;

public class EX_IF_LatchType {

	int isbranch;
	boolean IFEX_enable;
	int branch;
	public EX_IF_LatchType()
	{
		IFEX_enable = false;
	}
	
	public boolean isIFEX_enable() {
		return IFEX_enable;
	}

	public void setIFEX_enable(boolean IF_enable) {
		this.IFEX_enable = IF_enable;
	}
	public void setisbranch(int is_br){
		this.isbranch = is_br;
	}
	public int getisbranch(){
		return isbranch ;
	}
	public void setbranchtarget(int br) {
		this.branch = br;
	}
	public int getbranchtarget() {
		return branch;
	}

}
