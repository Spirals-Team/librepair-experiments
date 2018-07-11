insert into post values
(
    :commentId,
    NULL,
    :creationDate,
    :locationIp,
    :browserUsed,
    NULL,
    :content,
    :length,
    :authorPersonId,
    NULL,
    :countryId,
    NULL,
    :replyToCommentId + :replyToPostId + 1
);
