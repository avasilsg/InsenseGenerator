// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.CompilationUnit::printHeaders
#include "main.h"
#ifndef TIMEDTEMPREADER_H_
 #include "Insense_TimedTempReader.h"
#endif 
#ifndef PRINTER_H_
 #include "Insense_Printer.h"
#endif 



// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.CompilationUnit::printGlobals

// main_stack_size definition so that InceOS knows size of main component
int main_stack_size = 94;

// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.CompilationUnit::printGlobalFunctions

// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.CompilationUnit::printMain
void primordial_main( void *this ) {
// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Sequence::complete
	TimedTempReaderPNTR tr_glob = NULL;
	DAL_assign(&tr_glob , component_create( ((behaviour_ft)&Construct_TimedTempReader0), sizeof( TimedTempReaderStruct ) , 6, 0, NULL , -1) );
	component_yield(  ) ;
;
	PrinterPNTR pr_glob = NULL;
	DAL_assign(&pr_glob , component_create( ((behaviour_ft)&Construct_Printer0), sizeof( PrinterStruct ) , 6, 0, NULL , -1) );
	component_yield(  ) ;
;


// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Connect::complete
	channel_bind( tr_glob->output_comp,pr_glob->input_comp ) ;
;
	// End of sequence

	component_exit(  ) ;// as the primordial is a component itself created by the boot code, must deleted to return the memory and space it uses
}
