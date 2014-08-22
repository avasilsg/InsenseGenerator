// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::print_ifdef
#ifndef PRINTER_H_
#define PRINTER_H_

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printImplIncludes
#include "main.h"
// TODO put remainder of impl includes here


// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printStructDecl
typedef struct Printer *PrinterPNTR , PrinterStruct ;
struct Printer { 
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printCommonDecls
	void (*decRef)(PrinterPNTR pntr);
	bool stopped;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printStructChannelDecls
	chan_id input_comp;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.DeclarationContainer::printLocationDecls
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.DeclarationContainer::locationDecls

} ;

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorArrayFunctionDecls

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printComponentFuncsDecls

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorSignatures
extern void Construct_Printer0( PrinterPNTR this, int _argc, void* _argv[] ) ;

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printBehaviourForwardDecl
extern void behaviour_Printer( PrinterPNTR this ) ;
#endif /* PRINTER_H_*/ 

