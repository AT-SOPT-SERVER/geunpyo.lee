package org.sopt.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class PostCacheService {
	private static final String USER_POST_TIME_CACHE = "userPostTimeCache";

	private final CacheManager cacheManager;

	public PostCacheService(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public Optional<LocalDateTime> getUserLastPostTime(int userId, Duration coolTime) {
		Cache cache = cacheManager.getCache(USER_POST_TIME_CACHE);
		if (cache == null) {
			return Optional.empty();
		}

		Cache.ValueWrapper wrapper = cache.get(userId);
		if (wrapper == null) {
			return Optional.empty();
		}

		LocalDateTime lastPostTime = (LocalDateTime)wrapper.get();
		Duration timeSinceLastPost = Duration.between(lastPostTime, LocalDateTime.now());

		if (timeSinceLastPost.compareTo(coolTime) >= 0) {
			cache.evict(userId);
			return Optional.empty();
		}

		return Optional.of(lastPostTime);
	}

	public void updateUserLastPostTime(int userId, LocalDateTime time) {
		Cache cache = cacheManager.getCache(USER_POST_TIME_CACHE);
		if (cache != null) {
			cache.put(userId, time);
		}
	}
}
