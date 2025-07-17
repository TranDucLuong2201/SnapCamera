# 📷 SnapCamera (WIP)

SnapCamera là ứng dụng Android giúp sinh viên ghi lại hoạt động học tập của mình bằng ảnh chụp tự động theo lịch học. Dự án được phát triển bằng **Jetpack Compose toàn phần**, theo kiến trúc hiện đại, dễ mở rộng và module hóa rõ ràng.

---

## 🎯 Mục tiêu

Ứng dụng hỗ trợ sinh viên:

- Nhập thời khóa biểu học tập
- Chụp ảnh tự động hoặc thủ công khi đang trong buổi học
- Lưu ảnh theo thư mục ngày và môn học
- Xem lại, chia sẻ hoặc export ảnh thành ZIP
- Quản lý môn học, kỳ học và lịch thi

---

## 🛠️ Công nghệ sử dụng

| Công nghệ             | Vai trò                              |
|----------------------|---------------------------------------|
| Jetpack Compose      | UI hiện đại, khai báo toàn phần       |
| CameraX              | Chụp ảnh theo thời gian thực          |
| Media3 / ExoPlayer   | Phát video (sẽ dùng nếu có tính năng quay) |
| Room (SQLite)        | Lưu dữ liệu TKB, ảnh, lịch thi        |
| ViewModel + StateFlow| Quản lý trạng thái                    |
| Hilt                 | Dependency Injection                  |
| WorkManager (planned)| Tự động chụp theo lịch                |
| Navigation Compose   | Điều hướng giữa các màn hình          |

---

## 🧱 Kiến trúc tổng quan

- `presentation/`: Compose UI và các màn hình
- `data/`: Room database và entity models
- `domain/`: Các use-case (planned)
- `di/`: Cấu hình Hilt
- `utils/`: Hỗ trợ xử lý file, ngày giờ

---

## ✨ Tính năng (planned)

- [] Giao diện camera với preview và nút chụp
- [x] Chọn ảnh từ thư viện hệ thống
- [x] Hiển thị ảnh chụp gần nhất
- [x] Đăng nhập bằng email sinh viên
- [ ] Nhập và hiển thị thời khóa biểu
- [ ] Gợi ý môn học hiện tại
- [ ] Chụp ảnh tự động theo lịch học
- [ ] Lưu ảnh kèm metadata: thời gian, môn học
- [ ] Xem ảnh theo ngày hoặc môn
- [x] Export ZIP ảnh và chia sẻ
- [ ] Quản lý kết thúc môn học và kỳ học mới

---

## 📸 Lưu ảnh theo cấu trúc

```bash
/storage/emulated/0/SnapCameraPhotos/{yyyy-MM-dd}/{tenMon}/{hhmmss}.jpg
