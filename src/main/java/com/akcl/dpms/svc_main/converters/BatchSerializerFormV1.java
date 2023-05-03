package com.akcl.dpms.svc_main.converters;

import com.akcl.dpms.svc_main.entity.Batch;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Base64;

public class BatchSerializerFormV1 extends StdSerializer<Batch> {
    public BatchSerializerFormV1() {
        this(null);
    }

    public BatchSerializerFormV1(Class<Batch> t) {
        super(t);
    }

    @Override
    public void serialize(Batch batch, JsonGenerator gen, SerializerProvider sp) throws IOException {
        ObjectNode batchNode = new ObjectMapper().convertValue(batch, ObjectNode.class);

        ArrayNode processListNode = (ArrayNode) batchNode.remove("batchProcesses");
        if (processListNode != null) {
            for (JsonNode processNodeImmutable : processListNode.removeAll()) {
                JsonNode batchProcessIdNode = processNodeImmutable.get("batchProcessId");
                Long batchProcessId = batchProcessIdNode == null ? null : batchProcessIdNode.asLong();
                String processId = processNodeImmutable.get("processId").asText();
                String processParams = new String(Base64.getDecoder()
                        .decode(processNodeImmutable.get("parameters").asText()));

                ObjectNode processNode = (ObjectNode) new ObjectMapper().readValue(processParams, JsonNode.class);
                processNode.put("batchProcessId", batchProcessId);
                processNode.put("processId", processId);

                processListNode.add(processNode);
            }
        }
        batchNode.set("batchProcesses", processListNode);

        gen.writeObject(batchNode);
    }
}
