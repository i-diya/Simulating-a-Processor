package processor.pipeline;

import processor.Clock;
import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_EnableLatchType IF_Enable_Latch;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	static long flag;
	public static int hazardOF_MA =0;
	public static int hazardOF_EX = 0;
	public static int hazardOF_RW=0;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch,
			EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch , IF_EnableLatchType iF_Enable_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_Enable_Latch = iF_Enable_Latch;
	}
	public static long getint() {
		return flag;
	}
	public static int binarytodecimal(String num)
	{
		int dec_value = 0;
		int base = 1;  
		int len = num.length(); 
	
		for (int i = len - 1; i >= 0; i--)
		{ 
			if (num.charAt(i) == '1') 
    				{ dec_value += base; } 
				base = base * 2;
		}
		return dec_value;
	}
	public char flip(char c) {
        return (c == '0') ? '1' : '0';
    }
	
	  public int twosCompliment(String bin) {
	        String twos = "", ones = "";

	        for (int i = 0; i < bin.length(); i++) {
	            ones += flip(bin.charAt(i));
	        }
	        int number0 = Integer.parseInt(ones, 2);
	        StringBuilder builder = new StringBuilder(ones);
	        boolean b = false;
	        for (int i = ones.length() - 1; i > 0; i--) {
	            if (ones.charAt(i) == '1') {
	                builder.setCharAt(i, '0');
	            } else {
	                builder.setCharAt(i, '1');
	                b = true;
	                break;
	            }
	        }
	        if (!b)
	            builder.append("1", 0, 7);

	        twos = builder.toString();

	        return binarytodecimal(twos)*(-1);
	    }
	
	//load st branch bgt beq bne blt
	public int[] opcode(int i)
	{       //System.out.println(i.substring(0, 5)+ " substring given") ;
		int[] op  = new int[8];;
	              for(int u=0;u<8;u++) {op[u]=0;}
	         if(i == 24) { op[3] = 1;}
	          if(i == 27) {op[7] = 1;op[3]=1;}
	          if(i== 26) {op[6] = 1;op[3]=1;}
	        if(i == 25) {op[5] = 1;op[3]=1;
	        }
	       if(i == 28) {op[4] = 1;op[3] = 1;}
	       if(i == 22) {op[1] = 1;}
	          if(i == 23) {op[2] = 1;}
	          return op;
	         
	       
	}
	
	
	
	public void performOF()
	{
		
		
		if(EX_MA_Latch.getisbranch() == 1 && EX_MA_Latch.getEX_bubble() == true && MA_RW_Latch.getMA_bubble() == false) {
		IF_OF_Latch.setOF_enable(false);
		IF_OF_Latch.seton(0);
	}
		IF_Enable_Latch.setIF_enable(true);
		/*if(containingProcessor.getOFUnit().hazardOF_EX == 1 && containingProcessor.getOFUnit().hazardOF_MA == 1 &&
				containingProcessor.getOFUnit().hazardOF_RW == 1)
			{
				return;
			}*/
		if(containingProcessor.getEXUnit().isbranch == 1) {
			containingProcessor.getEXUnit().isbranch = 0;
			IF_OF_Latch.setOF_enable(false);
		}
		
		if(IF_OF_Latch.isOF_enable())
		{ 
			if(IF_Enable_Latch.isIFbusy() && Clock.getCurrentTime()>0 ) {
			System.out.println("in crap");
			return;
		}
		System.out.println("not in crap");
			if(IF_OF_Latch.isOFbusy()) {
				IF_Enable_Latch.setIFbusy(true);
				System.out.println("OF is busy\n\n");
				return;
			}
			//else { IF_Enable_Latch.setIFbusy(false);}
			if(EX_MA_Latch.getEX_bubble() == true && EX_MA_Latch.getisbranch() == 1) {
			OF_EX_Latch.setEX_enable(true);
			}
			//TODO
			//OF_EX_LatchType OF_EX_Latch = new OF_EX_LatchType();
			// if(IF_Enable_Latch.isIFbusy() == true ) { flag = Clock.getCurrentTime(); return; }
			int inst = IF_OF_Latch.getInstruction();
			String ins = Integer.toBinaryString(inst);
			//String ins = "";
			int len = ins.length();
			if(len < 32) {
				for(int i=len;i<32;i++) {
					ins = "0" + ins;
				}
			}
		//	System.out.println(binarytodecimal(ins.substring(10,15)));
			
			int a;
			int b;
			
			String opco1 = ins.substring(0,5);
			int opco = binarytodecimal(ins.substring(0,5));
			String h = ins.substring(15);
			String e = ins.substring(10);
			int imm;
			//System.out.println(h + " STRING THAT IS + OR - BGT BLT BEQ BNE");
			if(Integer.parseInt(h.substring(0, 1)) == 1) {imm = twosCompliment(h);}//System.out.println("NEGATIVE NUMBER");}
			else{ imm = Integer.parseInt(ins.substring(15), 2);}//System.out.println("POSITIVE NUMBER");}
			
			//int imm =  twocompl_to_decimal(ins.substring(15));
			//int imm2 =  twocompl_to_decimal(ins.substring(10));
			int imm2;
			//System.out.println(e + " STRING THAT IS + OR -");
			if(Integer.parseInt(e.substring(0, 1)) == 1) {imm2 = twosCompliment(e);}//System.out.println("NEGATIVE NUMBER");}
			else{ imm2 = Integer.parseInt(ins.substring(10), 2);}//System.out.println("POSITIVE NUMBER");}
			int rs1 = binarytodecimal(ins.substring(5,10));
			int rs2 = binarytodecimal(ins.substring(10,15));
			int rd_1 = binarytodecimal(ins.substring(15,20));
			int rd_2 = binarytodecimal(ins.substring(10,15));
			int rd_3 = binarytodecimal(ins.substring(5,10));
			int rd = rd_1;
			
			int[] signal = new int[8];
			signal = opcode(opco);
			for(int y=0;y<8;y++) {
			//System.out.println(signal[y] + " this is the aRRAY for opcode " + opco + " " + opco1);
			}
			
			int offset ;//new pc if branch/jmp is there
			int op1=0;
			int op2 =0;
			int bt = IF_OF_Latch.getPC(); 
		//	System.out.println("PC IN OF " + bt);
			//System.out.println(imm2 + " IMMEDIATE VALUE IN JUMP SEE LAST INST");
			
			if(signal[3] == 1 && signal[4] == 0 && signal[5] == 0 && signal[6] == 0 && signal[7] == 0) 
				{ offset = imm2;
				//System.out.println(ins.substring(10) + "JUMP SUBSTRING");
				bt=bt+offset; System.out.println("after AADDING " + bt);}
			else if(signal[4] == 1 || signal[5] == 1 || signal[6] == 1 || signal[7] == 1) 
				{ offset = imm; bt=bt+offset; 
				//System.out.println("beq bgt blt bne branch " + bt);
				}	
		
			
			
			if(signal[1] == 1) {  op2 = containingProcessor.getRegisterFile().getValue(rd_2);
									b = rd_2;}
			else if(signal[2] == 1) { op2 = containingProcessor.getRegisterFile().getValue(rd_2);
										b = rd_2;}
			else if(signal[4] == 1 || signal[5] == 1 || signal[6] == 1 || signal[7] == 1) 
			{ op2 = containingProcessor.getRegisterFile().getValue(rd_2);
				b = rd_2; }
			else { op2 = containingProcessor.getRegisterFile().getValue(rs2);
					b = rs2; }
			
			op1 = containingProcessor.getRegisterFile().getValue(rs1);
			a = rs1;
		//	System.out.println("              " +containingProcessor.getRegisterFile().getValue(rs2));
			
			
			int isimm=0;
			if(opco == 1 || opco == 3 || opco == 5 || opco == 7 || opco == 9 || opco == 11 ||
			   opco == 13 || opco == 15 || opco == 17 || opco == 19 || opco == 21)
			{
				isimm  = 1;
				rd = rd_2;
			}
			if(opco == 22 || opco == 23) {rd = rd_2;}
			
			
			int conflictOF_MA =0;
			int conflictOF_EX = 0;
			int conflictOF_RW=0;
			
			
			if(Clock.getCurrentTime() != 1)
    		{
			//detecting conflicts for data hazards
			
			if(EX_MA_Latch.bubble == false) {
				
			if(opco == 29 || opco == 24) { System.out.println("1st loop");conflictOF_EX = 0; }
			else if(OF_EX_Latch.getsig() == 25 || OF_EX_Latch.getsig() == 26 || OF_EX_Latch.getsig() == 27
					|| OF_EX_Latch.getsig() == 28)
			{ System.out.println("2nd loop");conflictOF_EX = 0; }
			else if(OF_EX_Latch.getsig() == 6 || OF_EX_Latch.getsig() == 7) {
				if(opco == 22) {
					if( rs1 == 31 || OF_EX_Latch.getrd() == rs1) { conflictOF_EX =1; }
				}
				else if (opco == 23) {
					if( rs1 == 31 ||  rd == 31 || OF_EX_Latch.getrd() == rd || OF_EX_Latch.getrd() == rs1) { conflictOF_EX= 1; }
				}
				else if(opco == 25 || opco == 26 || opco == 27 || opco == 28) {
					if( rs1 == 31 ||  rs2 == 31 || OF_EX_Latch.getrd() == rs1 || OF_EX_Latch.getrd() == rs2) { conflictOF_EX = 1; }
				}
				else if(isimm == 0) {
					if( rs1 == 31 || rs2 == 31 || OF_EX_Latch.getrd() == rs1 || OF_EX_Latch.getrd() == rs2) { conflictOF_EX = 1; }
				}
				else if(isimm ==1 ) {
					if(rs1 == 31 || OF_EX_Latch.getrd() == rs1) { conflictOF_EX = 1; }
				}
				
			}
			else if(OF_EX_Latch.getsig() != 25 && OF_EX_Latch.getsig() != 26 && OF_EX_Latch.getsig() != 27
					&& OF_EX_Latch.getsig() != 28 && 
					OF_EX_Latch.getsig() != 23 && OF_EX_Latch.getsig() != 24)
			{	System.out.println("3rd loop");
				if(opco == 22) {
					if(OF_EX_Latch.getrd() == rs1) { conflictOF_EX =1; }
				}
				else if (opco == 23) {
					if(OF_EX_Latch.getrd() == rd || OF_EX_Latch.getrd() == rs1) { conflictOF_EX = 1; }
				}
				else if(opco == 25 || opco == 26 || opco == 27 || opco == 28) {
					if(OF_EX_Latch.getsig() == 26 || OF_EX_Latch.getsig() == 27 || OF_EX_Latch.getsig() == 25 || OF_EX_Latch.getsig() == 28 || EX_MA_Latch.getisbranch() == 1) {
						conflictOF_EX = 0;
					}
					else{if(OF_EX_Latch.getrd() == rs1 || OF_EX_Latch.getrd() == rs2) { conflictOF_EX = 1; }}
				}
				else if(isimm == 0) { 
					
					if(OF_EX_Latch.getrd() == rs1 || OF_EX_Latch.getrd() == rs2) {conflictOF_EX = 1; }
				}
				else if(isimm ==1 ) {
					System.out.println(rs1 + " "+ OF_EX_Latch.getrd() + " hello world conflict");
					if(OF_EX_Latch.getrd() == rs1) { conflictOF_EX = 1; }
				}
				
			}
			else {conflictOF_EX = 0;}
			}
			
			if(Clock.getCurrentTime() > 3 && IF_Enable_Latch.bubble == false && IF_Enable_Latch.isrun() == true) {
				System.out.println(IF_Enable_Latch.bubble + " BUBBLE RW");
			if(opco == 29 || opco == 24) { conflictOF_RW = 0; }
			else if(IF_Enable_Latch.getopcode() == 25 || IF_Enable_Latch.getopcode() == 26 || IF_Enable_Latch.getopcode() == 27
					|| IF_Enable_Latch.getopcode() == 28)
			{ conflictOF_RW = 0; }
			else if(IF_Enable_Latch.getopcode() == 6 || IF_Enable_Latch.getopcode() == 7) {
				if(opco == 22) {
					if( rd == 31 || IF_Enable_Latch.getrd() == rs1) { conflictOF_RW =1; }
				}
				else if (opco == 23) {
					if( rd == 31 ||  rs1 == 31 || IF_Enable_Latch.getrd() == rd || IF_Enable_Latch.getrd() == rs1 ) { conflictOF_RW= 1; }
				}
				else if(opco == 25 || opco == 26 || opco == 27 || opco == 28) {
					if( rs1 == 31 ||  rs2 == 31 || IF_Enable_Latch.getrd() == rs1 || IF_Enable_Latch.getrd() == rs2) { conflictOF_RW = 1; }
				}
				else if(isimm == 0) {
					if( rs1 == 31 || rs2 == 31 || IF_Enable_Latch.getrd() == rs1 || IF_Enable_Latch.getrd() == rs2) { conflictOF_RW = 1; }
				}
				else if(isimm ==1 ) {
					if(rs1 == 31 || IF_Enable_Latch.getrd() == rs1) { conflictOF_RW = 1; }
				}
				
			}
			else if(IF_Enable_Latch.getopcode() != 25 && IF_Enable_Latch.getopcode() != 26 && IF_Enable_Latch.getopcode() != 27
					&& IF_Enable_Latch.getopcode() != 28 && 
							IF_Enable_Latch.getopcode() != 23 && IF_Enable_Latch.getopcode() != 24)
			{	
				if(opco == 22) {
					if(IF_Enable_Latch.getrd() == rs1) { conflictOF_RW =1; }
				}
				else if (opco == 23) {
					if(IF_Enable_Latch.getrd() == rd || IF_Enable_Latch.getrd() == rs1) { conflictOF_RW= 1; }
				}
				else if(opco == 25 || opco == 26 || opco == 27 || opco == 28) {
					if(IF_Enable_Latch.getrd() == rs1 || IF_Enable_Latch.getrd() == rs2) { conflictOF_RW = 1; }
				}
				else if(isimm == 0) {
					if(IF_Enable_Latch.getrd() == rs1 || IF_Enable_Latch.getrd() == rs2) { conflictOF_RW = 1; }
				}
				else if(isimm ==1 ) { //System.out.println("conflict isimm " +  rs1 + " "+EX_MA_Latch.getrd() );
					if(IF_Enable_Latch.getrd() == rs1) { conflictOF_RW = 1; }
				}
				
			}
			}
			
			
			if(Clock.getCurrentTime() > 2 && MA_RW_Latch.bubble == false) {
				System.out.println(Clock.getCurrentTime()+" TIMEEE");
			if(opco == 29 || opco == 24) { conflictOF_MA = 0; }
			
			else if(MA_RW_Latch.getopcode() == 25 || MA_RW_Latch.getopcode() == 26 || MA_RW_Latch.getopcode() == 27
					|| MA_RW_Latch.getopcode() == 28)
			{ conflictOF_MA = 0; }
			else if(MA_RW_Latch.getopcode() == 6 || MA_RW_Latch.getopcode() == 7) {
				if(opco == 22) {
					if( rd == 31 || MA_RW_Latch.getrd() == rd) { conflictOF_MA =1; }
				}
				else if (opco == 23) {
					if( rd == 31 ||  rs1 == 31 || MA_RW_Latch.getrd() == rd || MA_RW_Latch.getrd() == rs1  ) { conflictOF_MA= 1; }
				}
				else if(opco == 25 || opco == 26 || opco == 27 || opco == 28) {
					if( rs1 == 31 ||  rs2 == 31 || MA_RW_Latch.getrd() == rs1 || MA_RW_Latch.getrd() == rs2) { conflictOF_MA = 1; }}
				
				else if(isimm == 0) {
					if( rs1 == 31 || rs2 == 31 || MA_RW_Latch.getrd() == rs1 || MA_RW_Latch.getrd() == rs2) { conflictOF_MA = 1; }
				}
				else if(isimm ==1 ) {
					if(rs1 == 31 || MA_RW_Latch.getrd() == rs1) { conflictOF_MA = 1; }
				}
				
				
			}
			else if(MA_RW_Latch.getopcode() != 25 && MA_RW_Latch.getopcode() != 26 && MA_RW_Latch.getopcode() != 27
					&& MA_RW_Latch.getopcode() != 28 && 
					MA_RW_Latch.getopcode() != 23 && MA_RW_Latch.getopcode() != 24)
			{	
				if(opco == 22) {
					if(MA_RW_Latch.getrd() == rd) { conflictOF_MA =1; }
				}
				else if (opco == 23) {
					if(MA_RW_Latch.getrd() == rd || MA_RW_Latch.getrd() == rs1 ) { conflictOF_MA= 1; }
				}
				else if(opco == 25 || opco == 26 || opco == 27 || opco == 28) {
					if(MA_RW_Latch.getrd() == rs1 || MA_RW_Latch.getrd() == rs2) { conflictOF_MA = 1; }}
				else if(isimm == 0) {
					if(MA_RW_Latch.getrd() == rs1 || MA_RW_Latch.getrd() == rs2) { conflictOF_MA = 1; System.out.println("UPDAAAATTTTEEE RW CONFLICT ADD THING");}
				}
				else if(isimm ==1 ) {
					if(MA_RW_Latch.getrd() == rs1) { conflictOF_MA = 1; }
				}
				
				
			}
			}
			
    		}
			hazardOF_EX = conflictOF_EX;
			hazardOF_MA = conflictOF_MA;
			hazardOF_RW = conflictOF_RW;
			if(conflictOF_RW == 1) {
				System.out.println("conflict RW");
				OF_EX_Latch.setOF_stall(OF_EX_Latch.getOF_stall() + 1);
				IF_Enable_Latch.setIF_enable(false);
				OF_EX_Latch.setEX_enable(false);
				IF_OF_Latch.setOF_enable(true);
				//EX_MA_Latch.
			}
			else if(conflictOF_MA == 1) {
				System.out.println("conflict MA");
				OF_EX_Latch.setOF_stall(OF_EX_Latch.getOF_stall() + 1);
				IF_Enable_Latch.setIF_enable(false);
				OF_EX_Latch.setEX_enable(false);
				IF_OF_Latch.setOF_enable(true);
			}
			else if(conflictOF_EX ==1) {
				System.out.println("conflict EX");
				OF_EX_Latch.setOF_stall(OF_EX_Latch.getOF_stall() + 1);
				OF_EX_Latch.setEX_enable(false);
				IF_Enable_Latch.setIF_enable(false);
				IF_OF_Latch.setOF_enable(true);
			}


			
			
			
			
			
			
			
		if(conflictOF_EX != 1 && conflictOF_MA != 1 && conflictOF_RW != 1) {	
			
			System.out.println("no conflict");
			OF_EX_Latch.setSig(opco);
			OF_EX_Latch.setIsimm(isimm);
			OF_EX_Latch.setOp1(op1);
			OF_EX_Latch.setOp2(op2);
			OF_EX_Latch.setImm(imm);
			OF_EX_Latch.setbranchtarget(bt);
			OF_EX_Latch.setrd(rd);
			OF_EX_Latch.setrs1(a);
			OF_EX_Latch.setrs2(b);
			
			System.out.println("opcode in OF " + opco); 
			/*System.out.println("isimmediate in OF " + isimm );
			System.out.println("op1 in OF " + op1 );
			System.out.println("op2 in OF " + op2 );
			System.out.println("imm in OF beq" + imm );
			System.out.println("bt in OF " + bt );
			System.out.println("rd in OF " + rd );
			System.out.println(containingProcessor.getRegisterFile().getValue(31) + " VALUE IN REGISTER 31 operand fetch");
			*/
			
			
			OF_EX_Latch.setEX_enable(true);
			IF_Enable_Latch.setIF_enable(true);
		/*	if(opco != 29) { System.out.println("after end");
			IF_Enable_Latch.setIF_enable(true);}
			else {IF_Enable_Latch.setIF_enable(false);}*/
		}
		if(conflictOF_EX == 1 || conflictOF_MA == 1 || conflictOF_RW == 1) {IF_OF_Latch.setOF_enable(true);
		                                                                    IF_OF_Latch.seton(1);}
		else {IF_OF_Latch.setOF_enable(false);
		IF_OF_Latch.seton(1);}
		if(OF_EX_Latch.isbranch == 0 && conflictOF_EX == 0 && conflictOF_MA == 0 && conflictOF_RW == 0) {
			OF_EX_Latch.setEX_enable(true);}
		else if(EX_MA_Latch.getEX_bubble() == false && conflictOF_EX == 0 && conflictOF_MA == 0 && conflictOF_RW == 0){OF_EX_Latch.setEX_enable(false);
	IF_Enable_Latch.setIF_enable(true);}
	/*	else if(EX_MA_Latch.getEX_bubble() == true && MA_RW_Latch.getMA_bubble() == true && EX_MA_Latch.getisbranch() == 1  && conflictOF_EX == 0 && conflictOF_MA == 0 && conflictOF_RW == 0){OF_EX_Latch.setEX_enable(false);
		IF_Enable_Latch.setIF_enable(true);}*/
		if(EX_MA_Latch.getisbranch() == 1){
			IF_Enable_Latch.setIF_enable(true);
			OF_EX_Latch.setEX_enable(true);
IF_OF_Latch.setOF_enable(false);
			
		}
		

		System.out.println(OF_EX_Latch.getOF_stall() + " NUMBE R OF OF STALLS IN OF");
		if(opco == 29) {IF_Enable_Latch.setcheck(OF_EX_Latch.getOF_stall());}
		System.out.println("of over \n\n\n");
	
	//	if(containingProcessor.getEXUnit().isOF() == 1) { IF_OF_Latch.setOF_enable(false); }
		
		}
	}


}