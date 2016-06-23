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
	this.name = desc.substring(1, 7).trim();
	this.startingAddress = Integer.valueOf(desc.substring(7, 13));
	this.length = Integer.valueOf(desc.substring(13, 19));
    }

    @Override
    public boolean equals(Object compare) {
	if((compare == null) || (compare.getClass() != this.getClass())) {
	    return false;
	}
	
	if (this == compare) {
	    return true;
	}
	
	if (this.startingAddress != ((HeadRecord)compare).startingAddress) {
	    System.out.println("Different startingAddress");
	    return false;
	}
	
	if (this.length != ((HeadRecord)compare).length) {
	    System.out.println("Different length");
	    return false;
	}
	
	return true;
    }
}
