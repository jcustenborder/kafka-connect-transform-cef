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

import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.jcustenborder.kafka.connect.utils.jackson.ObjectMapperFactory;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.data.Timestamp;
import org.apache.kafka.connect.source.SourceRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static com.github.jcustenborder.kafka.connect.utils.AssertConnectRecord.assertRecord;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class CEFTransformationTest {

  public static final String HOST = "host";
  public static final String FACILITY = "facility";
  public static final String DATE = "date";
  public static final String LEVEL = "level";
  public static final String MESSAGE = "message";
  public static final String CHARSET = "charset";
  public static final String REMOTE_ADDRESS = "remote_address";
  public static final String HOSTNAME = "hostname";
  private static Logger log = LoggerFactory.getLogger(CEFTransformationTest.class);
  static final Schema KEY_SCHEMA = SchemaBuilder.struct().name("com.github.jcustenborder.kafka.connect.syslog.SyslogKey")
      .doc("This schema represents the key that is written to Kafka for syslog data. This will ensure that all data for " +
          "a host ends up in the same partition.")
      .field(
          REMOTE_ADDRESS,
          SchemaBuilder.string().doc("The ip address of the host that sent the syslog message.").build()
      )
      .build();
  static final Schema VALUE_SCHEMA = SchemaBuilder.struct().name("com.github.jcustenborder.kafka.connect.syslog.SyslogValue")
      .doc("This schema represents a syslog message that is written to Kafka.")
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
          SchemaBuilder.int32().optional().doc("The level of the syslog message as defined by [rfc5424](https://tools.ietf.org/html/rfc5424)").build()
      )
      .field(
          MESSAGE,
          SchemaBuilder.string().optional().doc("The text for the message.").build()
      )
      .field(
          CHARSET,
          SchemaBuilder.string().optional().doc("The character set of the message.").build()
      )
      .field(
          REMOTE_ADDRESS,
          SchemaBuilder.string().optional().doc("The ip address of the host that sent the syslog message.").build()
      )
      .field(
          HOSTNAME,
          SchemaBuilder.string().optional().doc("The reverse DNS of the `" + REMOTE_ADDRESS + "` field.").build()
      )
      .build();

  @BeforeAll
  public static void indent() {
    ObjectMapperFactory.INSTANCE.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  CEFTransformation<SourceRecord> transformation;

  @BeforeEach
  public void before() {
    this.transformation = new CEFTransformation<>();
    this.transformation.configure(ImmutableMap.of());
  }

  @TestFactory
  public Stream<DynamicTest> apply() throws IOException {
    List<TestCase> testCases = TestDataUtils.loadJsonResourceFiles(this.getClass().getPackage().getName() + ".records", TestCase.class);

    return testCases.stream().map(testCase -> dynamicTest(testCase.testName(), () -> {
      ConnectRecord actual = this.transformation.apply(testCase.input);
      assertRecord(testCase.expected, actual);
    }));
  }

  @Disabled
  @Test
  public void generate() throws IOException {

    List<String> messages = Arrays.asList(
        "CEF:0|Security|threatmanager|1.0|100|worm successfully stopped|10|src=10.0.0.1 dst=2.1.2.2 spt=1232",
        "CEF:0|security|threatmanager|1.0|100|detected a \\| in message|10|src=10.0.0.1 act=blocked a | dst=1.1.1.1",
        "CEF:0|security|threatmanager|1.0|100|detected a \\ in packet|10|src=10.0.0.1 act=blocked a \\ dst=1.1.1.1",
        "CEF:0|security|threatmanager|1.0|100|detected a = in message|10|src=10.0.0.1 act=blocked a \\= dst=1.1.1.1",
        "CEF:0|ArcSight|Logger|5.0.0.5355.2|sensor:115|Logger Internal Event|1|cat=/Monitor/Sensor/Fan5 cs2=Current Value cnt=1 dvc=10.0.0.1 cs3=Ok cs1=null type=0 cs1Label=unit rt=1305034099211 cs3Label=Status cn1Label=value cs2Label=timeframe",
        "CEF:0|Trend Micro Inc.|OSSEC HIDS|v2.5.1|5302|User missed the password to change UID to root.|9|dvc=ubuntusvr cs2=ubuntusvr->/var/log/auth.log cs2Label=Location src= suser=root msg=May 11 21:16:05 ubuntusvr su[24120]: - /dev/pts/1 xavier:root",
        "CEF:0|security|threatmanager|1.0|100|Detected a threat. No action needed.|10|src=10.0.0.1 msg=Detected a threat.\\n No action needed.",
        "CEF:0|security|threatmanager|1.0|100|Detected a threat. No action needed.|10",
        "filterlog: 5,16777216,,1000000003,igb1,match,block,in,6,0x00,0x00000,255,ICMPv6,58,32,2605:6000:c00:96::1,ff02::1:ffac:f98,",
        "dhcpd: DHCPACK on 10.10.0.10 to 00:26:ab:fb:27:dc via igb2",
        "dhcpd: DHCPREQUEST for 10.10.0.10 from 00:26:ab:fb:27:dc via igb2",
        "dhcpleases: Sending HUP signal to dns daemon(69876)"
    );

    Multiset<String> counts = HashMultiset.create();

    for (String message : messages) {
      TestCase testCase = new TestCase();
      Struct valueInput = new Struct(VALUE_SCHEMA)
          .put("date", new Date(1493195158000L))
          .put("facility", 16)
          .put("host", "filterlog")
          .put("level", 6)
          .put("message", message)
          .put("charset", "utf-8")
          .put("remote_address", "/10.10.0.1:514")
          .put("hostname", "vpn.example.com");


      testCase.input = new SourceRecord(
          ImmutableMap.of(),
          ImmutableMap.of(),
          "syslog",
          null,
          null,
          null,
          valueInput.schema(),
          valueInput,
          1493195158000L
      );


      String fileNameFormat;

      try {

        testCase.expected = (SourceRecord) this.transformation.apply(testCase.input);

        fileNameFormat = testCase.expected.topic().equals("syslog.cef") ? "CEF%04d.json" : "NotCEF%04d.json";

        ((Struct) testCase.expected.value()).validate();
//        fileNameFormat = "CEF%04d.json";
      } catch (IllegalStateException ex) {
        fileNameFormat = "NotCEF%04d.json";
        testCase.expected = testCase.input;
      }
      counts.add(fileNameFormat);
      int testNumber = counts.count(fileNameFormat);

      File root = new File("src/test/resources/com/github/jcustenborder/kafka/connect/transform/cef/records");
      String filename = String.format(fileNameFormat, testNumber);

      File file = new File(root, filename);
      log.trace("Saving {}", filename);
      ObjectMapperFactory.INSTANCE.writeValue(file, testCase);

    }
  }

}
