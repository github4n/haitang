/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.bmn.thrift.cs;

import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2019-02-15")
public class BmnResponse implements org.apache.thrift.TBase<BmnResponse, BmnResponse._Fields>, java.io.Serializable, Cloneable, Comparable<BmnResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BmnResponse");

  private static final org.apache.thrift.protocol.TField TID_FIELD_DESC = new org.apache.thrift.protocol.TField("tid", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField ERROR_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("errorCode", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField MESSAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("message", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField DATA_FIELD_DESC = new org.apache.thrift.protocol.TField("data", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField DID_FIELD_DESC = new org.apache.thrift.protocol.TField("did", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BmnResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BmnResponseTupleSchemeFactory());
  }

  public long tid; // required
  /**
   * 
   * @see com.bmn.thrift.ErrorCode
   */
  public com.bmn.thrift.ErrorCode errorCode; // required
  public String message; // optional
  public ByteBuffer data; // optional
  public String did; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TID((short)1, "tid"),
    /**
     * 
     * @see com.bmn.thrift.ErrorCode
     */
    ERROR_CODE((short)2, "errorCode"),
    MESSAGE((short)3, "message"),
    DATA((short)4, "data"),
    DID((short)5, "did");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TID
          return TID;
        case 2: // ERROR_CODE
          return ERROR_CODE;
        case 3: // MESSAGE
          return MESSAGE;
        case 4: // DATA
          return DATA;
        case 5: // DID
          return DID;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.MESSAGE, _Fields.DATA, _Fields.DID};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TID, new org.apache.thrift.meta_data.FieldMetaData("tid", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ERROR_CODE, new org.apache.thrift.meta_data.FieldMetaData("errorCode", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.bmn.thrift.ErrorCode.class)));
    tmpMap.put(_Fields.MESSAGE, new org.apache.thrift.meta_data.FieldMetaData("message", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DATA, new org.apache.thrift.meta_data.FieldMetaData("data", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.DID, new org.apache.thrift.meta_data.FieldMetaData("did", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BmnResponse.class, metaDataMap);
  }

  public BmnResponse() {
  }

  public BmnResponse(
    long tid,
    com.bmn.thrift.ErrorCode errorCode)
  {
    this();
    this.tid = tid;
    setTidIsSet(true);
    this.errorCode = errorCode;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BmnResponse(BmnResponse other) {
    __isset_bitfield = other.__isset_bitfield;
    this.tid = other.tid;
    if (other.isSetErrorCode()) {
      this.errorCode = other.errorCode;
    }
    if (other.isSetMessage()) {
      this.message = other.message;
    }
    if (other.isSetData()) {
      this.data = org.apache.thrift.TBaseHelper.copyBinary(other.data);
    }
    if (other.isSetDid()) {
      this.did = other.did;
    }
  }

  public BmnResponse deepCopy() {
    return new BmnResponse(this);
  }

  @Override
  public void clear() {
    setTidIsSet(false);
    this.tid = 0;
    this.errorCode = null;
    this.message = null;
    this.data = null;
    this.did = null;
  }

  public long getTid() {
    return this.tid;
  }

  public BmnResponse setTid(long tid) {
    this.tid = tid;
    setTidIsSet(true);
    return this;
  }

  public void unsetTid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TID_ISSET_ID);
  }

  /** Returns true if field tid is set (has been assigned a value) and false otherwise */
  public boolean isSetTid() {
    return EncodingUtils.testBit(__isset_bitfield, __TID_ISSET_ID);
  }

  public void setTidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TID_ISSET_ID, value);
  }

  /**
   * 
   * @see com.bmn.thrift.ErrorCode
   */
  public com.bmn.thrift.ErrorCode getErrorCode() {
    return this.errorCode;
  }

  /**
   * 
   * @see com.bmn.thrift.ErrorCode
   */
  public BmnResponse setErrorCode(com.bmn.thrift.ErrorCode errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  public void unsetErrorCode() {
    this.errorCode = null;
  }

  /** Returns true if field errorCode is set (has been assigned a value) and false otherwise */
  public boolean isSetErrorCode() {
    return this.errorCode != null;
  }

  public void setErrorCodeIsSet(boolean value) {
    if (!value) {
      this.errorCode = null;
    }
  }

  public String getMessage() {
    return this.message;
  }

  public BmnResponse setMessage(String message) {
    this.message = message;
    return this;
  }

  public void unsetMessage() {
    this.message = null;
  }

  /** Returns true if field message is set (has been assigned a value) and false otherwise */
  public boolean isSetMessage() {
    return this.message != null;
  }

  public void setMessageIsSet(boolean value) {
    if (!value) {
      this.message = null;
    }
  }

  public byte[] getData() {
    setData(org.apache.thrift.TBaseHelper.rightSize(data));
    return data == null ? null : data.array();
  }

  public ByteBuffer bufferForData() {
    return org.apache.thrift.TBaseHelper.copyBinary(data);
  }

  public BmnResponse setData(byte[] data) {
    this.data = data == null ? (ByteBuffer)null : ByteBuffer.wrap(Arrays.copyOf(data, data.length));
    return this;
  }

  public BmnResponse setData(ByteBuffer data) {
    this.data = org.apache.thrift.TBaseHelper.copyBinary(data);
    return this;
  }

  public void unsetData() {
    this.data = null;
  }

  /** Returns true if field data is set (has been assigned a value) and false otherwise */
  public boolean isSetData() {
    return this.data != null;
  }

  public void setDataIsSet(boolean value) {
    if (!value) {
      this.data = null;
    }
  }

  public String getDid() {
    return this.did;
  }

  public BmnResponse setDid(String did) {
    this.did = did;
    return this;
  }

  public void unsetDid() {
    this.did = null;
  }

  /** Returns true if field did is set (has been assigned a value) and false otherwise */
  public boolean isSetDid() {
    return this.did != null;
  }

  public void setDidIsSet(boolean value) {
    if (!value) {
      this.did = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TID:
      if (value == null) {
        unsetTid();
      } else {
        setTid((Long)value);
      }
      break;

    case ERROR_CODE:
      if (value == null) {
        unsetErrorCode();
      } else {
        setErrorCode((com.bmn.thrift.ErrorCode)value);
      }
      break;

    case MESSAGE:
      if (value == null) {
        unsetMessage();
      } else {
        setMessage((String)value);
      }
      break;

    case DATA:
      if (value == null) {
        unsetData();
      } else {
        setData((ByteBuffer)value);
      }
      break;

    case DID:
      if (value == null) {
        unsetDid();
      } else {
        setDid((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TID:
      return getTid();

    case ERROR_CODE:
      return getErrorCode();

    case MESSAGE:
      return getMessage();

    case DATA:
      return getData();

    case DID:
      return getDid();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TID:
      return isSetTid();
    case ERROR_CODE:
      return isSetErrorCode();
    case MESSAGE:
      return isSetMessage();
    case DATA:
      return isSetData();
    case DID:
      return isSetDid();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BmnResponse)
      return this.equals((BmnResponse)that);
    return false;
  }

  public boolean equals(BmnResponse that) {
    if (that == null)
      return false;

    boolean this_present_tid = true;
    boolean that_present_tid = true;
    if (this_present_tid || that_present_tid) {
      if (!(this_present_tid && that_present_tid))
        return false;
      if (this.tid != that.tid)
        return false;
    }

    boolean this_present_errorCode = true && this.isSetErrorCode();
    boolean that_present_errorCode = true && that.isSetErrorCode();
    if (this_present_errorCode || that_present_errorCode) {
      if (!(this_present_errorCode && that_present_errorCode))
        return false;
      if (!this.errorCode.equals(that.errorCode))
        return false;
    }

    boolean this_present_message = true && this.isSetMessage();
    boolean that_present_message = true && that.isSetMessage();
    if (this_present_message || that_present_message) {
      if (!(this_present_message && that_present_message))
        return false;
      if (!this.message.equals(that.message))
        return false;
    }

    boolean this_present_data = true && this.isSetData();
    boolean that_present_data = true && that.isSetData();
    if (this_present_data || that_present_data) {
      if (!(this_present_data && that_present_data))
        return false;
      if (!this.data.equals(that.data))
        return false;
    }

    boolean this_present_did = true && this.isSetDid();
    boolean that_present_did = true && that.isSetDid();
    if (this_present_did || that_present_did) {
      if (!(this_present_did && that_present_did))
        return false;
      if (!this.did.equals(that.did))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_tid = true;
    list.add(present_tid);
    if (present_tid)
      list.add(tid);

    boolean present_errorCode = true && (isSetErrorCode());
    list.add(present_errorCode);
    if (present_errorCode)
      list.add(errorCode.getValue());

    boolean present_message = true && (isSetMessage());
    list.add(present_message);
    if (present_message)
      list.add(message);

    boolean present_data = true && (isSetData());
    list.add(present_data);
    if (present_data)
      list.add(data);

    boolean present_did = true && (isSetDid());
    list.add(present_did);
    if (present_did)
      list.add(did);

    return list.hashCode();
  }

  @Override
  public int compareTo(BmnResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTid()).compareTo(other.isSetTid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tid, other.tid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetErrorCode()).compareTo(other.isSetErrorCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErrorCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.errorCode, other.errorCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMessage()).compareTo(other.isSetMessage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.message, other.message);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetData()).compareTo(other.isSetData());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetData()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.data, other.data);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDid()).compareTo(other.isSetDid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.did, other.did);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("BmnResponse(");
    boolean first = true;

    sb.append("tid:");
    sb.append(this.tid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("errorCode:");
    if (this.errorCode == null) {
      sb.append("null");
    } else {
      sb.append(this.errorCode);
    }
    first = false;
    if (isSetMessage()) {
      if (!first) sb.append(", ");
      sb.append("message:");
      if (this.message == null) {
        sb.append("null");
      } else {
        sb.append(this.message);
      }
      first = false;
    }
    if (isSetData()) {
      if (!first) sb.append(", ");
      sb.append("data:");
      if (this.data == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.data, sb);
      }
      first = false;
    }
    if (isSetDid()) {
      if (!first) sb.append(", ");
      sb.append("did:");
      if (this.did == null) {
        sb.append("null");
      } else {
        sb.append(this.did);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // alas, we cannot check 'tid' because it's a primitive and you chose the non-beans generator.
    if (errorCode == null) {
      throw new TProtocolException("Required field 'errorCode' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class BmnResponseStandardSchemeFactory implements SchemeFactory {
    public BmnResponseStandardScheme getScheme() {
      return new BmnResponseStandardScheme();
    }
  }

  private static class BmnResponseStandardScheme extends StandardScheme<BmnResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BmnResponse struct) throws TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // TID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.tid = iprot.readI64();
              struct.setTidIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ERROR_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.errorCode = com.bmn.thrift.ErrorCode.findByValue(iprot.readI32());
              struct.setErrorCodeIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MESSAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.message = iprot.readString();
              struct.setMessageIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DATA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.data = iprot.readBinary();
              struct.setDataIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // DID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.did = iprot.readString();
              struct.setDidIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetTid()) {
        throw new TProtocolException("Required field 'tid' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, BmnResponse struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(TID_FIELD_DESC);
      oprot.writeI64(struct.tid);
      oprot.writeFieldEnd();
      if (struct.errorCode != null) {
        oprot.writeFieldBegin(ERROR_CODE_FIELD_DESC);
        oprot.writeI32(struct.errorCode.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.message != null) {
        if (struct.isSetMessage()) {
          oprot.writeFieldBegin(MESSAGE_FIELD_DESC);
          oprot.writeString(struct.message);
          oprot.writeFieldEnd();
        }
      }
      if (struct.data != null) {
        if (struct.isSetData()) {
          oprot.writeFieldBegin(DATA_FIELD_DESC);
          oprot.writeBinary(struct.data);
          oprot.writeFieldEnd();
        }
      }
      if (struct.did != null) {
        if (struct.isSetDid()) {
          oprot.writeFieldBegin(DID_FIELD_DESC);
          oprot.writeString(struct.did);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BmnResponseTupleSchemeFactory implements SchemeFactory {
    public BmnResponseTupleScheme getScheme() {
      return new BmnResponseTupleScheme();
    }
  }

  private static class BmnResponseTupleScheme extends TupleScheme<BmnResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BmnResponse struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI64(struct.tid);
      oprot.writeI32(struct.errorCode.getValue());
      BitSet optionals = new BitSet();
      if (struct.isSetMessage()) {
        optionals.set(0);
      }
      if (struct.isSetData()) {
        optionals.set(1);
      }
      if (struct.isSetDid()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetMessage()) {
        oprot.writeString(struct.message);
      }
      if (struct.isSetData()) {
        oprot.writeBinary(struct.data);
      }
      if (struct.isSetDid()) {
        oprot.writeString(struct.did);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BmnResponse struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.tid = iprot.readI64();
      struct.setTidIsSet(true);
      struct.errorCode = com.bmn.thrift.ErrorCode.findByValue(iprot.readI32());
      struct.setErrorCodeIsSet(true);
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.message = iprot.readString();
        struct.setMessageIsSet(true);
      }
      if (incoming.get(1)) {
        struct.data = iprot.readBinary();
        struct.setDataIsSet(true);
      }
      if (incoming.get(2)) {
        struct.did = iprot.readString();
        struct.setDidIsSet(true);
      }
    }
  }

}

