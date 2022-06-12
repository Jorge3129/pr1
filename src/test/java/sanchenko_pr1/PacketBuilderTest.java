package sanchenko_pr1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class PacketBuilderTest {

    private IPacketBuilder packetBuilder;
    private Message message;
    private Packet packet;

    @Before
    public void init() {
        packetBuilder = new PacketBuilder();
        message = new Message(CommandType.CREATE, 1, new byte[]{1, 2, 3});
        packet = new Packet((byte) 1, 1, message);
    }

    @Test
    public void shouldEncodeAndDecodePacket() {
        byte[] encodedPacket = packetBuilder.encode(packet);
        Packet decodedPacket = packetBuilder.decode(encodedPacket);
        Assert.assertEquals(packet, decodedPacket);
    }

    @Test
    public void shouldThrowIfHeaderInvalid() {

        Exception exception = Assert.assertThrows(RuntimeException.class, () -> {
            byte[] encodedPacket = packetBuilder.encode(packet);
            encodedPacket[1] = 0x0;
            Packet decodedPacket = packetBuilder.decode(encodedPacket);
        });

        String expectedMessage = "Header is not valid";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowIfMessageInvalid() {

        Exception exception = Assert.assertThrows(RuntimeException.class, () -> {
            byte[] encodedPacket = packetBuilder.encode(packet);
            encodedPacket[16] = 0x0;
            Packet decodedPacket = packetBuilder.decode(encodedPacket);
        });

        String expectedMessage = "Message is not valid";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
}
