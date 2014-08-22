// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printImplIncludes
#include "main.h"
// TODO put remainder of impl includes here


#ifndef DALSMALL
static char *file_name = "TimedTempReader";
#endif


// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::generateDecRef
static void decRef_TimedTempReader( TimedTempReaderPNTR this ) { 
	channel_unbind( this->output_comp ) ;
	// TODO need InceOS Channel_decRef( this->output_comp ) ;

} 

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printComponentFuncsImpl

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printBehaviourImpl
void behaviour_TimedTempReader( TimedTempReaderPNTR this ) { 
	inceos_event_t op_status;// for exception handling

	while( ! this->stopped ) { 

	// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Sequence::complete
	int temp;
	temp = tempReading_proc(this, NULL ) ;
	// Make call to send op 
{ 
	int  _lvalue= temp;
	channel_send( this->output_comp,&_lvalue,NULL  ) ;
	// end of send op 
} 
;
	// End of sequence

	} 
	component_exit(  ) ;

} 

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorImpls
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorGlobalInitialiser
void TimedTempReader_init_globals( TimedTempReaderPNTR this ) 
{ 
	this->decRef = decRef_TimedTempReader;
	this->output_comp=channel_create( CHAN_OUT,sizeof( int  ) ,false ) ;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.DeclarationContainer::locationInitialisers

} 

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Constructor::generateCode
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Constructor::constructorFunctionDecl
void Construct_TimedTempReader0( TimedTempReaderPNTR this, int _argc, void* _argv[] ) { 
	TimedTempReader_init_globals( this ) ;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Sequence::complete
	// End of sequence

	behaviour_TimedTempReader( this ) ;
} 


