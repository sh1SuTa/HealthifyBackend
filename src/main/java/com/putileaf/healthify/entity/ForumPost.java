package com.putileaf.healthify.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ForumPost {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;//主键ID

    @NotEmpty
    private String title;
    private String category;

    private String summary;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;//自动创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;//自动更新时间

    private Integer createUser;
}
