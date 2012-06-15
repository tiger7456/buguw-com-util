package com.buguw.common.util;

import com.buguw.common.multilang.MultiLanguageResourceBundle;

public class ByteBuffer
{
  private static final float DEFAULT_FACTOR = 0.75F;
  private byte[] buf = null;
  private int size = 0;
  private float factor = 0.75F;

  public ByteBuffer()
  {
    this(256, 0.75F);
  }

  public ByteBuffer(int paramInt)
  {
    this(paramInt, 0.75F);
  }

  public ByteBuffer(int paramInt, float paramFloat)
  {
    this.buf = new byte[paramInt];
    this.factor = paramFloat;
  }

  public int capacity()
  {
    return this.buf.length;
  }

  public int size()
  {
    return this.size;
  }

  public void append(byte paramByte)
  {
    if (this.size == this.buf.length)
      expand(1);
    this.buf[(this.size++)] = paramByte;
  }

  public void append(byte[] paramArrayOfByte)
  {
    if (null == paramArrayOfByte)
      throw new IllegalArgumentException("bytes is null");
    append(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public void append(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (null == paramArrayOfByte)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("null", new String[] { "bytes" }));
    if (this.size + paramInt2 > this.buf.length)
    {
      expand(this.size + paramInt2 - this.buf.length);
      append(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    System.arraycopy(paramArrayOfByte, paramInt1, this.buf, this.size, paramInt2);
    this.size += paramInt2;
  }

  public void append(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
  {
    if (null == paramByteBuffer)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("null", new String[] { "bBuf" }));
    append(paramByteBuffer.getBytesAt(paramInt1, paramInt2));
  }

  public void append(ByteBuffer paramByteBuffer)
  {
    if (null == paramByteBuffer)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("null", new String[] { "bBuf" }));
    append(paramByteBuffer, 0, paramByteBuffer.size());
  }

  private void expand(int paramInt)
  {
    byte[] arrayOfByte = this.buf;
    int i = (int)(this.buf.length * this.factor);
    if (this.buf.length - this.size + i >= paramInt)
      this.buf = new byte[this.buf.length + i];
    else
      this.buf = new byte[this.buf.length + paramInt];
    System.arraycopy(arrayOfByte, 0, this.buf, 0, this.size);
    arrayOfByte = null;
  }

  public byte getByteAt(int paramInt)
  {
    return this.buf[paramInt];
  }

  public byte[] getBytesAt(int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(this.buf, paramInt1, arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }

  public void getBytesAt(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    if (null == paramArrayOfByte)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("ByteBuffer.getBytesAt.destByte.null"));
    if (paramInt1 + paramInt3 > this.size)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("ByteBuffer.index.outOfBounds", new String[] { "" + paramInt1, "" + paramInt3, "" + this.size }));
    System.arraycopy(this.buf, paramInt1, paramArrayOfByte, paramInt2, paramInt3);
  }

  public void getBytesAt(int paramInt, byte[] paramArrayOfByte)
  {
    if (null == paramArrayOfByte)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("ByteBuffer.getBytesAt.destByte.null"));
    if (paramInt + paramArrayOfByte.length > this.size)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("ByteBuffer.index.outOfBounds", new String[] { "" + paramInt, "" + paramArrayOfByte.length, "" + this.size }));
    System.arraycopy(this.buf, paramInt, paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public void setByteAt(int paramInt, byte paramByte)
  {
    this.buf[paramInt] = paramByte;
  }

  public void setBytesAt(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    if (null == paramArrayOfByte)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("ByteBuffer.sourceByte.null"));
    if (paramInt1 + paramInt3 > this.size)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("ByteBuffer.index.outOfBounds", new String[] { "" + paramInt1, "" + paramInt3, "" + this.size }));
    for (int i = 0; i < paramInt3; i++)
      this.buf[(paramInt1 + i)] = paramArrayOfByte[(paramInt2 + i)];
  }

  public void setBytesAt(int paramInt, byte[] paramArrayOfByte)
  {
    if (null == paramArrayOfByte)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("ByteBuffer.sourceByte.null"));
    setBytesAt(paramInt, paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public int indexOf(byte[] paramArrayOfByte, int paramInt)
  {
    return CodeUtil.byteArrayIndexOf(this.buf, paramArrayOfByte, paramInt);
  }

  public int indexOf(byte[] paramArrayOfByte)
  {
    return indexOf(paramArrayOfByte, 0);
  }

  public int lastIndexOf(byte[] paramArrayOfByte)
  {
    return lastIndexOf(paramArrayOfByte, this.size);
  }

  public int lastIndexOf(byte[] paramArrayOfByte, int paramInt)
  {
    return CodeUtil.byteArrayLastIndexOf(this.buf, paramArrayOfByte, paramInt);
  }

  public void remove(int paramInt)
  {
    remove(paramInt, 1);
  }

  public void remove(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= size())
      throw new IllegalArgumentException("fromIndex >= buffer's size!");
    if (0 > paramInt1)
      throw new IllegalArgumentException("fromIndex < 0!");
    if (0 >= paramInt2)
      throw new IllegalArgumentException("length <= 0");
    byte[] arrayOfByte = new byte[size() - paramInt2];
    System.arraycopy(this.buf, 0, arrayOfByte, 0, paramInt1);
    System.arraycopy(this.buf, paramInt1 + paramInt2, arrayOfByte, paramInt1, arrayOfByte.length - paramInt1);
    this.size -= paramInt2;
    this.buf = arrayOfByte;
  }

  public int indexOf(byte paramByte, int paramInt)
  {
    for (int i = paramInt; i < this.size; i++)
      if (paramByte == this.buf[i])
        return i;
    return -1;
  }

  public byte[] toBytes()
  {
    byte[] arrayOfByte = new byte[this.size];
    System.arraycopy(this.buf, 0, arrayOfByte, 0, this.size);
    return arrayOfByte;
  }

  public String toString()
  {
    return new String(toBytes());
  }

  public void clear()
  {
    this.size = 0;
  }
}

/* Location:           F:\360data\重要数据\桌面\giantstone-common-util.jar
 * Qualified Name:     com.giantstone.common.util.ByteBuffer
 * JD-Core Version:    0.6.0
 */