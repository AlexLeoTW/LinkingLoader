package click.alexleo.linkingloader.utils.record;

public class HeadRecord implements Record {
    public final String name;
    public final int startingAddress;
    public final int length;
    
    public HeadRecord(String name, int startingAddress, int firstExecutable) {
	this.name = name;
	this.startingAddress = startingAddress;
	this.length = firstExecutable;
    }
    
    public HeadRecord(String desc) {
	this.name = desc.substring(1, 7);
	this.startingAddress = Integer.valueOf(desc.substring(7, 13));
	this.length = Integer.valueOf(desc.substring(13, 19));
    }
}
