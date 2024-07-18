# BTL số 1: Từ điển cơ bản

Lớp thực hành: N8
Nhóm: 2.
Thành viên: 
- Nguyễn Minh Đức - 20020394 - N8
- Bùi Văn Tâm - 20020470 - N8

# Hướng dẫn cài đặt:
- Clone hoặc download mã nguồn và chạy trên IDE, khuyên dùng Intellij.
- File -> Project Structure -> Libraries -> Add 3 thư viện gồm JavaFX, Sphinx và FreeTTS.
- Build and Run class DictionaryApplication
- Run -> Edit configurations -> Modify options -> Add VM options -> Ở thanh VM Options nhập vào dòng sau:  
    --module-path "/đường dẫn tới thư mục lib của javafx trên máy bạn/" 
    --add-modules javafx.controls,javafx.fxml

- Giờ thì bạn đã có thể chạy chương trình.
- NOTE: Lần chạy đầu tiên có thể sẽ không load được image, nếu trường hợp đó xảy ra thì tắt hết cửa sổ IDE đi rồi mở lại. Trường hợp lỗi các đường dẫn thì tự xử lí tùy theo máy tính của bạn.

# Giới thiệu tính năng: 

1. Giao diện ban đầu:

![window1](/uploads/4835242919dfaab9b8eab85b00f7474f/window1.jpg)

2. Các chức năng cơ bản:

- Tìm kiếm và tra cứu: Sau khi nhập từ cần tìm vào và bấm chuột vào nút Search hoặc bấm phím ENTER thì sẽ xuất hiện 1 danh sách các từ có chứa kí tự cần tìm và hiện thị ra mà hình nghĩa của từ nếu từ đó có trong từ điển. Khi từ không có trong từ điển thì sẽ hiển thị "Word not found."

![search_window](/uploads/829c715ac61336ff100a00ca56ae77bf/search_window.jpg)

- Thêm từ vào từ điển: Nhập từ cần thêm vào, nếu trong từ điển đã có sẵn từ được thêm vào thì phần nghĩa được thêm vào sẽ gộp chung với nghĩa có sẵn. Trường hợp không nhập từ hoặc không nhập nghĩa sẽ có thông báo lỗi.

- Xóa từ: Nhập từ cần xóa vào, nếu trong từ điển không có từ đó thì sẽ có thông báo lỗi.

- Sửa từ: Nhập vào từ cần sửa, lựa chọn sửa từ, nghĩa của từ hoặc cả 2. Trường hợp không có từ muốn sửa hoặc không nhập nội dung cần sửa sẽ có thông báo lỗi.

- Phát âm: Giọng tiếng Anh của kevin16 tuy hơi khó nghe nhưng tạm chấp nhận được, không khuyến khích người dùng học phát âm theo. 
3. Các chức năng bổ sung: 

- Lịch sử tra cứu: Hiển thị các từ đã tra gần đây.

- Nhận diện giọng nói: Sau khi click vào nút Voice, đợi khoảng 3s rồi nói từ muốn tìm, máy sẽ ghi âm trong vòng 3-5s rồi đưa ra kết quả trên thanh tìm kiếm. Sẽ có dòng thông báo "Click to try again." để dễ nhận biết.
![voice](/uploads/8d4a358a7bc32121c139cf980ed2ccdb/voice.jpg)

- Note: Máy có thể nhận biết được 1000 từ thông dụng trong tiếng Anh nhưng khả năng nghe không được linh hoạt, xin bạn hãy nói gì đó đơn giản và phát âm thật chuẩn, nếu máy nhận biết sai cũng đừng đổ lỗi cho khả năng phát âm của mình. Và có thể sẽ hơi đơ 1 tí. Sẽ cải thiện ở phiên bản sau, hoặc là không.

