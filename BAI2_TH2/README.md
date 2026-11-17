Họ và tên: Đặng Đình An 
Lớp: 11_ĐH_THMT 
MSSV:115008001 
Lab 2: Mã Hóa Hiện Đại 
Hướng dẫn cách chạy 
| Bài | Thuật toán | Form |
|---|---|---|
| 2.1 | DES (CBC, khóa 8 byte) | `frm_DES` |
| 2.2 | TripleDES (khóa 24 byte) | `frm_TripleDES` |
| 2.3 | AES-256-GCM + Đăng ký / Đăng nhập SQL Server | `frm_AES` |
| 2.4 | RC4 (bản mã HEX) | `frm_RC4` |
| 2.5 | Twofish-CBC (BouncyCastle) | `frm_Twofish` |
| 2.6 – Bài 01 | AES-256-CTR (Java) | `frm_AES_CTR` |
| 2.6 – Bài 02 | AES-256-CBC (Python) | `aes_cbc_gui.py` |
| Phần 3 | Menu chính | `frm_Menu` |

Giao diện Swing được viết trực tiếp bằng code (GridBagLayout), không dùng tệp `.form` của NetBeans GUI Builder.

## Hướng dẫn cách chạy

### Yêu cầu phần mềm
- JDK 17 hoặc 21
- Apache NetBeans 20 trở lên
- SQL Server Express + SSMS (bật TCP/IP, cổng 14333, chế độ SQL Server and Windows Authentication)
- Python 3.10 trở lên

### Bước 1 – Chuẩn bị SQL Server
1. Mở SSMS, chạy `01_Tao_CSDL.sql` để tạo CSDL `LAB2_MaHoaHienDai` và 2 bảng `app_users`, `crypto_history`.
2. Mở `02_Tao_Tai_Khoan_SQL.sql`, thay `<DAT_MAT_KHAU_CUA_BAN>` bằng mật khẩu tự đặt rồi chạy để tạo tài khoản `lab2_user`.

### Bước 2 – Chạy chương trình Java
1. Mở NetBeans → **File → Open Project** → chọn thư mục `LAB2_MaHoaHienDai`.
2. Nhấp phải project → **Clean and Build** (lần đầu cần Internet để tải thư viện `mssql-jdbc` và `bcprov-jdk18on`).
3. Bấm **Run (F6)** để mở Menu chính.
4. Bấm **Kết nối SQL Server**, nhập:
   - Server: `127.0.0.1`
   - Port: `14333`
   - Database: `LAB2_MaHoaHienDai`
   - User: `lab2_user`
   - Password: mật khẩu đã đặt ở Bước 1

   Sau đó bấm **Test Connection**.
5. Mở từng bài từ Menu để mã hóa / giải mã. Bấm **Xem lịch sử** để xem bảng `crypto_history`.

### Bước 3 – Chạy chương trình Python 
py -m pip install cryptography
py aes_cbc_gui.py --self-test
py aes_cbc_gui.py
## Dữ liệu kiểm thử

| Bài | Dữ liệu thử | Kết quả |
|---|---|---|
| DES | "Xin chào Lab 2 - Mã hóa hiện đại!", Key `12345678` | Giải mã đúng, mỗi lần mã hóa ra bản mã khác |
| TripleDES | Key `123456789012345678901234` | Giải mã đúng; Key 23 ký tự báo lỗi |
| AES | Register + Login `sinhvien01` | Có Ticket, SQL Server có dữ liệu mới |
| RC4 | Plaintext `Plaintext`, Key `Key` | `BBF316E8D940AF0AD3` |
| Twofish | Key `Twofish@Lab2` + Generate IV | Save/Open khôi phục được IV, giải mã đúng |
| AES-CTR | Key + Nonce ngẫu nhiên | Độ dài bản mã = độ dài bản rõ |
| AES-CBC Python | Văn bản dài | `SELF-TEST OK` |
