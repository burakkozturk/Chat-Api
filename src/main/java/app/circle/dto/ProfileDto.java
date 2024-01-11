package app.circle.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    String email;
    String phoneNumber;
    String nickname;
    String profilePhoto;
}
