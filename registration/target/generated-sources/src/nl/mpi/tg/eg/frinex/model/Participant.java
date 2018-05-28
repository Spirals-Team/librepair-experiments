package nl.mpi.tg.eg.frinex.model;

                import java.io.Serializable;
                import java.util.Date;
                import javax.persistence.Entity;
                import javax.persistence.GeneratedValue;
                import javax.persistence.GenerationType;
                import javax.persistence.Id;
                import javax.persistence.Temporal;

                @Entity                     
                public class Participant implements Serializable, Comparable<Participant> {

                @Id
                @GeneratedValue(strategy = GenerationType.AUTO)
                private long id;

                @Temporal(javax.persistence.TemporalType.TIMESTAMP)
                private Date submitDate;
                private String userId;
                private String remoteAddr;
                private String acceptLang;
                private String userAgent;
                private Boolean staleCopy = false;
            
                    private String workerId;
                    private String datOfBirth;
                    private String gender;
                    private String groupAllocation_Room_1;
                    private String groupAllocation_Room_2;
                    private String groupAllocation_Room_3;
                    private String groupAllocation_Room_4;
                
                @Override
                public int compareTo(Participant o) {
                return (this.userId != null) ? this.userId.compareTo(o.getUserId()) : 1;
                }
                
                public long getId() {
                return id;
                }

                public boolean getStaleCopy() {
                return (staleCopy == null) ? false : staleCopy;
                }
                
                public String getUserId() {
                return userId;
                }
                
                public Date getSubmitDate() {
                return submitDate;
                }

                public void setSubmitDate(Date submitDate) {
                this.submitDate = submitDate;
                }

                public String getRemoteAddr() {
                return remoteAddr;
                }
                
                public void setRemoteAddr(String remoteAddr) {
                this.remoteAddr = remoteAddr;
                }

                public String getAcceptLang() {
                return acceptLang;
                }

                public void setAcceptLang(String acceptLang) {
                this.acceptLang = acceptLang;
                }

                public String getUserAgent() {
                return userAgent;
                }

                public void setUserAgent(String userAgent) {
                this.userAgent = userAgent;
                }
            
                    public String getWorkerId() {
                    return workerId;
                    }
                    public void setWorkerId(String workerId) {
                    this.workerId = workerId;
                    }
                
                    public String getDatOfBirth() {
                    return datOfBirth;
                    }
                    public void setDatOfBirth(String datOfBirth) {
                    this.datOfBirth = datOfBirth;
                    }
                
                    public String getGender() {
                    return gender;
                    }
                    public void setGender(String gender) {
                    this.gender = gender;
                    }
                
                    public String getGroupAllocation_Room_1() {
                    return groupAllocation_Room_1;
                    }
                    public void setGroupAllocation_Room_1(String groupAllocation_Room_1) {
                    this.groupAllocation_Room_1 = groupAllocation_Room_1;
                    }
                
                    public String getGroupAllocation_Room_2() {
                    return groupAllocation_Room_2;
                    }
                    public void setGroupAllocation_Room_2(String groupAllocation_Room_2) {
                    this.groupAllocation_Room_2 = groupAllocation_Room_2;
                    }
                
                    public String getGroupAllocation_Room_3() {
                    return groupAllocation_Room_3;
                    }
                    public void setGroupAllocation_Room_3(String groupAllocation_Room_3) {
                    this.groupAllocation_Room_3 = groupAllocation_Room_3;
                    }
                
                    public String getGroupAllocation_Room_4() {
                    return groupAllocation_Room_4;
                    }
                    public void setGroupAllocation_Room_4(String groupAllocation_Room_4) {
                    this.groupAllocation_Room_4 = groupAllocation_Room_4;
                    }
                              
                }    