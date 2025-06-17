package org.sopt.post.service.request;

import java.util.List;

import org.sopt.post.domain.constant.Tag;

public record PostCreateCommand(String title, String content, List<Tag> tags) {

}
