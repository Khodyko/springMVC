package org.example.spring.converter;

import junit.framework.TestCase;
import org.example.spring.model.Entity.EventEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class JsonReaderTest extends TestCase {
    @Spy
    private JsonReader jsonReader = new JsonReader();


    @Test
    public void readFileJsonTest() {
        Map<String, EventEntity> map = jsonReader.readFileJson("src/test/resources/test_event_data.json", EventEntity.class);
        assertNotNull(map.get("event:0"));
        assert (map.get("event:0").getId() == 0);
    }
}