// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::printImplIncludes
#include "main.h"
#include "semaphore.h"
#include "my_semaphore.h"
#include "pthread.h"
// TODO put remainder of impl includes here




// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::generateDecRef
static void decRef_Printer( PrinterPNTR this ) { 
	channel_unbind( this->input_comp ) ;
	// TODO need InceOS Channel_decRef( this->input_comp ) ;

} 

// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::printComponentFuncsImpl

// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::printBehaviourImpl
void behaviour_Printer( PrinterPNTR this ) { 
	while( ! this->stopped ) { 

	// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Sequence::complete
	// End of sequence

	} 
	component_exit(  ) ;

} 

// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::printConstructorImpls
// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::printConstructorGlobalInitialiser
void Printer_init_globals( PrinterPNTR this ) 
{ 
	this->decRef = decRef_Printer;
	this->input_comp=channel_create( CHAN_IN,sizeof( int  ) ,false ) ;
// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.DeclarationContainer::locationInitialisers

} 

// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Constructor::generateCode
// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Constructor::constructorFunctionDecl
void Construct_Printer0( PrinterPNTR this, int _argc, void* _argv[] ) { 
	Printer_init_globals( this ) ;
	my_sem_post( &this->component_create_sem ) ;
// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Sequence::complete
	// End of sequence

	behaviour_Printer( this ) ;
} 


