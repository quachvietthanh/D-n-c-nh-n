package com.atm.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Generic <T>: 1 class xử lý được mọi kiểu Entity (Account, Transaction,...)
public class BaseRepositoryImpl<T> implements BaseRepository<T> {

    private final String filePath;  // Đường dẫn file: "data/accounts.dat"
    private final Class<T> type;    // Lưu Class để chống Type Erasure

    public BaseRepositoryImpl(String filePath, Class<T> type) {
        this.filePath = filePath;
        this.type = type;           
    }

    @Override
    public List<T> findAll() {
        File file = new File(filePath);
        if (!file.exists()) {               // Lần chạy đầu: file chưa có
            return new ArrayList<>();       //  trả list rỗng
        }
        // try-with-resources: tự động close() khi thoát try
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();  // Deserialize: bytes -> Object
            if (obj instanceof List<?>) {   // Kiểm tra có phải List không
                List<T> result = new ArrayList<>();
                for (Object item : (List<?>) obj) {
                    if (type.isInstance(item)) {        // Lọc đúng kiểu T
                        result.add(type.cast(item));    // Ép an toàn: Object -> T
                    }
                }
                return result;
            }
        } catch (EOFException e) {          // File rỗng -> không đọc được
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void saveAll(List<T> items) {
        ensureDataDirectoryExists();        // Tạo thư mục data/ nếu chưa có
        // try-with-resources + .flush(): an toàn, không rò rỉ tài nguyên
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(items);         // Serialize: List -> bytes -> file
            oos.flush();                    // Ép dữ liệu xuống ổ đĩa ngay
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T findById(int id) {
        List<T> items = findAll();
        for (T item : items) {
            try {
                // Reflection: vì Type Erasure, T thành Object -> không gọi getId() được
                java.lang.reflect.Field idField = item.getClass().getDeclaredField("id");
                idField.setAccessible(true);            // Vượt private
                if (idField.getInt(item) == id) {       // Đọc giá trị id
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
        List<T> items = findAll();      // Đọc danh sách cũ
        items.add(item);                
        saveAll(items);                 // Ghi đè file
    }

    private void ensureDataDirectoryExists() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();         // Tạo data/ nếu chưa tồn tại
        }
    }
}
