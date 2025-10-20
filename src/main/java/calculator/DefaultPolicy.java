package calculator;

// 기본 구분자(, :)만 사용하는 정책
public class DefaultPolicy implements DelimiterPolicy {
    private static final String DEFAULT_DELIMITER_REGEX = "[,:]";

    @Override
    public boolean supports(String input) {
        return true; // 항상 마지막에 적용
    }

    @Override
    public Parsed parse(String input) {
        return new Parsed(input, DEFAULT_DELIMITER_REGEX);
    }
}