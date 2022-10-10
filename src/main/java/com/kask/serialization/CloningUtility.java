package com.kask.serialization;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class CloningUtility {

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();

        } catch (IOException | ClassNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw new IllegalStateException(ex);
        }
    }
}
