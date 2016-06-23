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
	this.startingAddress = Integer.valueOf(desc.substring(1, 7), 16);
	this.length = Integer.valueOf(desc.substring(7, 9), 16);
    }

    @Override
    public boolean equals(Object compare) {
	if((compare == null) || (compare.getClass() != this.getClass())) {
	    return false;
	}
	
	if (this == compare) {
	    return true;
	}
	
	if (this.startingAddress != ((ModifyRecord)compare).startingAddress) {
	    System.out.println("Different startingAddress");
	    return false;
	}
	
	if (this.length != ((ModifyRecord)compare).length) {
	    System.out.println("Different length");
	    return false;
	}
	
	return true;
    }
}
