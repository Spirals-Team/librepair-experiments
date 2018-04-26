package in.ac.bits.protocolanalyzer.persistence.entity;

import java.lang.String;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(
    indexName = "protocol",
    type = "tcp"
)
public class TcpEntity {
  @Id
  private long packetId;

  private int window;

  private long seqNo;

  private byte res;

  private int srcPort;

  private int urgentPtr;

  private String checksum;

  private long ackNo;

  private int dstPort;

  private short flags;

  private byte dataOffset;
}
