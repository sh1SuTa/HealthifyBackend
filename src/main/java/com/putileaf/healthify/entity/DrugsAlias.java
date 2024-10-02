package com.putileaf.healthify.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DrugsAlias {
    private Integer id;//主键ID

    @NotEmpty
    private String name;//药物名称

    private String drugsAlias;

    private Integer drugsId;
}
