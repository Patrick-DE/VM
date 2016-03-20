package vm;

public class VM {

	int anzahlregs = 4;
	int[] regs = new int[anzahlregs];

	int[] program = { 0x1064, 0x11C8, 0x2201, 0x0000 };

	/* program counter */
	int pc = 0;

	/* fetch the next word from the program */
	public int fetch(){
	  return program[ pc++ ];
	}

	/* instruction fields */
	int instrNum = 0;
	int reg1     = 0;
	int reg2     = 0;
	int reg3     = 0;
	int imm      = 0;

	/* decode a word */
	public void decode( int instr )
	{
	  instrNum = (instr & 0xF000) >> 12;
	  reg1     = (instr & 0xF00 ) >>  8;
	  reg2     = (instr & 0xF0  ) >>  4;
	  reg3     = (instr & 0xF   );
	  imm      = (instr & 0xFF  );
	}

	/* the VM runs until this flag becomes 0 */
	boolean running = true;

	/* evaluate the last decoded instruction */
	void eval()
	{
	  switch( instrNum )
	  {
	    case 0:
	      /* halt */
	      System.out.println("halt\n");
	      running = false;
	      break;
	    case 1:
	      /* loadi */
	      System.out.println("loadi r" + reg1 + " #" + imm);
	      regs[ reg1 ] = imm;
	      break;
	    case 2:
	      /* add */
	    	System.out.println("add r" + reg1 + " r" + reg2 + " r" + reg3);
	      regs[ reg1 ] = regs[ reg2 ] + regs[ reg3 ];
	      break;
	  }
	}

	/* display all registers as 4-digit hexadecimal words */
	void showRegs()
	{
	  int i;
	  System.out.print( "regs = " );
	  for( i=0; i<anzahlregs; i++ )
	    System.out.print( "%04X " + regs[ i ] );
	  System.out.print( "\n" );
	}

	void run()
	{
	  while( running )
	  {
	    showRegs();
	    int instr = fetch();
	    decode( instr );
	    eval();
	  }
	  showRegs();
	}

	public static void main(String[] args) {
		VM vm1 = new VM();
		vm1.run();	   
	}
}
