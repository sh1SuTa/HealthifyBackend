package com.putileaf.healthify.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import java.time.LocalDateTime;

@Data
public class Dose {

    private Integer id;//主键ID

    @NotEmpty
    private String name;//药物名称

    @NotEmpty(message = "描述不能为空")
    private String description;//药物描述

    @NotEmpty @URL
    private String coverImg;//封面图像


    private Integer createUser;//创建人ID，不使用外键为后续的分布式升级做准备

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;//自动创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;//自动更新时间

}
