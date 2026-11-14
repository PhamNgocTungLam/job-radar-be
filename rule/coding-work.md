# Quy ước quy trình (docs/process)

Áp dụng cho monorepo: `core-api`, `crawler-service`, `web`, `infra`, `docs`.
Mỗi thay đổi đều gắn với một ticket `JR-xxx` trong tab Tickets.

## 1. Git flow

- `main` luôn chạy được và là nhánh duy nhất tồn tại lâu dài. Không commit trực tiếp vào `main`.
- Mỗi ticket một nhánh, tách từ `main`:

  ```
  feature/JR-<số ticket>-<mô tả-ngắn-kebab-case>
  ```

  Ví dụ: `feature/JR-105-ingest-jobs-api`, `feature/JR-207-job-list-screen`.
- Nhánh sống ngắn (1–3 ngày). Ticket lớn thì tách nhỏ, không để nhánh treo cả sprint.
- Cập nhật nhánh bằng `git rebase main` (hoặc merge `main` vào nhánh) trước khi mở PR.
- Merge vào `main` bằng **Squash and merge**, xóa nhánh sau khi merge.
- Tiêu đề commit squash = tiêu đề PR, theo Conventional Commits.

## 2. Conventional Commits

Định dạng:

```
<type>(<scope>): <mô tả ngắn, thể mệnh lệnh, không chấm cuối>

[body: giải thích vì sao, không phải làm gì]

[footer: Refs JR-xxx]
```

**type**

| type | Dùng khi |
| --- | --- |
| `feat` | Thêm chức năng |
| `fix` | Sửa lỗi |
| `refactor` | Đổi cấu trúc code, không đổi hành vi |
| `test` | Thêm / sửa test |
| `docs` | Chỉ đổi tài liệu |
| `chore` | Việc vặt: dependency, config, .gitignore |
| `build` | Dockerfile, Maven, Vite, pyproject |
| `ci` | GitHub Actions |
| `perf` | Cải thiện hiệu năng |

**scope**: `core-api` · `crawler` · `web` · `db` · `infra` · `docs`

**Ví dụ**

```
feat(core-api): add POST /internal/jobs ingest endpoint

Upsert theo content_hash để cùng một job từ nhiều nguồn chỉ lưu một lần.

Refs JR-105
```

```
fix(crawler): stop pagination when ITviec returns empty page
```

```
feat(db)!: rename job.location to job.location_code

BREAKING CHANGE: cần chạy migration V4
```

Quy tắc:
- Mô tả ≤ 72 ký tự, viết thường chữ đầu.
- Mỗi commit làm một việc; commit phải build được.
- Thay đổi phá vỡ tương thích: thêm `!` sau scope và dòng `BREAKING CHANGE:` ở footer.
- Không commit `.env`, `data/`, token Gmail, CV thật.

## 3. Pull Request

- Tiêu đề PR: `<type>(<scope>): <mô tả> (JR-xxx)`.
- Một PR = một ticket. PR nên ≤ 400 dòng đổi (không tính file sinh tự động).
- Tự review diff của mình trước khi bấm Create.
- CI phải xanh trước khi merge.

### PR template

Lưu thành `.github/pull_request_template.md`:

```markdown
## Ticket
Closes JR-xxx

## Thay đổi
<!-- Làm gì và vì sao, 2–5 dòng -->

## Cách kiểm tra
<!-- Lệnh chạy / bước bấm / request mẫu -->

## Ảnh chụp / log
<!-- Với thay đổi UI hoặc crawl: ảnh, hoặc đoạn log kết quả -->

## Checklist
- [ ] Đã đạt Acceptance criteria của ticket
- [ ] Test đã thêm / cập nhật và chạy xanh
- [ ] Migration Flyway mới (nếu có) chạy được trên DB sạch
- [ ] API đổi → đã cập nhật `docs/api/openapi.yaml` và tab API Spec
- [ ] Bảng đổi → đã cập nhật `docs/db/` và tab DB Design
- [ ] Không có secrets, `.env`, `data/` trong diff
- [ ] Đã chuyển ticket sang Review / Done
```

## 4. Definition of Done

Một ticket chỉ chuyển sang **Done** khi đủ tất cả:

1. **Acceptance criteria** của ticket đều đạt (kiểm tra từng dòng).
2. **Code**: build và lint không lỗi; theo cấu trúc package `controller/service/mapper/dto/vo` (core-api), `app/sources` (crawler), route theo tab Màn hình (web).
3. **Test**:
  - core-api: `mvn verify` xanh; logic mới có unit test, mapper có test trên Postgres thật (Testcontainers).
  - crawler: `pytest` xanh, adapter test bằng HTML mẫu (fixture), chạy offline.
  - web: `npm run build` xanh; có loading / empty / error state với màn hình lấy dữ liệu.
4. **CI** trên PR xanh (mvn verify, pytest, npm run build).
5. **DB**: thay đổi schema đi qua migration Flyway, chạy được trên DB sạch, không sửa migration đã merge.
6. **Tài liệu**: API / DB / ADR liên quan đã cập nhật cùng PR.
7. **Bảo mật**: không có secrets trong repo; không log CV hay token.
8. **Review**: đã tự review diff, PR đã squash merge vào `main`, nhánh đã xóa.
9. **Tracking**: cột Trạng thái của ticket trong sheet là `Done`.
