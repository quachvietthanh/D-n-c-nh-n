# 🏦 Advanced ATM Simulator

> **Java Core Console Application** — Mô phỏng hệ thống quản lý tài khoản và giao dịch tại máy ATM.

---

## 📋 Giới thiệu

Dự án **Advanced ATM Simulator** là ứng dụng Java Core thuần chạy trên môi trường Console, mô phỏng các chức năng của một máy ATM thực tế. Được xây dựng theo kiến trúc **phân tầng (3-Tier Architecture)** với các nguyên lý **OOP**, **SOLID** và **Design Pattern**.

---

## 🛠️ Công nghệ & Kỹ thuật

| Công nghệ | Mô tả |
|-----------|-------|
| ☕ **Java Core (Java SE)** | Ngôn ngữ lập trình chính |
| 🏗️ **3-Tier Architecture** | Entity — Repository — Service — Controller — View |
| 🧬 **Generic & Collection** | `BaseRepository<T>`, `List<T>`, `ArrayList` |
| ❗ **Custom Exception** | `InsufficientBalanceException`, `AccountLockedException`, `InvalidPinException` |
| 🏭 **Factory Pattern** | `UserFactory` — khởi tạo đối tượng User theo role |
| 💾 **File IO (Serialization)** | Lưu trữ dữ liệu nhị phân bằng `ObjectInputStream` / `ObjectOutputStream` |
| 🧪 **JUnit 5 (TDD)** | Viết test trước — code xử lý sau |
| 🔒 **Encapsulation** | Thuộc tính `private`, Getter/Setter đầy đủ |

---

## 📁 Cấu trúc dự án

```
src/
└── com/atm/
    ├── Main.java                          # 🚀 Điểm khởi chạy
    ├── app/                               # (dự phòng)
    ├── controller/
    │   └── ATMController.java             # Điều phối View → Service
    ├── entity/
    │   ├── User.java                      # Abstract class (Serializable)
    │   ├── Admin.java                     # Kế thừa User
    │   ├── Account.java                   # Kế thừa User
    │   ├── Transaction.java               # Lịch sử giao dịch
    │   └── UserFactory.java               # Factory Pattern
    ├── exception/
    │   ├── InsufficientBalanceException   # Số dư không đủ
    │   ├── AccountLockedException         # Tài khoản bị khóa
    │   └── InvalidPinException            # Sai mã PIN
    ├── repository/
    │   ├── BaseRepository.java            # Generic interface
    │   ├── BaseRepositoryImpl.java        # File IO (ObjectStream)
    │   ├── AccountRepository.java         # data/accounts.dat
    │   └── TransactionRepository.java     # data/transactions.dat
    ├── service/
    │   ├── AccountService.java            # Business Logic
    │   └── AccountServiceTest.java        # JUnit 5 Tests
    ├── util/                              # (dự phòng)
    └── view/
        └── ATMView.java                   # CLI Menu
```

---

## 🔄 Luồng hoạt động

```
                ┌─────────────────────────────┐
                │    CHÀO MỪNG BANK ATM       │
                │  1. Đăng nhập               │
                │  2. Đăng ký tài khoản       │
                │  3. Thoát                   │
                └──────────┬──────────────────┘
                           │
           ┌───────────────┴───────────────┐
           ▼                               ▼
    ┌──────────────┐            ┌──────────────────┐
    │   ĐĂNG NHẬP  │            │  ĐĂNG KÝ MỚI     │
    │ account + PIN │            │ Nhập thông tin   │
    └──────┬───────┘            │ → accountRepository.add()
           ▼                    └──────────────────┘
    ┌──────────────────────────────────┐
    │         MENU CHỨC NĂNG          │
    │ 1. Xem số dư                     │
    │ 2. Rút tiền   → withdraw()      │
    │ 3. Nạp tiền   → deposit()       │
    │ 4. Chuyển tiền → transfer()     │
    │ 5. Đổi mã PIN → changePin()     │
    │ 6. Đăng xuất                     │
    └──────────────────────────────────┘
```

---

## 🧪 Tài khoản mẫu

| Số tài khoản | Mã PIN | Số dư | Username |
|:------------:|:------:|:-----:|:--------:|
| `1234567890` | `123456` | 5,000,000₫ | user1 |
| `0987654321` | `654321` | 3,000,000₫ | user2 |
| `1111111111` | `111111` | 10,000,000₫ | admin |

> Tài khoản mẫu được tự động tạo khi chạy lần đầu nếu chưa có dữ liệu.

---

## 📅 Tiến độ dự án

| Tuần | Nội dung | Trạng thái |
|:----:|----------|:----------:|
| 1 | Khởi tạo kiến trúc & Entities (Factory Pattern) | ✅ Hoàn thành |
| 2 | Bộ kiểm thử TDD (JUnit 5) & Custom Exception | ✅ Hoàn thành |
| 3 | Tầng dữ liệu (Generic Repository & File IO) | ✅ Hoàn thành |
| 4 | Logic nghiệp vụ Service (withdraw, deposit, transfer, changePin) | ✅ Hoàn thành |
| 5 | Controller, CLI View, Login/Register & Nghiệm thu | ✅ Hoàn thành |

---


