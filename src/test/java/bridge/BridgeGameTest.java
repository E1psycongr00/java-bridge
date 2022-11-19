package bridge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import bridge.domain.BridgeStatus;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BridgeGameTest {

    private final BridgeStatusSaver bridgeStatusSaver = new BridgeStatusSaver();
    private final BridgeMaker bridgeMaker = mock(BridgeMaker.class);
    private final BridgeGame bridgeGame = new BridgeGame(bridgeMaker, bridgeStatusSaver);

    @DisplayName("다리 생성시 영속화 테스트")
    @Test
    void persistBridgeStatusWhenCreateBridge() {
        // given
        when(bridgeMaker.makeBridge(anyInt())).thenReturn(List.of("U", "D", "U"));

        // when
        bridgeGame.createBridge(3);

        // then
        assertThat(bridgeStatusSaver.getBridgeStatus()).isNotNull();
    }

    @DisplayName("움직임 명령 요청시 영속화 테스트")
    @Test
    void persistBridgeStatusWhenRequestMovingCommand() {
        // given
        when(bridgeMaker.makeBridge(anyInt())).thenReturn(List.of("U", "D", "U"));
        bridgeGame.createBridge(3);
        BridgeStatus original = bridgeStatusSaver.getBridgeStatus();

        // when
        bridgeGame.move("U");

        // then
        assertThat(bridgeStatusSaver.getBridgeStatus()).isNotNull();
    }

    @DisplayName("retry 요청시 R인 경우 true, Q인 경우 false 반환")
    @ParameterizedTest
    @CsvSource({
            "R, true", "Q, false"
    })
    void returnTrueWhenRequestRorReturnFalseWhenRequestQ(String input, boolean result) {
        // given
        when(bridgeMaker.makeBridge(anyInt())).thenReturn(List.of("U", "D", "U"));
        bridgeGame.createBridge(3);

        // when
        boolean retry = bridgeGame.retry(input);

        // then
        assertThat(retry).isEqualTo(result);
    }

}