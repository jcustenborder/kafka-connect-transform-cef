/**
 * Copyright © 2017 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.connect.transform.cef;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Timestamp;

class Constants {

  public static final String DATE = "date";
  public static final String LEVEL = "level";
  public static final String MESSAGE = "message";
  public static final String CEFVERSION = "cefVersion";
  public static final String DEVICEVENDOR = "deviceVendor";
  public static final String DEVICEPRODUCT = "deviceProduct";
  public static final String DEVICEVERSION = "deviceVersion";
  public static final String DEVICEEVENTCLASSID = "deviceEventClassId";
  public static final String NAME = "name";
  public static final String SEVERITY = "severity";
  public static final String EXTENSIONS = "extensions";
  public static final String HOST = "host";
  public static final String FACILITY = "facility";
  public static final Schema SCHEMA = SchemaBuilder.struct()
      .name("com.github.jcustenborder.kafka.connect.transform.cef.CEFMessage")
      .doc("The parsed representation of a CEF Message.")
      .field(
          DATE,
          Timestamp.builder().optional().doc("The timestamp of the message.").build()
      )
      .field(
          FACILITY,
          SchemaBuilder.int32().optional().doc("The facility of the message.").build()
      )
      .field(
          HOST,
          SchemaBuilder.string().optional().doc("The host of the message.").build()
      )
      .field(
          LEVEL,
          SchemaBuilder.int32().optional().doc("The level of the syslog message as defined by :rfc:`5424`.").build()
      )
      .field(
          MESSAGE,
          SchemaBuilder.string().doc("Unparsed version of the message.").build()
      )
      .field(
          CEFVERSION,
          SchemaBuilder.int32().doc("Version of CEF the message is using.").build()
      )
      .field(
          DEVICEVENDOR,
          SchemaBuilder.string().doc("Vendor of the device that logged the message.").build()
      )
      .field(
          DEVICEPRODUCT,
          SchemaBuilder.string().doc("The product that logged the message.").build()
      )
      .field(
          DEVICEVERSION,
          SchemaBuilder.string().doc("The version of the device that is logging the message.").build()
      )
      .field(
          DEVICEEVENTCLASSID,
          SchemaBuilder.string().doc("The internal event id for the message.").build()
      )
      .field(
          NAME,
          SchemaBuilder.string().doc("Name of the event. This is typically a short description.").build()
      )
      .field(
          SEVERITY,
          SchemaBuilder.string().doc("The severity of the message.").build()
      )
      .field(
          EXTENSIONS,
          SchemaBuilder.map(Schema.STRING_SCHEMA, Schema.STRING_SCHEMA).optional().doc("Key value pairs of any extensions to the message.").build()
      )
      .build();
}
