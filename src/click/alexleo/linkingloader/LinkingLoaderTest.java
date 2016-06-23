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
        
        memory = new byte[]{(byte) 0b00110000, (byte) 0b11111111};
        assertEquals(0b0011, LinkingLoader.helfByteRead(memory, 0));
        assertEquals(0b0000, LinkingLoader.helfByteRead(memory, 0.5));
        assertEquals(0b1111, LinkingLoader.helfByteRead(memory, 1));
        assertEquals(0b1111, LinkingLoader.helfByteRead(memory, 1.5));
    }
    
    @Test
    public void testHelfByteWrite() {
	byte[] memory = {(byte) 0b00000000, (byte) 0b00000000};
	LinkingLoader.helfByteWrite(memory, 0, (byte)0b0001);
	assertEquals(0x10, memory[0]);
	LinkingLoader.helfByteWrite(memory, 0.5, (byte)0b0001);
	assertEquals(0x11, memory[0]);
	LinkingLoader.helfByteWrite(memory, 1, (byte)0b0001);
	assertEquals(0x10, memory[1]);
	LinkingLoader.helfByteWrite(memory, 1.5, (byte)0b0001);
	assertEquals(0x11, memory[1]);
	
	LinkingLoader.helfByteWrite(memory, 0, (byte)0b0000);
	assertEquals(0x01, memory[0]);
	LinkingLoader.helfByteWrite(memory, 0.5, (byte)0b0000);
	assertEquals(0x00, memory[0]);
	LinkingLoader.helfByteWrite(memory, 1, (byte)0b0000);
	assertEquals(0x01, memory[1]);
	LinkingLoader.helfByteWrite(memory, 1.5, (byte)0b0000);
	assertEquals(0x00, memory[1]);
    }
    
    @Test
    public void testMemcpy() {
	byte[] memory = new byte[5];
	byte[] from = {(byte)0xFF, (byte)0xEE, 0x01};
	byte[] expected = {
		0x00,
		(byte)0xFF,
		(byte)0xEE,
		0x01,
		0x00
	};
	
	LinkingLoader.memcpy(memory, from, 1, from.length);
	
    }
    
    @Test
    public void testHex() {
	assertEquals(0xF, 0xFF%16);
	assertEquals(0x6, 0x1036%16);
    }
}
