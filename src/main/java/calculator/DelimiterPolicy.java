package calculator;

// 구분자 정책 인터페이스
public interface DelimiterPolicy {
    boolean supports(String input); // 처리 가능 여부
    Parsed parse(String input); // 파싱 결과 반환
}
