package net.posesor.allocations;

import lombok.*;

/**
 * Contains grouped info about all available financial allocations.
 */
@NoArgsConstructor
@Data
public final class AvailableAllocationsDto {

    private SubjectDto[] elements;

    public AvailableAllocationsDto(SubjectDto... elements) {
        this.elements = elements;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class SubjectDto {
        private String subjectName;
        private String paymentTitle;
        private PeriodDto periodFrom;
        private PeriodDto periodTo;
    }

    /**
     * Transfers year and month.
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static final class PeriodDto {
        @Getter
        private Integer year;
        @Getter
        private Integer month;
    }

}
