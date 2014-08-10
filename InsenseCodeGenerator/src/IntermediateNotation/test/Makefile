ifndef INSENSE_RUNTIME_UNIX
     $(error INSENSE_RUNTIME_UNIX not defined! You must specify where INSENSE_RUNTIME_UNIX resides!)
endif

TARGETFLAGS = $(CFLAGS) 

PROJ=/home/stephan/git/InsenseGenerator/InsenseCodeGenerator/src/IntermediateNotation/test1.isf

APP_INCLUDES = -I $(INSENSE_RUNTIME_UNIX)
APP_SRCFILES = Insense_TimedTempReader.c Insense_Printer.c main.c marshaller.c 
APP_OBJECTFILES = $(APP_SRCFILES:.c=.o)

all: iclean $(APP_OBJECTFILES) insense_runtime_unix.a $(PROJECT_LIBRARIES)
	$(CC) $(LDFLAGS) $(APP_OBJECTFILES) insense_runtime_unix.a -o $(PROJ) -pthread

include $(INSENSE_RUNTIME_UNIX)/Makefile.include
iclean:
		rm -f $(APP_OBJECTFILES) $(INSENSE_OBJECTFILES) insense_runtime_unix.a *~ $(INSENSE_RUNTIME_UNIX)/*~
sclean: iclean
		rm -f *.[ch] Makefile *.stackdump *.a $(PROJ)
