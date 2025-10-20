package calculator;

import java.util.regex.Pattern;

public class CustomHeaderPolicy implements DelimiterPolicy {
    private static final String DEFAULT_DELIMITER_REGEX = "[,:]";
    private static final String ERROR_MSG = "잘못된 입력입니다.";

    @Override
    public boolean supports(String input) {
        // 맨 앞이 '//' 로 시작하면 커스텀 정책 후보
        return input.startsWith("//");
    }

    @Override
    public Parsed parse(String input) {
        // 헤더의 끝 찾기
        int idxActualNewline = input.indexOf('\n', 2);
        int idxLiteralBackslashN = input.indexOf("\\n", 2);

        int sepIdx; // 헤더와 본문을 가르는 위치
        int sepLen; // 구분자 길이
        if (idxActualNewline >= 0 && (idxLiteralBackslashN < 0 || idxActualNewline < idxLiteralBackslashN)) {
            sepIdx = idxActualNewline;
            sepLen = 1;
        } else if (idxLiteralBackslashN >= 0) {
            sepIdx = idxLiteralBackslashN;
            sepLen = 2;
        } else {
            throw new IllegalArgumentException(ERROR_MSG);
        }

        // 커스텀 구분자 추출
        String custom = input.substring(2, sepIdx);
        if (custom.length() != 1) {
            throw new IllegalArgumentException(ERROR_MSG);
        }

        // 숫자 본문과 최종 구분자 정규식 구성
        String numbersPart = input.substring(sepIdx + sepLen);
        String anyDelimiter = orRegex(DEFAULT_DELIMITER_REGEX, Pattern.quote(custom));
        return new Parsed(numbersPart, anyDelimiter);
    }

    private String orRegex(String leftRegex, String rightRegex) {
        return "(?:" + leftRegex + ")|(?:" + rightRegex + ")";
    }
}
