package sanchenko_pr1;

public interface IPacketBuilder {
    byte[] encode(Packet packet) throws RuntimeException;
    Packet decode(byte[] data) throws RuntimeException;
}
