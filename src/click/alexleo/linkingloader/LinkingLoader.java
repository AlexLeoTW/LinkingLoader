package click.alexleo.linkingloader;

import java.util.Random;

import click.alexleo.linkingloader.utils.ObjectRecord;
import click.alexleo.linkingloader.utils.ObjectRecordType;
import click.alexleo.linkingloader.utils.record.EndRecord;
import click.alexleo.linkingloader.utils.record.HeadRecord;
import click.alexleo.linkingloader.utils.record.ModifyRecord;
import click.alexleo.linkingloader.utils.record.Record;
import click.alexleo.linkingloader.utils.record.TextRecord;

public class LinkingLoader {
    
    public static void main(String[] arg) throws Exception {
	Reader reader = null;
	byte[] memory = new byte[1024*1024];
	int programStart = (new Random()).nextInt(4096);
	ObjectRecord currentRecord = null;
	
	// ---------------------------------------------------------------------------------------
	// System.out.printf("(byte)0b0000 = [%d, %X]\n", (byte)0b0000, (byte)0b0000);
	// System.out.printf("(byte)0b0001 = [%d, %X]\n", (byte)0b0001, (byte)0b0001);
	// System.out.printf("(byte)0b0011 = [%d, %X]\n", (byte)0b0011, (byte)0b0011);
	// System.out.printf("(byte)0b0111 = [%d, %X]\n", (byte)0b0111, (byte)0b0111);
	// System.out.printf("(byte)0b1111 = [%d, %X]\n", (byte)0b1111, (byte)0b1111);
	// System.out.printf("(byte)0b0001 1111 = [%d, %X]\n", (byte)0b00011111, (byte)0b00011111);
	// System.out.printf("(byte)0b0011 1111 = [%d, %X]\n", (byte)0b00111111, (byte)0b00111111);
	// System.out.printf("(byte)0b0111 1111 = [%d, %X]\n", (byte)0b01111111, (byte)0b01111111);
	// System.out.printf("(byte)0b1111 1111 = [%d, %X]\n", (byte)0b11111111, (byte)0b11111111);
	// System.out.printf("(int)(byte)0b1111 1111 = [%d, %X]\n", (int)(byte)0b11111111, (int)(byte)0b11111111);
	// System.out.printf("(int)(byte)0b0111 1111 = [%d, %X]\n", (int)(byte)0b1111111, (int)(byte)0b01111111);
	// System.out.printf("%d\n", Integer.parseInt("1D", 16));
	// ---------------------------------------------------------------------------------------
	
	if (arg.length > 1) {
	    reader = new Reader(arg[1]);
	} else {
	    reader = new Reader();
	}
	
	
	while ((currentRecord = reader.getLine()) != null) {
	    switch(currentRecord.type) {
	    case HEADER:
		HeadRecord headRecord = (HeadRecord)currentRecord.content;
		
		// Rreserved for set all space to 0
		// programStart += headRecord.startingAddress;
		
		if ((programStart + headRecord.length) > memory.length) {
		    throw new Exception("Out of memory");
		}
		
		System.out.printf("Loading %s from 0x%06X ...\n", headRecord.name, programStart);
		break;
		
	    case TEXT:
		TextRecord textRecord = (TextRecord)currentRecord.content;
		
		if ((programStart + textRecord.startingAddress + textRecord.length) > memory.length) {
		    throw new Exception("Out of memory");
		}
		
		System.out.printf("[TextRecord] Loading data @0x%06X + 0x%06X\n", textRecord.startingAddress, programStart);
		
		if (!memcpy(memory, textRecord.data, programStart + textRecord.startingAddress, textRecord.length)) {
		    throw new Exception("Out of memory");
		}
		break;
		
	    case END:
		EndRecord endRecord = (EndRecord)currentRecord.content;
		
		System.out.printf("[EndRecord] complete loading, please start execute from 0x%06X\n", programStart + endRecord.firstExecuable);
		break;
		
	    case MODIFY:
		ModifyRecord modifyRecord = (ModifyRecord)currentRecord.content;
		long data = 0;
		double modFrom = programStart;
		
		if (modifyRecord.length%2 == 1) {
		    modFrom += modifyRecord.startingAddress + 0.5;
		} else {
		    modFrom += modifyRecord.startingAddress;
		}
		
		for (int i=0; i<modifyRecord.length; i++) {
		    data = data << 4;
		    data += helfByteRead(memory, modFrom+(double)i/2);
		    System.out.printf("%X", helfByteRead(memory, modFrom+(double)i/2));
		}
		
		data += programStart;
		System.out.printf(" -> %X -> ", data);
		
		for (int i=modifyRecord.length-1; i>0; i--) {
		    helfByteWrite(memory, modFrom+(double)i/2, (byte)(data%16));
		    //System.out.printf("%X\n", (byte)(data%16));
		    data = data >> 4;
		}
		
		System.out.printf("[ModifyRecord] Modify data @%06X\n", programStart + modifyRecord.startingAddress);
		
		break;
		
	    default:
		System.out.println("ERROR, unknown record type");
	    }
	}
	
	System.out.println("======================= Memory Dump =======================");
	
	for (int i=programStart; i<programStart + 4215; i++) {
	    if ((i - programStart) % 10 == 0) {
		System.out.printf("[%06X] ", i);
	    }
	    
	    System.out.printf("%02X ", memory[i]);
	    
	    if ((i - programStart) % 10 == 9) {
		System.out.printf(" [%06X]\n", i);
	    }
	}
    }
    
    public static boolean memcpy(byte[] to, byte[] from, int startingAddress, int length) {
	//System.out.printf("memcpy(to, from, %d, %d)\n", startingAddress, length);
	if (startingAddress + length > to.length) {
	    return false;
	} else {
	    for (int i=0; i<length; i++) {
		to[startingAddress + i] = from[i];
		//System.out.printf("to[%X + %d] = from[%d]  (%02X)\n", startingAddress, i, i, from[i]);
	    }
	    return true;
	}
    }
    
    public static byte helfByteRead(byte[] memory, double byteAddress) {
	int rounded = (new Double(byteAddress)).intValue();
	int real = memory[rounded];
	
	if (byteAddress != rounded) {
	    real = real & 0x0000000F;
	    return (byte)real;
	} else {
	    real = real & 0x000000F0;
	    real = real >> 4;
	    return (byte)real;
	}
    }
    
    public static void helfByteWrite(byte[] memory, double byteAddress, byte data) {
	int rounded = (new Double(byteAddress)).intValue();
	int real = data;
	
	if (byteAddress != rounded) {
	    memory[rounded] = (byte) ((memory[rounded] & 0xF0) + (real & 0x0000000F));
	} else {
	    real = real << 4;
	    memory[rounded] = (byte) ((memory[rounded] & 0x0F) + (real & 0x000000F0));
	}
    }
}
