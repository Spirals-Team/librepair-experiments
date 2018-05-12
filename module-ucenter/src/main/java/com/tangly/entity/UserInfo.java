package com.tangly.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author tangly
 */
@Table(name = "user_info")
@Data
@ApiModel(description = "用户信息实体")
public class UserInfo implements Serializable{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long userId;
    @Column(name = "nickname")
    private String nickname;

}