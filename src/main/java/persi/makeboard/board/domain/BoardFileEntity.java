package persi.makeboard.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persi.makeboard.global.entity.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "board_file_table")
public class BoardFileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY) // 필요한 상황에 사용할 수 있음
    @JoinColumn(name = "board_id") // db에 들어가는 column 이름
    private Board board;

}
