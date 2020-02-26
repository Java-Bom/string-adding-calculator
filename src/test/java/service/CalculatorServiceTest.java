package service;

import domain.Calculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CalculatorServiceTest {

    CalculatorService calculatorService = new CalculatorService(new Calculator());

    @DisplayName("올바른 수식 계산 테스트")
    @MethodSource("validFormula")
    @ParameterizedTest
    void invalidFormula(String formula, double expectedValue){
        assertThat(calculatorService.calculate(formula)).isEqualTo(expectedValue);
    }

    static Stream<Arguments> validFormula() {
        return Stream.of(
                Arguments.of("//o₩n1o2o3", 6d),
                Arguments.of("//abc₩n1abc2abc3abc", 6d),
                Arguments.of("//@₩n3@1@7@1", 12d),
                Arguments.of("1,2:3", 6d)
        );
    }


    @DisplayName("올바르지 않은 수식 테스트")
    @MethodSource("invalidFormula")
    @ParameterizedTest
    void inValidFormula(String formula){
        assertThatThrownBy(() -> calculatorService.calculate(formula))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidFormula() {
        return Stream.of(
                Arguments.of("//₩n1o2o3"),
                Arguments.of("//abc"),
                Arguments.of("1abc2abc3abc"),
                Arguments.of("//o₩n1*2")
        );
    }


}
