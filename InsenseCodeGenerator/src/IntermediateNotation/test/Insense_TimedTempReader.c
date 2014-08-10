// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::printImplIncludes
#include "main.h"
#include "semaphore.h"
#include "my_semaphore.h"
#include "pthread.h"
// TODO put remainder of impl includes here




// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::generateDecRef
static void decRef_TimedTempReader( TimedTempReaderPNTR this ) { 
	channel_unbind( this->output_comp ) ;
	// TODO need InceOS Channel_decRef( this->output_comp ) ;

} 

// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::printComponentFuncsImpl

// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::printBehaviourImpl
void behaviour_TimedTempReader( TimedTempReaderPNTR this ) { 
	while( ! this->stopped ) { 

	// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Sequence::complete
	int temp;
	temp = tempReading_proc(this, NULL ) ;
	// Make call to send op
{ 
	int _temp_var = temp;
	channel_send( this->output_comp,&_temp_var,NULL  ) ;
	// end of send op 
} 
;
	// End of sequence

	} 
	component_exit(  ) ;

} 

// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::printConstructorImpls
// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Component::printConstructorGlobalInitialiser
void TimedTempReader_init_globals( TimedTempReaderPNTR this ) 
{ 
	this->decRef = decRef_TimedTempReader;
	this->output_comp=channel_create( CHAN_OUT,sizeof( int  ) ,false ) ;
// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.DeclarationContainer::locationInitialisers

} 

// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Constructor::generateCode
// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Constructor::constructorFunctionDecl
void Construct_TimedTempReader0( TimedTempReaderPNTR this, int _argc, void* _argv[] ) { 
	TimedTempReader_init_globals( this ) ;
	my_sem_post( &this->component_create_sem ) ;
// Generated from: uk.ac.stand.cs.insense.compiler.unixCCgen.Sequence::complete
	// End of sequence

	behaviour_TimedTempReader( this ) ;
} 


