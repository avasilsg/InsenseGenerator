// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printImplIncludes
#include "main.h"
// TODO put remainder of impl includes here


#ifndef DALSMALL
static char *file_name = "Printer";
#endif


// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::generateDecRef
static void decRef_Printer( PrinterPNTR this ) { 
	channel_unbind( this->input_comp ) ;
	// TODO need InceOS Channel_decRef( this->input_comp ) ;

} 

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printComponentFuncsImpl

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printBehaviourImpl
void behaviour_Printer( PrinterPNTR this ) { 
	inceos_event_t op_status;// for exception handling

	while( ! this->stopped ) { 

	// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Sequence::complete
	int temp;

	// Make call to receive function 
	channel_receive( this->input_comp,&temp,false ) ;
;
	printInt_proc(this, NULL, temp ) ;
	printString_proc(this, NULL, Construct_String0(" is the current temp\n") ) ;
	// End of sequence

	} 
	component_exit(  ) ;

} 

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorImpls
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorGlobalInitialiser
void Printer_init_globals( PrinterPNTR this ) 
{ 
	this->decRef = decRef_Printer;
	this->input_comp=channel_create( CHAN_IN,sizeof( int  ) ,false ) ;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.DeclarationContainer::locationInitialisers

} 

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Constructor::generateCode
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Constructor::constructorFunctionDecl
void Construct_Printer0( PrinterPNTR this, int _argc, void* _argv[] ) { 
	Printer_init_globals( this ) ;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Sequence::complete
	// End of sequence

	behaviour_Printer( this ) ;
} 


