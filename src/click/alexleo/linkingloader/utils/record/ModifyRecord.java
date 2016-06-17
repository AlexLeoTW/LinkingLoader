package click.alexleo.linkingloader.utils.record;

public class ModifyRecord implements Record {
    public final int startingAddress;
    public final int length;
    
    public ModifyRecord(int startingAddress, int length) {
	super();
	this.startingAddress = startingAddress;
	this.length = length;
    }
    
    public ModifyRecord(String desc) {
	this.startingAddress = Integer.valueOf(desc.substring(1, 7));
	this.length = Integer.valueOf(desc.substring(7, 9));
    }
}
