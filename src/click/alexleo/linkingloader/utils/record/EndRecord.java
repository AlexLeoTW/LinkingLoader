package click.alexleo.linkingloader.utils.record;

public class EndRecord implements Record {
    public final int firstExecuable;

    public EndRecord(int firstExecuable) {
	this.firstExecuable = firstExecuable;
    }
    
    public EndRecord(String desc) {
	this.firstExecuable = Integer.valueOf(desc.substring(1, 7));
    }

    @Override
    public boolean equals(Object compare) {
	if((compare == null) || (compare.getClass() != this.getClass())) {
	    return false;
	}
	
	if (this == compare) {
	    return true;
	}
	
	if (this.firstExecuable != ((EndRecord)compare).firstExecuable) {
	    System.out.println("Different startingAddress");
	    return false;
	}
	
	return true;
    }
}
