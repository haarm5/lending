package com.tmb.oneapp.lendingservice.repository;

import com.tmb.oneapp.lendingservice.model.RslMessage;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * getting RSL Message based on Loan Type
 *
 */

@Repository
public interface RslMessageRepository extends MongoRepository<RslMessage, String> {
    @Aggregation(pipeline = {"{$match:{'Desc_Detail.?0.status_code':\"?1\",'Desc_Detail.?0.loantype':\"?2\"}}",
            "{$group:{_id:\"$_id\",\"group_msgth\":{\"$first\":\"$Desc_Detail.?0.group_msgth\"},\"group_msgen\":{\"$first\":\"$Desc_Detail.?0.group_msgen\"},\"line_descth\":{\"$first\":\"$Desc_Detail.?0.line_descth\"},\"line_descen\":{\"$first\":\"$Desc_Detail.?0.line_descen\"}}}"})
    RslMessage getMsg(String appStatusWithLoanTypeNumber, String appStatus, String loanType);
}