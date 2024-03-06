package app.circle.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest {
    private UUID senderId;
    private Long chatHistoryId;
    private String content;

}



/*
*
* 1f66c84b-0688-4a83-9d85-731c9d065177
*
* 87218fe0-e37c-4cbc-b970-7c3eadfa587a
*
*
* */