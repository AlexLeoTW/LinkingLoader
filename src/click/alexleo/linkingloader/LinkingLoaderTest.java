package click.alexleo.linkingloader;

import static org.junit.Assert.*;

import org.junit.Test;
import click.alexleo.linkingloader.LinkingLoader;

public class LinkingLoaderTest {
    
    @Test
    public void testHelfByteRead() {
        byte[] memory = {(byte) 0b00000000, (byte) 0b00000000};
        assertEquals(0b0000, LinkingLoader.helfByteRead(memory, 0));
        assertEquals(0b0000, LinkingLoader.helfByteRead(memory, 0.5));
        assertEquals(0b0000, LinkingLoader.helfByteRead(memory, 1));
        assertEquals(0b0000, LinkingLoader.helfByteRead(memory, 1.5));
        
        memory = new byte[]{(byte) 0b00110000, (byte) 0b00000000};
        assertEquals(0b0011, LinkingLoader.helfByteRead(memory, 0));
        //assertEquals(0b0000, LinkingLoader.helfByteRead(memory, 0.5));
        //assertEquals(0b0000, LinkingLoader.helfByteRead(memory, 1));
        //assertEquals(0b0000, LinkingLoader.helfByteRead(memory, 1.5));
    }
    
    /*@Test
    public void testByteWrite() {
	    fail("Not yet implemented");
    }*/
}
