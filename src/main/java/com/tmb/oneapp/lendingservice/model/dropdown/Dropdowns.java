package com.tmb.oneapp.lendingservice.model.dropdown;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Dropdowns {

    private Dropdowns() {

    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class EmploymentStatus {
        private String code;
        private String name;
        private String name2;
        private String refEntryCode;
    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class RmOccupation {
        private String code;
        private String name;
        private String name2;
        private String refEntryCode;
        private String groupId;
        private String extValue1;
        private String extValue2;
        private List<Occupation> occupation;
    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Occupation {
        private String code;
        private String name;
        private String name2;
    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class BusinessType {
        private String code;
        private String name;
        private String name2;
        private List<BusinessSubType> businessSubType;
    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class BusinessSubType {
        private String code;
        private String name;
        private String name2;
    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class IncomeBank {
        private String code;
        private String name;
        private String name2;
    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class IncomeType {
        private String code;
        private String name;
        private String name2;
        private String extValue2;
    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class SciCountry {
        private String code;
        private String name;
        private String name2;
    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class MaritalStatus {
        private String code;
        private String name;
        private String name2;
    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class ResidentType {
        private String code;
        private String name;
        private String name2;
    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class EducationLevel {
        private String code;
        private String name;
        private String name2;
    }

}
