package org.lx.cs.server.game.user.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Accessors(chain = true)
@Data
@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String account;
    private String password;

}
