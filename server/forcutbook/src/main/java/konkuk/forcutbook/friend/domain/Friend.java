package konkuk.forcutbook.friend.domain;

import jakarta.persistence.*;
import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.global.domain.TimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Friend extends TimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User receiver;

    private boolean isAccept;

    private Friend(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public static Friend createFriend(User sender, User receiver){
        Friend friend = new Friend(sender, receiver);
        friend.isAccept = false;
        return friend;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }
}
