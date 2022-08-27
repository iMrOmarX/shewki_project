package co.cotede.shewki_project.model;

import co.cotede.shewki_project.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String userName ;
    private String password;
    private Post addedPost ;
}