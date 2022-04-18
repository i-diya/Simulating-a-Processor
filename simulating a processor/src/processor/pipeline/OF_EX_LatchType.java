package processor.pipeline;

public class OF_EX_LatchType {
int imm;
int op1;
int op2;
int ismm;
int sig;
int branch;
int rd;
int isbranch;
int rs1;
int rs2;
int OF_stall = 0;
int wrong = 0;
boolean EX_busy = false;

boolean EX_enable;

public OF_EX_LatchType()
{
EX_enable = false;
}

public boolean isEX_enable() {
return EX_enable;
}

public void setEX_enable(boolean eX_enable) {
EX_enable = eX_enable;
}
public void setImm(int imm) {
this.imm = imm;
}
public int getImm() {
return imm;
}
public void setOp1(int op1) {
this.op1 = op1;
}
public int getOp1() {
return op1;
}
public void setOp2(int op2) {
this.op2 = op2;
}
public int getOp2() {
return op2;
}
public void setIsimm(int ismm) {
this.ismm = ismm;
}
public int getIsimm() {
return ismm;
}
public void setSig(int sg) {
this.sig = sg;
}
public int getsig() {
return sig;
}

public void setbranchtarget(int br) {
	this.branch = br;
}
public int getbranchtarget() {
	return branch;
}
public void setrd(int RD) {
	this.rd = RD;
}
public int getrd() {
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
public void setisbranch(int is_br){
	this.isbranch = is_br;
}
public int getisbranch(){
	return isbranch ;
}
public void setOF_stall(int num) {
	this.OF_stall = num;
}
public int getOF_stall() {
	return OF_stall;
}
public void setwrong(int W) {
	this.wrong = W;
}
public int getwrong() {
	return wrong;
}
public void setEX_busy(boolean s) {
	this.EX_busy = s;
}
public boolean IsEX_busy() {
	return EX_busy;
}



}