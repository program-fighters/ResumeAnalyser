package org.example.Constant;

public interface DbConstant {
    interface Collection {
        String CandidatesInfo = "candidates_info";
    }

    interface AuditTrails {
        String INS_TS = "insTs";
        String INS_BY = "insBy";
        String UPD_TS = "updTs";
        String UPD_BY = "updBy";

    }

    interface CandidatesInfo {
        String FILE_NAME="fileName";
        String NAME = "name";
        String EMAIL = "mailId";
        String SKILLS = "skills";
        String EXPERIENCE = "experience";
        String SUMMARY = "summary";
        String RESUME_TEXT = "resumeText";
        String UPDATE_COUNT="updateCount";
    }
}

