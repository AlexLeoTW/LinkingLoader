package click.alexleo.linkingloader.utils;

public class ObjectRecord {
    public final ObjectRecordType type;
    public final Object content;
    
    public ObjectRecord(ObjectRecordType type, Object content) {
	this.type = type;
	this.content = content;
    }
}
