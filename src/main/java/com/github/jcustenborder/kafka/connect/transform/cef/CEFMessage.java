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
  @Override
  public Date timestamp() {
    return null;
  }

  @Override
  public String host() {
    return null;
  }

  @Override
  public int cefVersion() {
    return 0;
  }

  @Override
  public String deviceVendor() {
    return null;
  }

  @Override
  public String deviceProduct() {
    return null;
  }

  @Override
  public String deviceVersion() {
    return null;
  }

  @Override
  public String deviceEventClassId() {
    return null;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public String severity() {
    return null;
  }

  @Override
  public Map<String, String> extensions() {
    return null;
  }

  public static class Builder implements Message.Builder {
    final Struct value;

    public Builder(Struct value) {
      this.value = value;
    }

    @Override
    public Message.Builder timestamp(Date date) {
      return this;
    }

    @Override
    public Message.Builder host(String s) {
      return this;
    }

    @Override
    public Message.Builder cefVersion(int i) {
      return this;
    }

    @Override
    public Message.Builder deviceVendor(String s) {
      return this;
    }

    @Override
    public Message.Builder deviceProduct(String s) {
      return this;
    }

    @Override
    public Message.Builder deviceVersion(String s) {
      return this;
    }

    @Override
    public Message.Builder deviceEventClassId(String s) {
      return this;
    }

    @Override
    public Message.Builder name(String s) {
      return this;
    }

    @Override
    public Message.Builder severity(String s) {
      return this;
    }

    @Override
    public Message.Builder extensions(Map<String, String> map) {
      return this;
    }

    @Override
    public Date timestamp() {
      long value = this.value.getInt64("date");
      return Timestamp.toLogical(Timestamp.SCHEMA, value);
    }

    @Override
    public String host() {
      return null;
    }

    @Override
    public int cefVersion() {
      return 0;
    }

    @Override
    public String deviceVendor() {
      return null;
    }

    @Override
    public String deviceProduct() {
      return null;
    }

    @Override
    public String deviceVersion() {
      return null;
    }

    @Override
    public String deviceEventClassId() {
      return null;
    }

    @Override
    public String name() {
      return null;
    }

    @Override
    public String severity() {
      return null;
    }

    @Override
    public Map<String, String> extensions() {
      return null;
    }

    @Override
    public Message build() {
      return null;
    }
  }
}
