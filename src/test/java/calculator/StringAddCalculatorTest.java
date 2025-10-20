package calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringAddCalculatorTest {

    private StringAddCalculator newCalc() {
        DelimiterResolver r = new DelimiterResolver(new CustomHeaderPolicy(), new DefaultPolicy());
        return new StringAddCalculator(r);
    }

    @Test
    @DisplayName("빈 문자열은 0을 반환한다")
    void emptyReturnsZero() {
        assertEquals(0, newCalc().add(""));
    }

    @Test
    @DisplayName("기본 구분자(, :)로 합산한다")
    void defaultDelimiters() {
        assertEquals(6, newCalc().add("1,2:3"));
    }

    @Test
    @DisplayName("커스텀 구분자 //{c}\\n 을 인식한다")
    void customDelimiter() {
        assertEquals(6, newCalc().add("//;\n1;2;3"));
    }

    @Test
    @DisplayName("기본 + 커스텀 구분자를 동시에 허용한다")
    void mixedDelimiters() {
        assertEquals(6, newCalc().add("//;\n1;2:3"));
    }

    @Test
    @DisplayName("리터럴 \\n 헤더도 인식한다")
    void literalNewlineHeader() {
        assertEquals(6, newCalc().add("//|\n1|2|03".replace("\n", "\\n")));
    }

    @Test
    @DisplayName("음수는 예외를 던진다")
    void negativeThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> newCalc().add("-1,2"));
        assertEquals("잘못된 입력입니다.", ex.getMessage());
    }

    @Test
    @DisplayName("비숫자 토큰은 예외를 던진다")
    void nonDigitThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> newCalc().add("1,a"));
        assertEquals("잘못된 입력입니다.", ex.getMessage());
    }

    @Test
    @DisplayName("빈 토큰은 예외를 던진다")
    void emptyTokenThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> newCalc().add("1,,2"));
        assertEquals("잘못된 입력입니다.", ex.getMessage());
    }

    @Test
    @DisplayName("null 입력은 예외를 던진다")
    void nullThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> newCalc().add(null));
        assertEquals("잘못된 입력입니다.", ex.getMessage());
    }
}
