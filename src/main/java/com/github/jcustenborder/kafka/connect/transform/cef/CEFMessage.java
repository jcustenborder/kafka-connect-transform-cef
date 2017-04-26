/**
 * Copyright © 2017 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.connect.transform.cef;

import com.github.jcustenborder.cef.Message;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.data.Timestamp;

import java.util.Date;
import java.util.Map;

class CEFMessage implements Message {
  final Struct value;

  CEFMessage(Struct value) {
    this.value = value;
  }

  @Override
  public Date timestamp() {
    long value = this.value.getInt64("date");
    return Timestamp.toLogical(Timestamp.SCHEMA, value);
  }

  @Override
  public String host() {
    return this.value.getString(Constants.HOST);
  }

  @Override
  public int cefVersion() {
    return this.value.getInt32(Constants.CEFVERSION);
  }

  @Override
  public String deviceVendor() {
    return this.value.getString(Constants.DEVICEVENDOR);
  }

  @Override
  public String deviceProduct() {
    return this.value.getString(Constants.DEVICEPRODUCT);
  }

  @Override
  public String deviceVersion() {
    return this.value.getString(Constants.DEVICEVERSION);
  }

  @Override
  public String deviceEventClassId() {
    return this.value.getString(Constants.DEVICEEVENTCLASSID);
  }

  @Override
  public String name() {
    return this.value.getString(Constants.NAME);
  }

  @Override
  public String severity() {
    return this.value.getString(Constants.SEVERITY);
  }

  @Override
  public Map<String, String> extensions() {
    return this.value.getMap(Constants.EXTENSIONS);
  }

  public static class Builder implements Message.Builder {
    final Struct value;

    public Builder(Struct value) {
      this.value = value;
    }

    @Override
    public Message.Builder timestamp(Date date) {
      this.value.put(Constants.DATE, date);
      return this;
    }

    @Override
    public Message.Builder host(String s) {
      this.value.put(Constants.HOST, s);
      return this;
    }

    @Override
    public Message.Builder cefVersion(int i) {
      this.value.put(Constants.CEFVERSION, i);
      return this;
    }

    @Override
    public Message.Builder deviceVendor(String s) {
      this.value.put(Constants.DEVICEVENDOR, s);
      return this;
    }

    @Override
    public Message.Builder deviceProduct(String s) {
      this.value.put(Constants.DEVICEPRODUCT, s);
      return this;
    }

    @Override
    public Message.Builder deviceVersion(String s) {
      this.value.put(Constants.DEVICEVERSION, s);
      return this;
    }

    @Override
    public Message.Builder deviceEventClassId(String s) {
      this.value.put(Constants.DEVICEEVENTCLASSID, s);
      return this;
    }

    @Override
    public Message.Builder name(String s) {
      this.value.put(Constants.NAME, s);
      return this;
    }

    @Override
    public Message.Builder severity(String s) {
      this.value.put(Constants.SEVERITY, s);
      return this;
    }

    @Override
    public Message.Builder extensions(Map<String, String> map) {
      this.value.put(Constants.EXTENSIONS, map);
      return this;
    }

    @Override
    public Date timestamp() {
      long value = this.value.getInt64("date");
      return Timestamp.toLogical(Timestamp.SCHEMA, value);
    }

    @Override
    public String host() {
      return this.value.getString(Constants.HOST);
    }

    @Override
    public int cefVersion() {
      return this.value.getInt32(Constants.CEFVERSION);
    }

    @Override
    public String deviceVendor() {
      return this.value.getString(Constants.DEVICEVENDOR);
    }

    @Override
    public String deviceProduct() {
      return this.value.getString(Constants.DEVICEPRODUCT);
    }

    @Override
    public String deviceVersion() {
      return this.value.getString(Constants.DEVICEVERSION);
    }

    @Override
    public String deviceEventClassId() {
      return this.value.getString(Constants.DEVICEEVENTCLASSID);
    }

    @Override
    public String name() {
      return this.value.getString(Constants.NAME);
    }

    @Override
    public String severity() {
      return this.value.getString(Constants.SEVERITY);
    }

    @Override
    public Map<String, String> extensions() {
      return this.value.getMap(Constants.EXTENSIONS);
    }

    @Override
    public Message build() {
      return new CEFMessage(this.value);
    }
  }
}
