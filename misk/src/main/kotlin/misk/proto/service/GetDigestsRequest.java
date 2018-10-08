// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: service.proto
package misk.proto.service;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import okio.ByteString;

public final class GetDigestsRequest extends Message<GetDigestsRequest, GetDigestsRequest.Builder> {
  public static final ProtoAdapter<GetDigestsRequest> ADAPTER = new ProtoAdapter_GetDigestsRequest();

  private static final long serialVersionUID = 0L;

  public static final Long DEFAULT_WINDOWS_END_FROM_MS = 0L;

  public static final Long DEFAULT_WINDOWS_END_TO_MS = 0L;

  /**
   * Earliest end time of windows to return, inclusive.
   */
  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#INT64"
  )
  public final Long windows_end_from_ms;

  /**
   * Latest end time of windows to return, inclusive.
   */
  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#INT64"
  )
  public final Long windows_end_to_ms;

  public GetDigestsRequest(Long windows_end_from_ms, Long windows_end_to_ms) {
    this(windows_end_from_ms, windows_end_to_ms, ByteString.EMPTY);
  }

  public GetDigestsRequest(Long windows_end_from_ms, Long windows_end_to_ms,
      ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.windows_end_from_ms = windows_end_from_ms;
    this.windows_end_to_ms = windows_end_to_ms;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.windows_end_from_ms = windows_end_from_ms;
    builder.windows_end_to_ms = windows_end_to_ms;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof GetDigestsRequest)) return false;
    GetDigestsRequest o = (GetDigestsRequest) other;
    return unknownFields().equals(o.unknownFields())
        && Internal.equals(windows_end_from_ms, o.windows_end_from_ms)
        && Internal.equals(windows_end_to_ms, o.windows_end_to_ms);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (windows_end_from_ms != null ? windows_end_from_ms.hashCode() : 0);
      result = result * 37 + (windows_end_to_ms != null ? windows_end_to_ms.hashCode() : 0);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (windows_end_from_ms != null) builder.append(", windows_end_from_ms=").append(windows_end_from_ms);
    if (windows_end_to_ms != null) builder.append(", windows_end_to_ms=").append(windows_end_to_ms);
    return builder.replace(0, 2, "GetDigestsRequest{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<GetDigestsRequest, Builder> {
    public Long windows_end_from_ms;

    public Long windows_end_to_ms;

    public Builder() {
    }

    /**
     * Earliest end time of windows to return, inclusive.
     */
    public Builder windows_end_from_ms(Long windows_end_from_ms) {
      this.windows_end_from_ms = windows_end_from_ms;
      return this;
    }

    /**
     * Latest end time of windows to return, inclusive.
     */
    public Builder windows_end_to_ms(Long windows_end_to_ms) {
      this.windows_end_to_ms = windows_end_to_ms;
      return this;
    }

    @Override
    public GetDigestsRequest build() {
      return new GetDigestsRequest(windows_end_from_ms, windows_end_to_ms, super.buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_GetDigestsRequest extends ProtoAdapter<GetDigestsRequest> {
    public ProtoAdapter_GetDigestsRequest() {
      super(FieldEncoding.LENGTH_DELIMITED, GetDigestsRequest.class);
    }

    @Override
    public int encodedSize(GetDigestsRequest value) {
      return ProtoAdapter.INT64.encodedSizeWithTag(1, value.windows_end_from_ms)
          + ProtoAdapter.INT64.encodedSizeWithTag(2, value.windows_end_to_ms)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, GetDigestsRequest value) throws IOException {
      ProtoAdapter.INT64.encodeWithTag(writer, 1, value.windows_end_from_ms);
      ProtoAdapter.INT64.encodeWithTag(writer, 2, value.windows_end_to_ms);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public GetDigestsRequest decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.windows_end_from_ms(ProtoAdapter.INT64.decode(reader)); break;
          case 2: builder.windows_end_to_ms(ProtoAdapter.INT64.decode(reader)); break;
          default: {
            FieldEncoding fieldEncoding = reader.peekFieldEncoding();
            Object value = fieldEncoding.rawProtoAdapter().decode(reader);
            builder.addUnknownField(tag, fieldEncoding, value);
          }
        }
      }
      reader.endMessage(token);
      return builder.build();
    }

    @Override
    public GetDigestsRequest redact(GetDigestsRequest value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
