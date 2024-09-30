package com.putileaf.healthify.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DoseAlias {
    private Integer id;//主键ID

    @NotEmpty
    private String name;//药物名称

    private String doseAlias;

    private Integer doseId;
}
