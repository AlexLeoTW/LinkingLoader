package click.alexleo.linkingloader.utils.record;

import java.util.Arrays;

public class TextRecord implements Record {
    public final int startingAddress;
    public final int length;
    public final byte[] data;
    
    public TextRecord(int startingAddress, int length, byte[] data) {
	this.startingAddress = startingAddress;
	this.length = length;
	this.data = data;
    }
    
    public TextRecord(String desc) throws Exception {
	this.startingAddress = Integer.parseInt(desc.substring(1, 7), 16);
	//System.out.printf("this.startingAddress = [%d, %X]\n", this.startingAddress, this.startingAddress);
	this.length = Integer.parseInt(desc.substring(7, 9), 16);
	//System.out.printf("this.length = [%d, %X]\n", this.length, this.length);
	if ((desc.length() - 9)/2 != length) {
	    throw new Exception(String.format("Langth field and Data field doesn't match ( %X : %X )", length, (desc.length() - 9)/2));
	}
	
	byte[] data = new byte[length];
	
	for (int i=0; i<data.length; i++) {
	    //System.out.println(desc.substring(9+i*2, 11+i*2));
	    data[i] = (byte)Integer.parseInt(desc.substring(9+i*2, 11+i*2), 16);
	    //System.out.printf("data[%d] = [%d, %02X]\n", i, data[i], data[i]);
	}
	
	this.data = data;
    }
    
    @Override
    public boolean equals(Object compare) {
	if((compare == null) || (compare.getClass() != this.getClass())) {
	    return false;
	}
	
	if (this == compare) {
	    return true;
	}
	
	if (this.startingAddress != ((TextRecord)compare).startingAddress) {
	    System.out.println("Different startingAddress");
	    return false;
	}
	
	if (this.length != ((TextRecord)compare).length) {
	    System.out.println("Different length");
	    return false;
	}
	
	if (!Arrays.equals(this.data, ((TextRecord)compare).data)) {
	    System.out.println("Different data");
	    return false;
	}
	
	return true;
    }
    
    public String toString() {
	String dataString = "";
	
	for (int i=0; i<data.length; i++) {
	    dataString += data[i];
	}
	
	return  String.format("startingAddress: %d\n", startingAddress)
		+ String.format("length: %d\n", length)
		+ String.format("data: %s\n", dataString);
    }
}
