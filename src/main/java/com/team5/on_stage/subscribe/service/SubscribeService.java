package com.team5.on_stage.subscribe.service;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.subscribe.entity.Subscribe;
import com.team5.on_stage.subscribe.repository.SubscribeRepository;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final UserRepository userRepository;
    private final LinkRepository linkRepository;


    @Transactional
    public Boolean subscribeLink(String username, Long linkId) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new GlobalException(ErrorCode.LINK_NOT_FOUND));

        // Subscribe 기록이 없으면 추가, 있으면 삭제
        if (!subscribeRepository.existsSubscribeByUsernameAndLinkId(username, linkId)) {

            Subscribe subscribe = new Subscribe(user, link);
            subscribeRepository.save(subscribe);

            link.subscribe();

        } else {

            subscribeRepository.deleteSubscribeByUsernameAndLinkId(username, linkId);

            link.unsubscribe();

        }
        return true;
    }

}
