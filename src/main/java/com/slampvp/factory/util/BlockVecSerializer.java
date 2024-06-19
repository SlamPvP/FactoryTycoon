package com.slampvp.factory.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.minestom.server.coordinate.BlockVec;

import java.io.IOException;

public class BlockVecSerializer extends StdSerializer<BlockVec> {
    public BlockVecSerializer() {
        this(null);
    }

    public BlockVecSerializer(Class<BlockVec> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(BlockVec blockVec, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("x", blockVec.blockX());
        jsonGenerator.writeNumberField("y", blockVec.blockY());
        jsonGenerator.writeNumberField("z", blockVec.blockZ());
        jsonGenerator.writeEndObject();
    }
}
