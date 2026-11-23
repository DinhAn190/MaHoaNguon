# LAB 3 – Mã hóa khóa công khai

Đặng Đình An 
MSSV 11150080001 
Học phần An toàn và bảo mật thông tin

Ứng dụng Java Swing (Maven, NetBeans) minh họa mật mã khóa công khai, có ghi lịch sử thao tác vào SQL Server.

## Đã làm được

- **3.1 RSA (console):** tạo khóa 2048/3072 bit, mã hóa/giải mã "HELLO RSA" bằng RSA-OAEP-SHA256; giới hạn bản rõ 190/318 byte.
- **3.2 RSA (Swing):** form mã hóa/giải mã; cùng bản rõ cho bản mã khác nhau mỗi lần.
- **3.3 ECC:** mã hóa ECDH (secp256r1) + HKDF + AES-GCM; lưu/nạp khóa; khóa sai thì giải mã thất bại.
- **3.4 RSA + AES:** mã hóa văn bản dài hơn 1.000 ký tự; sửa bản mã thì giải mã bị từ chối.
- **3.5 ECDSA:** ký và xác minh; "THONG DIEP A" → VALID, sửa thông điệp hoặc đổi khóa → INVALID.
- **SQL Server:** kết nối JDBC, mỗi thao tác ghi vào `dbo.History`, xem lịch sử trên form và trong SSMS.

## Chạy

1. Chạy `01_Tao_CSDL.sql` và `02_Tao_TaiKhoan_LAB.sql` trong SSMS.
2. Mở project trong NetBeans → Clean and Build → F6.
3. Bấm Kết nối SQL Server → Test Connection, rồi mở các bài từ menu.

Khó khăn và cách khắc phục

Không lưu được lịch sử SQL: mở form bằng Run File riêng lẻ thì form không biết đã kết nối → chạy F6, Test Connection rồi mở các bài từ menu.
Bảng lịch sử trống dù đã kết nối: thao tác làm trước khi kết nối hoặc trên cửa sổ cũ, form lịch sử và SSMS không tự cập nhật → làm lại sau khi kết nối, bấm Tải lại / Execute SQL.
RSA không mã hóa được văn bản dài: RSA-2048 chỉ mã hóa tối đa 190 byte → dùng hệ mã lai RSA + AES (bài 3.4).
