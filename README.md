# Reddit Copy API
## Simple API that Emulates the Reddit Backend
### Authentication Endpoints
> These endpoints are available so that a person can log in or register to the application
>>### POST Requests
>>Register New User.
>>
>>This request gives back a user object containing the new user's token in it.
>>The request body must have a username and password field.
>>```JSON
>>request endpoint:
>>localhost:8080/api/v1/authentication/register/
>>
>>request body:
>>{
>>    "username": "user1",
>>    "password": "user1pw"
>>}
>>```
>>Login
>>
>>This request gives back a user object containing the user's recently created token. The request body must have a username and password field.
>>```JSON
>>request endpoint:
>>localhost:8080/api/v1/authentication/login/
>>
>>request body:
>>{
>>    "username": "user1",
>>    "password": "user1pw"
>>}
>>```
### General Endpoints
> These endpoints are available for non-logged users.
>>### GET Requests
>> Get Homepage.
>> 
>> This request gives back an answer containing varied posts from the different available subreddits (similar to the r/all subreddit).
>>```JSON
>>request endpoint:
>>localhost:8080/api/v1/general/
>>```
>>Get All Subs
>>
>>This request gives back an answer containing all the subreddits registered in the application.
>>```JSON
>>request endpoint
>>localhost:8080/api/v1/general/sub/
>>```
>>Get Single Sub
>>
>>This request gives back an answer containing a sub's information and all topics posted to it.
>>```JSON
>>request endpoint
>>localhost:8080/api/v1/general/sub/{subId}/
>>```
>>Get Single Topic
>>
>>This request gives back a topics' information and all comments attached to it.
>>```JSON
>>request endpoint
>>localhost:8080/api/v1/general/topic/{topicId}/
>>```
>>Get Single Comment
>>
>>To avoid recursion problems, the comments made under another comment aren't loaded right away, to see them, this endpoint is needed. It gives back a comment and the comments under it. 
>>```JSON
>>request endpoint
>>localhost:8080/api/v1/general/comment/{commentId}/
>>```
>>Get User
>>
>>This request gives back a user's profile and the topics and all the comments and topics that they've made.
>>```JSON
>>request endpoint
>>localhost:8080/api/v1/general/user/{userId}/
>>```
### User Endpoints
> These endpoints are available for logged users, whose proof of login must be the JWT in a Authorization Bearer header in each request.
>>### GET Requests
>> Get Homepage.
>>
>>This request gives back the user's homepage, containing one post from each one of the user's followed subs.
>>```JSON
>>request endpoint
>>localhost:8080/api/v1/user/
>>```
>>Get User's Sub Status.
>>
>>This request gives back the user's status regarding a certain sub, if the user follows it, it returns true, if the user doesn't follow it, it returns false.
>>```JSON
>>request endpoint
>>localhost:8080/api/v1/user/subinfo/{subId}/
>>```
>>Get User's Post Status
>>
>>This request gives back the user's status regarding a certain post (it can be either comment or topic), returning 0, if the user hasn't upvoted or downvoted it, returning -1 if the user has downvoted it, and returning 1 if the user has upvoted it.
>>```JSON
>>request endpoint
>>localhost:8080/api/v1/user/postinfo/{postId}/
>>```
>>Get User's Followed Subs
>>
>>This request gives back a list containing data on all the subs that the user (passed as JWT) follows.
>>```JSON
>>request endpoint
>>localhost:8080/api/v1/user/sub/
>>```
>
>>POST Requests
>>
>>
>>
>>
>>
>>
>>
