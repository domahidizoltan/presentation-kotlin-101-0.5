package hu.telekom.bankdemo.java.transport;

public enum DtoFields {
    ID(2),
    OPENING_HOURS_001(4),
    OPENING_HOURS_002(5),
    OPENING_HOURS_003(6),
    OPENING_HOURS_004(7),
    OPENING_HOURS_005(8),
    OPENING_HOURS_006(9),
    OPENING_HOURS_007(10),
    SPECIAL_SERVICES_001(11),
    SPECIAL_SERVICES_002(12),
    SPECIAL_SERVICES_003(13),
    SPECIAL_SERVICES_004(14),
    ADDRESS(15),
    TYPE(16),
    ZIP_CODE(49),
    TOWN(50),
    LAT(51),
    LONG(52);

    public final int idx;

    DtoFields(int idx) {
        this.idx = idx;
    }

}
