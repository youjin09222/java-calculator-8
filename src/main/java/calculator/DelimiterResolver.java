package calculator;

import java.util.List;

// 어떤 '구분자 정책'을 쓸지 결정
public class DelimiterResolver {
    private final List<DelimiterPolicy> policies;  // 정책 목록

    public DelimiterResolver(DelimiterPolicy... policies) {
        this.policies = List.of(policies);
    }

    public Parsed resolve(String input) {
        for (DelimiterPolicy p : policies) {
            if (p.supports(input)) {
                return p.parse(input); // 가능한 첫 정책으로 처리
            }
        }

        throw new IllegalStateException("구분자 해석 정책이 없습니다.");
    }
}
