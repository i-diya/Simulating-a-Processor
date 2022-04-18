package processor.pipeline;

public class EX_MA_LatchType {
	
	int aluresult;
	int isbranch;
	int op1;
	int op2;
	int is_store;
	int is_load;
	int opcode;
	int rd;
	int rs1;
	int rs2;
	int isimm;
	boolean bubble = false;
	boolean MA_busy = false;
	int late = 0;
	long clk;
	
	
	boolean MA_enable;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}
	public void setlate(int res){
		this.late = res;
	}
	public int getlate(){
		return late	;
	}
	public void setclk(long res){
		this.clk = res;
	}
	public long getclk(){
		return clk;
	}
	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}
	public void setaluresult(int res){
		this.aluresult = res;
	}
	public int getaluresult(){
		return aluresult ;
	}
	public void setop1(int Op1){
		this.op1 = Op1;
	}
	public int getop1(){
		return op1 ;
	}
	public void setop2(int Op2){
		this.op2 = Op2;
	}
	public int getop2(){
		return op2 ;
	}
	public void setisbranch(int is_br){
		this.isbranch = is_br;
	}
	public int getisbranch(){
		return isbranch ;
	}
	public void setis_store(int isstore){
		this.is_store = isstore;
	}
	public int getis_store(){
		return is_store ;
	}
	public void setis_load(int Isload){
		this.is_load = Isload;
	}
	public int getis_load(){
		return is_load ;
	}
	public void setopcode(int opc){
		this.opcode = opc;
	}
	public int getopcode(){
		return opcode ;
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
    public void setEX_bubble(boolean Bubble) {
    	this.bubble = Bubble;
    }
    public boolean getEX_bubble() {
    	return bubble;
    }
    public void setMA_busy(boolean a) {
    	this.MA_busy = a;
    }
    public boolean IsMA_busy() {
    	return MA_busy;
    }
}
