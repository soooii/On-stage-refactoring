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


    @Transactional
    public Boolean subscribeLink(String subscriber, String subscribed) {

        if (subscriber.equals(subscribed)) {
            throw new GlobalException(ErrorCode.CANNOT_SUBSCRIBE_SELF);
        }

        User user = userRepository.findByUsername(subscriber);

        if (user == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        User subscribedUser = userRepository.findByUsername(subscribed);

        if (subscribedUser == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        // Subscribe 기록이 없으면 추가, 있으면 삭제
        if (!subscribeRepository.existsSubscribeBySubscriberAndSubscribed(subscriber, subscribed)) {

            Subscribe subscribe = new Subscribe(user, subscribedUser);
            subscribeRepository.save(subscribe);

            subscribedUser.subscribe();

        } else {

            subscribeRepository.deleteSubscribeBySubscriberAndSubscribed(subscriber, subscribed);

            subscribedUser.unsubscribe();

        }
        return true;
    }

}
