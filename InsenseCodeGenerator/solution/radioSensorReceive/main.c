// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.CompilationUnit::printHeaders
#include "main.h"
#ifndef PRINTER_H_
 #include "Insense_Printer.h"
#endif 
#ifndef RADIOSENSORRECEIVE2_H_
 #include "Insense_RadioSensorReceive2.h"
#endif 


#ifndef DALSMALL
static char *file_name = "main";
#endif

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.CompilationUnit::printGlobals

// main_stack_size definition so that InceOS knows size of main component
int main_stack_size = 94;

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.CompilationUnit::printGlobalFunctions

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.CompilationUnit::printMain
void primordial_main( void *this ) {
	inceos_event_t op_status;// for exception handling
	DAL_assign( &serialiserMap,Construct_StringMap(  )  ) ;
	initializeSerializerFunctions(  ) ;
	initDALGlobalObjects(  ) ;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Sequence::complete
	PrinterPNTR pr_glob = NULL;
	DAL_assign(&pr_glob , component_create( Construct_Printer0, sizeof( PrinterStruct ) , 22, 0, NULL ) );
	component_yield(  ) ;
;
	RadioSensorReceive2PNTR radioSensorReceive2_glob = NULL;
	DAL_assign(&radioSensorReceive2_glob , component_create( Construct_RadioSensorReceive20, sizeof( RadioSensorReceive2Struct ) , 104, 0, NULL ) );
	component_yield(  ) ;
;


// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Connect::complete
	channel_bind( radioSensorReceive2_glob->received_comp,radio_glob->received_comp ) ;
;


// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Connect::complete
	channel_bind( radioSensorReceive2_glob->output_comp,pr_glob->input_comp ) ;
;
	// End of sequence

	component_exit(  ) ;// as the primordial is a component itself created by the boot code, must deleted to return the memory and space it uses
}
