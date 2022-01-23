package com.aro.cryptocurrency;

import com.aro.cryptocurrency.model.CryptoCurrencyRequest;
import com.aro.cryptocurrency.model.TimerInfo;
import com.aro.cryptocurrency.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.Test;

import static com.aro.cryptocurrency.TestUtils.createParameters;
import static com.aro.cryptocurrency.TestUtils.createTimerInfo;
import static org.junit.jupiter.api.Assertions.*;

public class JsonUtilsTests {


    @Test
    public void toJson_success() throws JsonProcessingException {
        assertEquals("{\"totalFireCount\":1,\"runForever\":true,\"repeatIntervalMs\":5,\"initialOffsetMs\":9}", JsonUtils.toJson(createTimerInfo(1,true,5,9)));
        assertEquals("{\"start\":1,\"limit\":8,\"convert\":\"ABC\",\"sort\":\"market_cap\"}", JsonUtils.toJson(createParameters(1,8,"ABC","market_cap")));
        assertNotEquals("{\"start\":5,\"limit\":8,\"convert\":\"ABC\",\"sort\":\"market_cap\"}", JsonUtils.toJson(createParameters(1,8,"ABC","market_cap")));
    }

    @Test
    public void toJson_null()  throws JsonProcessingException {
        assertEquals("null", JsonUtils.toJson(null));
    }

    @Test
    public void toObject_success() throws JsonProcessingException {
        TimerInfo timerInfo = JsonUtils.toObject("{\"totalFireCount\":1,\"runForever\":true,\"repeatIntervalMs\":5,\"initialOffsetMs\":9}", TimerInfo.class);
        assertEquals(1, timerInfo.getTotalFireCount());
        assertTrue(timerInfo.isRunForever());
        assertEquals(5, timerInfo.getRepeatIntervalMs());
        assertNotEquals(99, timerInfo.getInitialOffsetMs());
    }

    @Test
    public void toObject_null() {
        Exception exception = assertThrows(JsonMappingException.class, () ->
                JsonUtils.toObject("{\"start\":1,\"limit\":\"8\",\"convert\":\"ABC\",\"sort\":\"market_cap}", CryptoCurrencyRequest.class));
        assertTrue(exception.getMessage().startsWith("Unexpected end-of-input: was expecting closing quote for a string value"));
    }
}
