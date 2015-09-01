package sepim.server.net.handlers;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.CorruptedFrameException;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;


public class TestDecoder extends FrameDecoder {

    private final int maxFrameLength;
    private final int lengthFieldOffset;
    private final int lengthFieldLength;
    private final int lengthFieldEndOffset;
    private final int lengthAdjustment;
    private final int initialBytesToStrip;
    private final boolean failFast;
    private boolean discardingTooLongFrame;
    private long tooLongFrameLength;
    private long bytesToDiscard;
    
    public TestDecoder(int maxFrameLength,int lengthFieldOffset, int lengthFieldLength) {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, 0, 0);
    }
    public TestDecoder(int maxFrameLength,int lengthFieldOffset, int lengthFieldLength,int lengthAdjustment, int initialBytesToStrip) {
        this(maxFrameLength,lengthFieldOffset, lengthFieldLength, lengthAdjustment,initialBytesToStrip, false);
    }
    public TestDecoder(
            int maxFrameLength,
            int lengthFieldOffset, int lengthFieldLength,
            int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        if (maxFrameLength <= 0) {
            throw new IllegalArgumentException(
                    "maxFrameLength must be a positive integer: " +
                    maxFrameLength);
        }

        if (lengthFieldOffset < 0) {
            throw new IllegalArgumentException(
                    "lengthFieldOffset must be a non-negative integer: " +
                    lengthFieldOffset);
        }

        if (initialBytesToStrip < 0) {
            throw new IllegalArgumentException(
                    "initialBytesToStrip must be a non-negative integer: " +
                    initialBytesToStrip);
        }

        if (lengthFieldLength != 1 && lengthFieldLength != 2 &&
            lengthFieldLength != 3 && lengthFieldLength != 4 &&
            lengthFieldLength != 8) {
            throw new IllegalArgumentException(
                    "lengthFieldLength must be either 1, 2, 3, 4, or 8: " +
                    lengthFieldLength);
        }

        if (lengthFieldOffset > maxFrameLength - lengthFieldLength) {
            throw new IllegalArgumentException(
                    "maxFrameLength (" + maxFrameLength + ") " +
                    "must be equal to or greater than " +
                    "lengthFieldOffset (" + lengthFieldOffset + ") + " +
                    "lengthFieldLength (" + lengthFieldLength + ").");
        }

        this.maxFrameLength = maxFrameLength;
        this.lengthFieldOffset = lengthFieldOffset;
        this.lengthFieldLength = lengthFieldLength;
        this.lengthAdjustment = lengthAdjustment;
        lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
        this.initialBytesToStrip = initialBytesToStrip;
        this.failFast = failFast;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception 
    {
        if (discardingTooLongFrame)
        {
            long bytesToDiscard = this.bytesToDiscard;
            int localBytesToDiscard = (int) Math.min(bytesToDiscard, buffer.readableBytes());
            buffer.skipBytes(localBytesToDiscard);
            bytesToDiscard -= localBytesToDiscard;
            this.bytesToDiscard = bytesToDiscard;
            failIfNecessary(ctx, false);
            return null;
        }
        if (buffer.readableBytes() < lengthFieldEndOffset) 
        {
            return null;
        }

        int actualLengthFieldOffset = buffer.readerIndex() + lengthFieldOffset;
        long frameLength;
        switch (lengthFieldLength) //字符串长度占用的字节数
        {
	        case 1:
	            frameLength = buffer.getUnsignedByte(actualLengthFieldOffset);
	            break;
	        case 2:
	            frameLength = buffer.getUnsignedShort(actualLengthFieldOffset);
	            break;
	        case 3:
	            frameLength = buffer.getUnsignedMedium(actualLengthFieldOffset);
	            break;
	        case 4:
	        	String s="";
	        	for(int i=0;i<4;i++)
	        		s+=(char)buffer.getByte(actualLengthFieldOffset+i);
	            frameLength = Integer.parseInt(s,16);
	            break;
	        case 8:
	            frameLength = buffer.getLong(actualLengthFieldOffset);
	            break;
	        default:
	            throw new Error("should not reach here");
        }
        if (frameLength < 0) {
            buffer.skipBytes(lengthFieldEndOffset);
            throw new CorruptedFrameException(
                    "negative pre-adjustment length field: " + frameLength);
        }

        frameLength += lengthAdjustment + lengthFieldEndOffset;
        if (frameLength < lengthFieldEndOffset) {
            buffer.skipBytes(lengthFieldEndOffset);
            throw new CorruptedFrameException(
                    "Adjusted frame length (" + frameLength + ") is less " +
                    "than lengthFieldEndOffset: " + lengthFieldEndOffset);
        }

        if (frameLength > maxFrameLength) {
            // Enter the discard mode and discard everything received so far.
            discardingTooLongFrame = true;
            tooLongFrameLength = frameLength;
            bytesToDiscard = frameLength - buffer.readableBytes();
            buffer.skipBytes(buffer.readableBytes());
            failIfNecessary(ctx, true);
            return null;
        }

        // never overflows because it's less than maxFrameLength
        int frameLengthInt = (int) frameLength;
        if (buffer.readableBytes() < frameLengthInt) {
            return null;
        }

        if (initialBytesToStrip > frameLengthInt) {
            buffer.skipBytes(frameLengthInt);
            throw new CorruptedFrameException(
                    "Adjusted frame length (" + frameLength + ") is less " +
                    "than initialBytesToStrip: " + initialBytesToStrip);
        }
        buffer.skipBytes(initialBytesToStrip);

        // extract frame
        int readerIndex = buffer.readerIndex();
        int actualFrameLength = frameLengthInt - initialBytesToStrip;
        ChannelBuffer frame = extractFrame(buffer, readerIndex, actualFrameLength);
        buffer.readerIndex(readerIndex + actualFrameLength);
        
        return frame;
    }

    private void failIfNecessary(ChannelHandlerContext ctx, boolean firstDetectionOfTooLongFrame) {
        if (bytesToDiscard == 0) {
            // Reset to the initial state and tell the handlers that
            // the frame was too large.
            long tooLongFrameLength = this.tooLongFrameLength;
            this.tooLongFrameLength = 0;
            discardingTooLongFrame = false;
            if ((!failFast) ||
                (failFast && firstDetectionOfTooLongFrame))
            {
                fail(ctx, tooLongFrameLength);
            }
        } else {
            // Keep discarding and notify handlers if necessary.
            if (failFast && firstDetectionOfTooLongFrame)
            {
                fail(ctx, this.tooLongFrameLength);
            }
        }

    }
    protected ChannelBuffer extractFrame(ChannelBuffer buffer, int index, int length) {
        ChannelBuffer frame = buffer.factory().getBuffer(length);
        frame.writeBytes(buffer, index, length);
        return frame;
    }

    private void fail(ChannelHandlerContext ctx, long frameLength) {
        if (frameLength > 0) {
            Channels.fireExceptionCaught(
                    ctx.getChannel(),
                    new TooLongFrameException(
                            "Adjusted frame length exceeds " + maxFrameLength +
                            ": " + frameLength + " - discarded"));
        } else {
            Channels.fireExceptionCaught(
                    ctx.getChannel(),
                    new TooLongFrameException(
                            "Adjusted frame length exceeds " + maxFrameLength +
                            " - discarding"));
        }
    }
}
