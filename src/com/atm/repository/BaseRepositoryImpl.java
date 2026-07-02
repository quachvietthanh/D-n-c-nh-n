package com.atm.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BaseRepositoryImpl<T> implements BaseRepository<T> {

    private final String filePath;
    private final Class<T> type;

    public BaseRepositoryImpl(String filePath, Class<T> type) {
        this.filePath = filePath;
        this.type = type;
    }

    @Override
    public List<T> findAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                List<T> result = new ArrayList<>();
                for (Object item : (List<?>) obj) {
                    if (type.isInstance(item)) {
                        result.add(type.cast(item));
                    }
                }
                return result;
            }
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void saveAll(List<T> items) {
        ensureDataDirectoryExists();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(items);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T findById(int id) {
        List<T> items = findAll();
        for (T item : items) {
            try {
                java.lang.reflect.Field idField = item.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                if (idField.getInt(item) == id) {
                    return item;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void add(T item) {
        List<T> items = findAll();
        items.add(item);
        saveAll(items);
    }

    private void ensureDataDirectoryExists() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }
}
