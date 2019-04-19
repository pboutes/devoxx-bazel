package com.gateway.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Info;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class UtilTests {

    @Test
    public void shouldSerializeData() {

        Info info = Info.of("some data", "some url");

        String s = Util.tryOr(new ObjectMapper(), info);

        Assert.assertEquals("{\"data\":\"some data\",\"from\":\"some url\"}", s);

    }

    @Test
    public void shouldReturnEmptyObject() throws JsonProcessingException {

        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Info info = Info.of("some data", "some url");

        Mockito.doThrow(new JsonProcessingException("error"){}).when(mapper).writeValueAsString(info);

        String s = Util.tryOr(mapper, info);

        Assert.assertEquals("{}", s);

    }

}
