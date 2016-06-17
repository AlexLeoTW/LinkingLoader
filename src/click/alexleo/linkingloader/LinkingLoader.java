package click.alexleo.linkingloader;

import java.io.IOException;
import java.util.Random;

import click.alexleo.linkingloader.utils.record.EndRecord;
import click.alexleo.linkingloader.utils.record.HeadRecord;
import click.alexleo.linkingloader.utils.record.ModifyRecord;
import click.alexleo.linkingloader.utils.record.Record;
import click.alexleo.linkingloader.utils.record.TextRecord;

public class LinkingLoader {
    
    public static void main(String[] arg) throws Exception {
	Reader reader = null;
	byte[] memory = new byte[1024*1024];
	int startingAddress = (new Random()).nextInt(4096);
	Record currentRecord = null;
	
	if (arg.length > 1) {
	    reader = new Reader(arg[1]);
	} else {
	    reader = new Reader();
	}
	
	while ((currentRecord = reader.getLine()) != null) {
	    if (currentRecord instanceof HeadRecord) {
		HeadRecord headRecord = (HeadRecord)currentRecord;
		
		startingAddress += headRecord.startingAddress;
		
		if ((startingAddress + headRecord.length) > memory.length) {
		    throw new Exception("Out of memory");
		}
		
		System.out.println("Loading " + headRecord.name + "...");
		
	    } else if (currentRecord instanceof TextRecord) {
		TextRecord textRecord = (TextRecord)currentRecord;
		
		if ((textRecord.startingAddress + textRecord.length) > memory.length) {
		    throw new Exception("Out of memory");
		}
		
		System.out.println("[TextRecord] Loading data @" + textRecord.startingAddress);
		
		if (!memcpy(memory, textRecord.data, textRecord.startingAddress, textRecord.length)) {
		    throw new Exception("Out of memory");
		}
		
	    } else if (currentRecord instanceof EndRecord) {
		EndRecord endRecord = (EndRecord)currentRecord;
		
		System.out.println("[EndRecord] complete loading, please start execute from " + endRecord.firstExecuable);
		
	    } else if (currentRecord instanceof ModifyRecord) {
		ModifyRecord modifyRecord = (ModifyRecord)currentRecord;
		long data = 0;
		double modFrom = 0;
		
		if (modifyRecord.length%2 == 1) {
		    modFrom = modifyRecord.startingAddress + 0.5;
		} else {
		    modFrom = modifyRecord.startingAddress;
		}
		
		for (int i=0; i<modifyRecord.length; i++) {
		    data += helfByteRead(memory, modFrom+i) * Math.pow(128, modifyRecord.length-i-1);
		}
		
		for (int i=modifyRecord.length; i>0; i--) {
		    helfByteWrite(memory, modFrom+i, (byte)(data%128));
		    data /= 128;
		}
		
	    } else {
		System.out.println("ERROR, unknown record type");
	    }
	}
    }
    
    public static boolean memcpy(byte[] to, byte[] from, int startingAddress, int length) {
	if (startingAddress + length > to.length) {
	    return false;
	} else {
	    for (int i=0; i<length; i++) {
		to[startingAddress + i] = from[i];
	    }
	    return true;
	}
    }
    
    public static byte helfByteRead(byte[] memory, double byteAddress) {
	int rounded = (new Double(byteAddress)).intValue();
	
	if (byteAddress != rounded) {
	    return (byte)(memory[rounded] % 128);
	} else {
	    return (byte)(memory[rounded] / 128);
	}
    }
    
    public static void helfByteWrite(byte[] memory, double byteAddress, byte data) {
	int rounded = (new Double(byteAddress)).intValue();
	
	if (byteAddress != rounded) {
	    memory[rounded] = (byte)(memory[rounded]/128*128 + data);
	} else {
	    memory[rounded] = (byte)(memory[rounded]%128 + data*128); 
	}
    }
}
