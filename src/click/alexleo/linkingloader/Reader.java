package click.alexleo.linkingloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import click.alexleo.linkingloader.utils.ObjectRecord;
import click.alexleo.linkingloader.utils.ObjectRecordType;
import click.alexleo.linkingloader.utils.record.*;

public class Reader {
    static final public String defaultPath = "./input.obj";
    BufferedReader fileStream = null;
    
    public Reader() {
	openStream(defaultPath);
    }
    
    public Reader(String filePath) {
	openStream(filePath);
    }
    
    public ObjectRecord getLine() throws Exception {
	String line = fileStream.readLine();
	
	if (line != null && line.length() > 0) {
	    char recordType = line.charAt(0);
	    switch(recordType) {
	    case 'H':
		return new ObjectRecord(ObjectRecordType.HEADER, new HeadRecord(line));
	    case 'T':
		return new ObjectRecord(ObjectRecordType.TEXT, new TextRecord(line));
	    case 'E':
		return new ObjectRecord(ObjectRecordType.END, new EndRecord(line));
	    case 'M':
		return new ObjectRecord(ObjectRecordType.MODIFY, new ModifyRecord(line));
	    default:
		return null;
	    }
	} else {
	    return null;
	}
    }
    
    private void openStream(String path) {
	try {
	    fileStream = new BufferedReader(new FileReader(path));
	}
	catch (IOException e) {
		System.out.println("ERROR when opening object file.");
		e.printStackTrace();
	}
    }
}
