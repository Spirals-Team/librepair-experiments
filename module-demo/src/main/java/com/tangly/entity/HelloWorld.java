package com.tangly.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author tangly
 */
@Entity
@Data
@ApiModel(description = "示例实体")
public class HelloWorld {

    @Id
    @ApiModelProperty(value = "主键",required = true)
    /**
     *  配置自增 @GeneratedValue，
     *  实体insert后可以立即用getId方法获得
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "名称")
    /**
     * @NotNull @Pattern @Min @Max 是javax.validation.contrains校验框架中的注解；
     *
     */
    @NotNull(message = "名字不能为空")
    @Size(min = 2, max = 14 , message = "名字长度限制为2-14")
    private String name;

    @ApiModelProperty(value = "手机号")
    @Pattern(regexp="\\d{11}" , message = "手机号格式不正确")
    private String phoneNum;

    public HelloWorld() {
    }

    public HelloWorld(String name) {
        this.name = name;
    }


    public HelloWorld(String name, String phoneNum) {
        this.name = name;
        this.phoneNum = phoneNum;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}