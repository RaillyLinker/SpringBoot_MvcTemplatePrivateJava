package com.raillylinker.springboot_mvc_template_private_java.data_sources.mongo_db_beans.mdb1_main.documents;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "test")
public class Mdb1Test {
    @Id
    private String uid;

    @CreatedDate
    @Field("row_create_date")
    private LocalDateTime rowCreateDate;

    @LastModifiedDate
    @Field("row_update_date")
    private LocalDateTime rowUpdateDate;

    public Mdb1Test(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer randomNum,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String nullableValue,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean rowActivate
    ) {
        this.content = content;
        this.randomNum = randomNum;
        this.nullableValue = nullableValue;
        this.rowActivate = rowActivate;
    }

    @Field("content")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private String content;

    @Field("random_num")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private Integer randomNum;

    @Field("nullable_value")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private String nullableValue;

    @Field("row_activate")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private Boolean rowActivate;
}
