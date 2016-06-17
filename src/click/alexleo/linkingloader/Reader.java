package click.alexleo.linkingloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import click.alexleo.linkingloader.utils.record.EndRecord;
import click.alexleo.linkingloader.utils.record.HeadRecord;
import click.alexleo.linkingloader.utils.record.ModifyRecord;
import click.alexleo.linkingloader.utils.record.Record;
import click.alexleo.linkingloader.utils.record.TextRecord;

public class Reader {
    static final public String defaultPath = "./input.obj";
    BufferedReader fileStream = null;
    
    public Reader() {
	openStream(defaultPath);
    }
    
    public Reader(String filePath) {
	openStream(filePath);
    }
    
    public Record getLine() throws IOException {
	String line = fileStream.readLine();
	
	if (line.length() > 0) {
	    char recordType = line.charAt(0);
	    switch(recordType) {
	    case 'H':
		return new HeadRecord(line);
	    case 'T':
		return new TextRecord(line);
	    case 'E':
		return new EndRecord(line);
	    case 'M':
		return new ModifyRecord(line);
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
