package click.alexleo.linkingloader.utils.record;

public class TextRecord implements Record {
    public final int startingAddress;
    public final int length;
    public final byte[] data;
    
    public TextRecord(int startingAddress, int length, byte[] data) {
	this.startingAddress = startingAddress;
	this.length = length;
	this.data = data;
    }
    
    public TextRecord(String desc) {
	this.startingAddress = Integer.valueOf(desc.substring(1, 7));
	this.length = Integer.valueOf(desc.substring(7, 9));
	byte[] data = new byte[length/2];
	
	for (int i=0; i<data.length; i++) {
	    data[i] = Byte.valueOf(desc.substring(9+i*2, 11+i*2));
	}
	
	this.data = data;
    }
}
