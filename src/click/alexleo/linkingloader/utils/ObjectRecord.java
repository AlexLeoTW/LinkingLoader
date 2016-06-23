package click.alexleo.linkingloader.utils;

import click.alexleo.linkingloader.utils.record.Record;

public class ObjectRecord {
    public final ObjectRecordType type;
    public final Record content;
    
    public ObjectRecord(ObjectRecordType type, Record content) {
	this.type = type;
	this.content = content;
    }
}
