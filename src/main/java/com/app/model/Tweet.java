package com.app.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tweets")
public class Tweet {
	
	//@Version private Long Version;
	@Id
	private String id;
	private String tweetName;
	@CreatedDate
	private LocalDate postDate;
	private int likes;
	private User user;
	private List<Reply> replies;
	private String tweetTag;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTweetName() {
		return tweetName;
	}
	public void setTweetName(String tweetName) {
		this.tweetName = tweetName;
	}
	public LocalDate getPostDate() {
		return postDate;
	}
	public void setPostDate(LocalDate postDate) {
		this.postDate = postDate;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Reply> getReplies() {
		return replies;
	}
	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	public String getTweetTag() {
		return tweetTag;
	}
	public void setTweetTag(String tweetTag) {
		this.tweetTag = tweetTag;
	}
	
	@Override
	public String toString() {
		return "Tweet Details: [id=" + id + ", tweetName=" + tweetName + ", postDate=" + postDate + ", likes=" + likes
				+ ", user=" + user + ", replies=" + replies + ", tweetTag=" + tweetTag + "]";
	}
	
 	

}
