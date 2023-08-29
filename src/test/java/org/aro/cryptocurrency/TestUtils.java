package org.aro.cryptocurrency;

import org.aro.cryptocurrency.model.CryptoCurrencyRequest;
import org.aro.cryptocurrency.model.TimerInfo;

public final class TestUtils {
    private TestUtils() {
    }

    public static TimerInfo createTimerInfo(int totalFireCount, boolean runForever, long repeatIntervalMs, long initialOffsetMs) {
        final TimerInfo timerInfo = new TimerInfo();
        timerInfo.setTotalFireCount(totalFireCount);
        timerInfo.setRunForever(runForever);
        timerInfo.setInitialOffsetMs(initialOffsetMs);
        timerInfo.setRepeatIntervalMs(repeatIntervalMs);
        return timerInfo;
    }

    public static CryptoCurrencyRequest createParameters(final int start, final int limit, final String convert, final String sort) {
        final CryptoCurrencyRequest parameters = new CryptoCurrencyRequest();
        parameters.setStart(start);
        parameters.setLimit(limit);
        parameters.setConvert(convert);
        parameters.setSort(sort);
        return parameters;
    }
}
