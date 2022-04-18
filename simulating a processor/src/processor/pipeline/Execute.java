package processor.pipeline;
import generic.*;
import processor.Clock;
import processor.Processor;

public class Execute {
Processor containingProcessor;
OF_EX_LatchType OF_EX_Latch;
EX_MA_LatchType EX_MA_Latch;
EX_IF_LatchType EX_IF_Latch;
IF_OF_LatchType IF_OF_Latch;
MA_RW_LatchType MA_RW_Latch;
IF_EnableLatchType IF_Enable_Latch;
public static int isbranch=0;

public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, 
		EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch,
		IF_OF_LatchType iF_OF_Latch,MA_RW_LatchType mA_RW_Latch,IF_EnableLatchType iF_Enable_Latch)
{
this.containingProcessor = containingProcessor;
this.OF_EX_Latch = oF_EX_Latch;
this.EX_MA_Latch = eX_MA_Latch;
this.EX_IF_Latch = eX_IF_Latch;
this.IF_OF_Latch = iF_OF_Latch;
this.MA_RW_Latch = mA_RW_Latch;
this.IF_Enable_Latch = iF_Enable_Latch;
}
int a = 0;
public int isOF() {
	return a;
}
public static int iF =0 ;
public void performEX()
{
System.out.println("prev isbranch =  " + isbranch +" in EX\n\n");

/*if(containingProcessor.getOFUnit().hazardOF_EX == 1 && containingProcessor.getOFUnit().hazardOF_MA == 1 &&
	containingProcessor.getOFUnit().hazardOF_RW == 1)
{
	return;
}*/


if(OF_EX_Latch.EX_enable == false) { EX_MA_Latch.setEX_bubble(true); }


if(OF_EX_Latch.isEX_enable()) {
	
	if(OF_EX_Latch.IsEX_busy()) {
		IF_OF_Latch.setOFbusy(true);
		System.out.println("EX is busy\n\n");
		return;
	}
	else {IF_OF_Latch.setOFbusy(false);}
	if(OperandFetch.flag == Clock.getCurrentTime()+1 && IF_Enable_Latch.isIFbusy() == true) {return;}
EX_MA_Latch.setEX_bubble(false);

int isimm = OF_EX_Latch.getIsimm();
//System.out.println(isimm+ " isimm in EX");
//System.out.println(OF_EX_Latch.getImm()+ " imm in EX");
int num2;
if(OF_EX_Latch.getIsimm() == 1) {num2 = OF_EX_Latch.getImm();}
else {num2 = OF_EX_Latch.getOp2();}
int num1 = OF_EX_Latch.getOp1();
int opcode = OF_EX_Latch.getsig();
int res=0;
int op2 = OF_EX_Latch.getOp2();
int op1 = OF_EX_Latch.getOp1();


int isbra=0;
//System.out.println(OF_EX_Latch.getsig()+ " opcode get sig to check 7");
if(OF_EX_Latch.getsig() == 0|| OF_EX_Latch.getsig() == 1) {res = num1 + num2;}//System.out.println(num1 + " num1 " + num2 +" num2 " + res );}
if(OF_EX_Latch.getsig() == 2|| OF_EX_Latch.getsig() == 3) {res = num1 - num2;}
if(OF_EX_Latch.getsig() == 4 || OF_EX_Latch.getsig() == 5) {res = (num1 * num2);}
if(OF_EX_Latch.getsig() == 6|| OF_EX_Latch.getsig() == 7) {res = (num1 / num2); System.out.println(num1 %num2 + "x31");
MA_RW_Latch.setx((num1%num2));}

if(OF_EX_Latch.getsig() == 8|| OF_EX_Latch.getsig() == 9){res = (num1 & num2);}
if(OF_EX_Latch.getsig() == 10|| OF_EX_Latch.getsig() == 11) {res = (num1 | num2);}
if(OF_EX_Latch.getsig() == 12|| OF_EX_Latch.getsig() == 13) {res = (num1 ^ num2);}
if(OF_EX_Latch.getsig() == 14|| OF_EX_Latch.getsig() == 15) {if(num1 < num2) {res = 1;}else{res = 0;}}
if(OF_EX_Latch.getsig() == 25) {if(num1 == num2) {isbra = 1;}}
if(OF_EX_Latch.getsig() == 26) {if(num1 != num2) {isbra = 1;}}
if(OF_EX_Latch.getsig() == 27) {if(num1 < num2) {isbra = 1;}}
if(OF_EX_Latch.getsig() == 28) {if(num1 > num2) {isbra = 1;}}
if(OF_EX_Latch.getsig() == 24) {isbra = 1;}
if(OF_EX_Latch.getsig() == 22) {System.out.println("IN EXECUTE IN LOAD "+num1+" "+OF_EX_Latch.getImm());res = num1+OF_EX_Latch.getImm();}
if(OF_EX_Latch.getsig() == 23) {res = OF_EX_Latch.getOp2()+OF_EX_Latch.getImm();}
if(OF_EX_Latch.getsig() == 16|| OF_EX_Latch.getsig() == 17) {res = num1 << num2;}
if(OF_EX_Latch.getsig() == 18|| OF_EX_Latch.getsig() == 19) {res = num1 >>> num2;}
if(OF_EX_Latch.getsig() == 20|| OF_EX_Latch.getsig() == 21) {res = num1 >> num2;}

System.out.println(num1 + " num1 " + num2 +" num2 " + res );
System.out.println("value od x31 =  " + containingProcessor.getRegisterFile().getValue(31));
System.out.println("value od x30 =  " + containingProcessor.getRegisterFile().getValue(30 ));
System.out.println(" values of x5 x7   " + containingProcessor.getRegisterFile().getValue(5) + "  " + containingProcessor.getRegisterFile().getValue(7));
//System.out.println(res + " " + num1 + " " + OF_EX_Latch.getImm());

//EX_MA_LatchType EX_MA_Latch = new EX_MA_LatchType();

EX_MA_Latch.setaluresult(res);
EX_MA_Latch.setisbranch(isbra);
if(opcode == 22) { EX_MA_Latch.setis_load(1);} else {EX_MA_Latch.setis_load(0);}
if(opcode == 23) {EX_MA_Latch.setis_store(1);} else {EX_MA_Latch.setis_store(0);}
EX_MA_Latch.setop2(op2);
EX_MA_Latch.setop1(op1);
EX_MA_Latch.setopcode(OF_EX_Latch.getsig());
EX_MA_Latch.setrd(OF_EX_Latch.getrd());
int r = OF_EX_Latch.getbranchtarget();
EX_IF_Latch.setbranchtarget(r);
EX_IF_Latch.setisbranch(isbra);
OF_EX_Latch.setisbranch(isbra);
EX_MA_Latch.setisimm(OF_EX_Latch.getIsimm());
EX_MA_Latch.setrs1(OF_EX_Latch.getrs1());
EX_MA_Latch.setrs2(OF_EX_Latch.getrs2());


//System.out.println("res in EX " + res);
System.out.println("branchtarget in EX " + r);
System.out.println("isbra in EX " + isbra);
isbranch = isbra;
//System.out.println(containingProcessor.getRegisterFile().getValue(31) + " VALUE IN REGISTER 31 execute ");


if(isbra == 1) {
	   //IF_OF_Latch.setOF_enable(false);
	   a =1;
	   EX_MA_Latch.setMA_enable(true);
	   OF_EX_Latch.setEX_enable(false);
	   OF_EX_Latch.setwrong(OF_EX_Latch.getwrong() + 1);
	}
	else {
    
	OF_EX_Latch.setEX_enable(true);
	EX_MA_Latch.setMA_enable(true);
	}
/*OF_EX_Latch.setEX_enable(true);
EX_MA_Latch.setMA_enable(true);*/
//if(containingProcessor.getEXUnit().isOF() == 1) { IF_OF_Latch.setOF_enable(true); }
System.out.println("OPCODE " + opcode);
System.out.println(OF_EX_Latch.getwrong());
OF_EX_Latch.setEX_enable(false);

System.out.println("EX OVER \n");

	}
}


}