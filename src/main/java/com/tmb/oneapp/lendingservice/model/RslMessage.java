package com.tmb.oneapp.lendingservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


/**
 * RslMessage Entity class
 */


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "application_loan_status_tracking")
public class RslMessage {
    @Id
    @Field("_id")
    private String id;

    @Field("group_msgth")
    private String groupMsgTh;

    @Field("group_msgen")
    private String groupMsgEn;

    @Field("line_descth")
    private String lineDescTh;

    @Field("line_descen")
    private String lineDescEn;
}