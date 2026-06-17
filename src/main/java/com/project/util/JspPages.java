package com.project.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspPages {
    private final String MAIN_PATH = "WEB-INF/view";
    public final String NEW_MATCH = MAIN_PATH + "/new-match.jsp";
    public final String MATCH_SCORE = MAIN_PATH + "/match-score.jsp";
    public final String MATCHES = MAIN_PATH + "/matches.jsp";
    public final String FINISHED_MATCH = MAIN_PATH + "/finished-match.jsp";
    public final String ERROR = MAIN_PATH + "/error.jsp";
}
