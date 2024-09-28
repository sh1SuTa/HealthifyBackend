package com.putileaf.healthify.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间

}
