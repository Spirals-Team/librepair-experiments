package com.revature.project2.userreports;

import com.revature.project2.users.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "USER_REPORT")
public class UserReport {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @OneToOne
  private User reportingUser;

  @OneToOne
  private User reportedUser;

  private String comment;

  private Date submitTimeStamp;

  private Date resolveTimeStamp;

}
