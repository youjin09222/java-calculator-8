package calculator;

public class StringAddCalculator {
    private static final String ERROR_MSG = "잘못된 입력입니다.";
    private final DelimiterResolver resolver;

    public StringAddCalculator(DelimiterResolver resolver) {
        this.resolver = resolver;
    }

    public int add(String input) {
        // 입력값이 없거나 비어있는 경우
        if (input == null) {
            throw new IllegalArgumentException(ERROR_MSG);
        }
        if (input.isEmpty()) {
            return 0;
        }

        // 어떤 정책으로 자를지 결정
        Parsed parsed = resolver.resolve(input);

        // 구분자 정규식 기준으로 토큰 분리
        String[] tokens = parsed.numbersPart().split(parsed.anyDelimiterRegex());

        // 각 토큰 검증 및 합산
        int sum = 0;
        for (String token : tokens) {
            validateToken(token);
            sum += Integer.parseInt(token);
        }
        return sum;
    }

    // 토큰이 유효한지 검사
    private void validateToken(String token) {
        if (token.isEmpty() || token.startsWith("-") || !token.matches("\\d+")) {
            throw new IllegalArgumentException(ERROR_MSG);
        }
    }
}
