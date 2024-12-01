package com.team5.on_stage.linklike.service;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linklike.entity.LinkLike;
import com.team5.on_stage.linklike.repository.LinkLikeRepository;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LinkLikeService {

    private final LinkLikeRepository linkLikeRepository;
    private final UserRepository userRepository;
    private final LinkRepository linkRepository;


    @Transactional
    public Boolean likeLink(Long userId, Long linkId) {

        Boolean isExist = linkLikeRepository.existsLinkLikeByUserIdAndLinkId(userId, linkId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new GlobalException(ErrorCode.LINK_NOT_FOUND));

        if (!isExist) {

            LinkLike linkLike = new LinkLike(user, link);
            linkLikeRepository.save(linkLike);

            link.Like();

            return true;
        } else {

            linkLikeRepository.deleteLinkLikeByUserIdAndLinkId(userId, linkId);

            link.unLike();

            return true;
        }
    }

}
