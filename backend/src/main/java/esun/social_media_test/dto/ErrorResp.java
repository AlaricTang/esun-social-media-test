package esun.social_media_test.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResp {
	private int status;          // HTTP 狀態碼
	private String message;      // 錯誤詳細訊息
	private LocalDateTime timestamp; // 發生時間
}

