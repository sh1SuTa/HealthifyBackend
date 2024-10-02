package com.putileaf.healthify.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
@TableName("drugs_alias")
@Data
public class DrugsAlias {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;//主键ID

    @NotEmpty
    private String name;//药物名称

    private String drugsAlias;

    private Integer drugsId;
}
