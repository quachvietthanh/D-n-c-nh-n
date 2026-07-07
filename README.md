# 🏦 Advanced ATM Simulator

> **Java Core Console Application** — Mô phỏng hệ thống quản lý tài khoản và giao dịch tại máy ATM.

---

## 📋 Giới thiệu

Dự án **Advanced ATM Simulator** là ứng dụng Java Core thuần chạy trên môi trường Console, mô phỏng các chức năng của một máy ATM thực tế. Được xây dựng theo kiến trúc **phân tầng (3-Tier Architecture)** với các nguyên lý **OOP**, **SOLID**, **Design Pattern** và **Thread Safety**.

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
| 🧪 **JUnit 5 (TDD)** | 12 test cases — Viết test trước, code xử lý sau |
| 🔒 **Thread Safety** | `synchronized` methods + try-with-resources |
| 🔗 **Dependency Injection** | Interface `AccountServiceInterface` → `AccountServiceImpl` → DI qua constructor |
| ✅ **Data Validation** | Kiểm tra số dư, PIN 6 số, số tiền hợp lệ |

---

## 📁 Cấu trúc dự án

```
src/
└── com/atm/
    ├── Main.java                              # 🚀 Điểm khởi chạy + Login + Register
    ├── controller/
    │   └── ATMController.java                 # Điều phối View → Service (DI)
    ├── entity/
    │   ├── User.java                          # Abstract class (Serializable)
    │   ├── Admin.java                         # Kế thừa User
    │   ├── Account.java                       # Kế thừa User
    │   ├── Transaction.java                   # Lịch sử giao dịch
    │   └── UserFactory.java                   # Factory Pattern
    ├── exception/
    │   ├── InsufficientBalanceException.java  # Số dư không đủ
    │   ├── AccountLockedException.java        # Tài khoản bị khóa
    │   └── InvalidPinException.java           # Sai mã PIN
    ├── repository/
    │   ├── BaseRepository.java                # Generic Interface `<T>`
    │   ├── BaseRepositoryImpl.java            # File IO (try-with-resources)
    │   ├── AccountRepository.java             # data/accounts.dat
    │   └── TransactionRepository.java         # data/transactions.dat
    ├── service/
    │   ├── AccountServiceInterface.java       # Interface cho Service Layer
    │   ├── AccountServiceImpl.java            # Business Logic (synchronized)
    │   └── AccountServiceTest.java            # 12 JUnit 5 Test Cases
    ├── view/
    │   └── ATMView.java                       # CLI Menu (try-catch đa tầng)
    └── util/                                  # (dự phòng)
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

## 🔗 Dependency Injection (DI)

```
Main.java
 ├── AccountRepository (trực tiếp cho Login/Register)
 ├── AccountServiceImpl
 │    └── AccountRepository (tự khởi tạo)
 ├── ATMController(AccountServiceInterface ← AccountServiceImpl)  ← DI
 └── ATMView(ATMController)
```

---

## ✅ Validation & Thread Safety

| Phương thức | Validation | Thread Safety |
|:-----------|:-----------|:-------------:|
| `withdraw()` | Tài khoản khóa? Số dư đủ? | `synchronized` |
| `deposit()` | Số tiền > 0? | `synchronized` |
| `transfer()` | Nguồn khóa? Đủ tiền? Đích tồn tại? Đích khóa? | `synchronized` |
| `changePin()` | PIN cũ đúng? PIN mới 6 số? | `synchronized` |

---

## 🧪 Tài khoản mẫu

| Số tài khoản | Mã PIN | Số dư | Username |
|:------------:|:------:|:-----:|:--------:|
| `1234567890` | `123456` | 5,000,000₫ | user1 |
| `0987654321` | `654321` | 3,000,000₫ | user2 |
| `1111111111` | `111111` | 10,000,000₫ | admin |

> Tài khoản mẫu được tự động tạo khi chạy lần đầu nếu chưa có dữ liệu.

---

## 🧪 Bộ kiểm thử JUnit 5 (12 Test Cases)

| Chức năng | Test case | Kỳ vọng |
|:----------|:----------|:--------|
| **Rút tiền** | `testWithdraw_Success` | 500k → rút 200k = 300k ✅ |
| | `testWithdraw_InsufficientBalance_ShouldThrowException` | Rút > số dư → `InsufficientBalanceException` ✅ |
| | `testWithdraw_AccountLocked_ShouldThrowException` | TK khóa → `AccountLockedException` ✅ |
| **Nạp tiền** | `testDeposit_Success` | 500k → nạp 100k = 600k ✅ |
| | `testDeposit_NegativeAmount_ShouldThrowException` | Số âm → `IllegalArgumentException` ✅ |
| | `testDeposit_ZeroAmount_ShouldThrowException` | Số 0 → `IllegalArgumentException` ✅ |
| **Chuyển tiền** | `testTransfer_Success` | Chuyển 200k, nguồn còn 300k ✅ |
| | `testTransfer_InsufficientBalance_ShouldThrowException` | Chuyển > số dư → `InsufficientBalanceException` ✅ |
| | `testTransfer_AccountNotFound_ShouldThrowException` | TK đích ko tồn tại → `IllegalArgumentException` ✅ |
| **Đổi PIN** | `testChangePin_Success` | PIN cũ → PIN mới ✅ |
| | `testChangePin_WrongOldPin_ShouldThrowException` | Sai PIN cũ → `InvalidPinException` ✅ |
| | `testChangePin_NewPinNotSixDigits_ShouldThrowException` | PIN mới 5 số → `InvalidPinException` ✅ |
| | `testChangePin_NewPinContainsLetters_ShouldThrowException` | PIN mới "abc123" → `InvalidPinException` ✅ |

---

## 📅 Tiến độ dự án

| Tuần | Nội dung | Trạng thái |
|:----:|----------|:----------:|
| 1 | Khởi tạo kiến trúc & Entities (Factory Pattern) | ✅ Hoàn thành |
| 2 | Bộ kiểm thử TDD (JUnit 5) & Custom Exception | ✅ Hoàn thành |
| 3 | Tầng dữ liệu (Generic Repository & File IO) | ✅ Hoàn thành |
| 4 | Logic nghiệp vụ Service (withdraw, deposit, transfer, changePin) | ✅ Hoàn thành |
| 5 | Controller, CLI View, Login/Register & Nghiệm thu | ✅ Hoàn thành |
| 6 | **Refactor:** Interface Service, DI, Thread Safety, Validation, 12 Tests | ✅ Hoàn thành |

---

## 🚀 Hướng dẫn chạy

```bash
# 1. Biên dịch toàn bộ source code
javac -d bin -cp lib/junit-platform-console-standalone.jar src/com/atm/**/*.java src/com/atm/Main.java

# 2. Chạy chương trình
java -cp bin com.atm.Main
```

> **Yêu cầu:** Java 8+ và thư viện `junit-platform-console-standalone.jar` trong thư mục `lib/`.

---

## 👤 Tác giả

**Dự án cá nhân — CodeGym**  
Mô phỏng máy ATM bằng Java Core thuần — Kiến trúc 3-Tier — Thread Safety — TDD

---

<p align="center"><i>⭐ Nếu bạn thấy dự án hữu ích, hãy để lại một ngôi sao nhé!</i></p>
