package click.alexleo.linkingloader.utils.record;

public class EndRecord implements Record {
    public final int firstExecuable;

    public EndRecord(int firstExecuable) {
	this.firstExecuable = firstExecuable;
    }
    
    public EndRecord(String desc) {
	this.firstExecuable = Integer.valueOf(desc.substring(1, 7));
    }
}
