package org.lx.cs.hall.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

//@Setter
//@Getter
@Data
@Document(collation = "player")
public class Player {

    @Id
    private long _id;
//    @Indexed(unique = true)
    private String name;
    private int coin;
    private int cups;
    private List<HeroInfo> heroList;




}
