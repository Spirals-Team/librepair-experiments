package com.psg.glms.model;

import javax.persistence.*;

@Entity
public class BookRequest{
    // bookreqid,bookid, userid, status (Q/I/R)
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookreqid;
    @Column
    private Long userid;
    @Column
    private Long catId;
    @Column
    private Long bookId;
	@Column
    private String status;

    /**
     * @return the bookreqid
     */
    public Long getBookreqid() {
        return bookreqid;
    }
    /**
     * @param bookreqid the bookreqid to set
     */
    public void setBookreqid(Long bookreqid) {
        this.bookreqid = bookreqid;
    }
    /**
     * @return the catId
     */
    public Long getCatId() {
        return catId;
    }
    /**
     * @param catId the catId to set
     */
    public void setCatId(Long catId) {
        this.catId = catId;
    }
    /**
     * @return the bookId
     */
    public Long getBookId() {
        return bookId;
    }
    /**
     * @param bookId the bookId to set
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}