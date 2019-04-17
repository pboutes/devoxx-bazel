package com.gateway.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Model1;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class UtilTests {

    @Test
    public void shouldSerializeData() {

        Model1 model1 = new Model1("some data", "some url");

        String s = Util.tryOr(new ObjectMapper(), model1, () -> "{}");

        Assert.assertEquals("{\"data\":\"some data\",\"from\":\"some url\"}", s);

    }

    @Test
    public void shouldReturnEmptyObject() throws JsonProcessingException {

        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Model1 model1 = new Model1("some data", "some url");

        Mockito.doThrow(new JsonProcessingException("error"){}).when(mapper).writeValueAsString(model1);

        String s = Util.tryOr(mapper, model1, () -> "{}");

        Assert.assertEquals("{}", s);

    }

}
