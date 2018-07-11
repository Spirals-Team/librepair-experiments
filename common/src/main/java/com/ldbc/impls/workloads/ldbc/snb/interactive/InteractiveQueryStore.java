package com.ldbc.impls.workloads.ldbc.snb.interactive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery10;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery11;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery12;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery13;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery14;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery2;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery4;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery5;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery6;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery7;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery9;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery1PersonProfile;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery2PersonPosts;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery3PersonFriends;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContent;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreator;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageReplies;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate2AddPostLike;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;

import java.util.List;

public abstract class InteractiveQueryStore extends QueryStore<InteractiveQueryStore.InteractiveQuery> {


    public enum InteractiveQuery {
        Query1 ("query1",  ImmutableList.of("Person", "Name")),
        Query2 ("query2",  ImmutableList.of("Person", "Date0")),
        Query3 ("query3",  ImmutableList.of("Person", "Country1", "Country2", "Date0", "Duration")),
        Query4 ("query4",  ImmutableList.of("Person", "Date0", "Duration")),
        Query5 ("query5",  ImmutableList.of("Person", "Date0")),
        Query6 ("query6",  ImmutableList.of("Person", "Tag")),
        Query7 ("query7",  ImmutableList.of("Person")),
        Query8 ("query8",  ImmutableList.of("Person")),
        Query9 ("query9",  ImmutableList.of("Person", "Date0")),
        Query10("query10", ImmutableList.of("Person", "HS0", "HS1")),
        Query11("query11", ImmutableList.of("Person", "Date0", "Country")),
        Query12("query12", ImmutableList.of("Person", "TagType")),
        Query13("query13", ImmutableList.of("Person1", "Person2")),
        Query14("query14", ImmutableList.of("Person1", "Person2")),

        ShortQuery1PersonProfile ("shortquery1personprofile.sql", ImmutableList.of()),
		ShortQuery2PersonPosts   ("shortquery2personposts.sql",    ImmutableList.of("personId")),
		ShortQuery3PersonFriends ("shortquery3personfriends.sql",  ImmutableList.of("personId")),
		ShortQuery4MessageContent("shortquery4messagecontent.sql", ImmutableList.of("messageId")),
		ShortQuery5MessageCreator("shortquery5messagecreator.sql", ImmutableList.of("messageId")),
		ShortQuery6MessageForum  ("shortquery6messageforum.sql",   ImmutableList.of("messageId")),
		ShortQuery7MessageReplies("shortquery7messagereplies.sql", ImmutableList.of("messageId")),

//		Update1AddPerson("update1addperson.sql", ImmutableList.of()),
//		Update1AddPersonCompanies("update1addpersoncompanies.sql", ImmutableList.of()),
//		Update1AddPersonEmails("update1addpersonemails.sql", ImmutableList.of()),
//		Update1AddPersonLanguages("update1addpersonlanguages.sql", ImmutableList.of()),
//		Update1AddPersonTags("update1addpersontags.sql", ImmutableList.of()),
//		Update1AddPersonUniversities("update1addpersonuniversities.sql", ImmutableList.of()),

		Update2AddPostLike("update2addpostlike.sql", ImmutableList.of(
                LdbcUpdate2AddPostLike.PERSON_ID,
                LdbcUpdate2AddPostLike.POST_ID,
                LdbcUpdate2AddPostLike.CREATION_DATE
        )),

//		Update3AddCommentLike("update3addcommentlike.sql", ImmutableList.of()),

//		Update4AddForum("update4addforum.sql", ImmutableList.of()),
//		Update4AddForumTags("update4addforumtags.sql", ImmutableList.of()),

//		Update5AddForumMembership("update5addforummembership.sql", ImmutableList.of()),

//		Update6AddPost("update6addpost.sql", ImmutableList.of()),
//		Update6AddPostTags("update6addposttags.sql", ImmutableList.of()),

//		Update7AddComment("update7addcomment.sql", ImmutableList.of()),
//		Update7AddCommentTags("update7addcommenttags.sql", ImmutableList.of()),

//		Update8AddFriendship("update8addfriendship.sql", ImmutableList.of()),
        ;

        private String name;
        private List<String> parameters;

        InteractiveQuery(String name, List<String> parameters) {
            this.name = name;
            this.parameters = parameters;
        }

//        public List<String> getParameters() {
//            return parameters;
//        }
    }

    public InteractiveQueryStore(String path, String prefix, String postfix) throws DbException {
        for (InteractiveQuery interactiveQuery : InteractiveQuery.values()) {
            queries.put(interactiveQuery, loadQueryFromFile(path, prefix + interactiveQuery.name + postfix));
        }
    }

    public String getQuery1(LdbcQuery1 operation) {
        return prepare(InteractiveQuery.Query1, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Name", getConverter().convertString(operation.firstName()))
                .build());
    }

    public String getQuery2(LdbcQuery2 operation) {
        return prepare(InteractiveQuery.Query2, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Date0", getConverter().convertDateTime(operation.maxDate()))
                .build());
    }

    public String getQuery3(LdbcQuery3 operation) {
        return prepare(InteractiveQuery.Query3, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Country1", getConverter().convertString(operation.countryXName()))
                .put("Country2", getConverter().convertString(operation.countryYName()))
                .put("Date0", getConverter().convertDateTime(operation.startDate()))
                .put("Duration", getConverter().convertInteger(operation.durationDays()))
                .build());
    }

    public String getQuery4(LdbcQuery4 operation) {
        return prepare(InteractiveQuery.Query4, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Date0", getConverter().convertDateTime(operation.startDate()))
                .put("Duration", getConverter().convertInteger(operation.durationDays()))
                .build());
    }

    public String getQuery5(LdbcQuery5 operation) {
        return prepare(InteractiveQuery.Query5, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Date0", getConverter().convertDateTime(operation.minDate()))
                .build());
    }

    public String getQuery6(LdbcQuery6 operation) {
        return prepare(InteractiveQuery.Query6, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Tag", getConverter().convertString(operation.tagName()))
                .build());
    }

    public String getQuery7(LdbcQuery7 operation) {
        return prepare(InteractiveQuery.Query7, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .build());
    }

    public String getQuery8(LdbcQuery8 operation) {
        return prepare(InteractiveQuery.Query8, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .build());
    }

    public String getQuery9(LdbcQuery9 operation) {
        return prepare(InteractiveQuery.Query9, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Date0", getConverter().convertDateTime(operation.maxDate()))
                .build());
    }

    public String getQuery10(LdbcQuery10 operation) {
        return prepare(InteractiveQuery.Query10, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("HS0", getConverter().convertInteger(operation.month()))
                .put("HS1", getConverter().convertInteger(operation.month() % 12 + 1))
                .build());
    }

    public String getQuery11(LdbcQuery11 operation) {
        return prepare(InteractiveQuery.Query11, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Date0", getConverter().convertInteger(operation.workFromYear()))
                .put("Country", getConverter().convertString(operation.countryName()))
                .build());
    }

    public String getQuery12(LdbcQuery12 operation) {
        return prepare(InteractiveQuery.Query12, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("TagType", getConverter().convertString(operation.tagClassName()))
                .build());
    }

    public String getQuery13(LdbcQuery13 operation) {
        return prepare(InteractiveQuery.Query13, new ImmutableMap.Builder<String, String>()
                .put("Person1", getConverter().convertId(operation.person1Id()))
                .put("Person2", getConverter().convertId(operation.person2Id()))
                .build());
    }

    public String getQuery14(LdbcQuery14 operation) {
        return prepare(InteractiveQuery.Query14, new ImmutableMap.Builder<String, String>()
                .put("Person1", getConverter().convertId(operation.person1Id()))
                .put("Person2", getConverter().convertId(operation.person2Id()))
                .build());
    }

	public String getShortQuery1PersonProfile(LdbcShortQuery1PersonProfile operation) {
		return prepare(
		        InteractiveQuery.ShortQuery1PersonProfile,
                ImmutableMap.of("personId", getConverter().convertId(operation.personId()))
            );
	}

	public String getShortQuery2PersonPosts(LdbcShortQuery2PersonPosts operation) {
        return prepare(
                InteractiveQuery.ShortQuery2PersonPosts,
                ImmutableMap.of("personId", getConverter().convertId(operation.personId()))
        );
	}

	public String getShortQuery3PersonFriends(LdbcShortQuery3PersonFriends operation) {
        return prepare(
                InteractiveQuery.ShortQuery3PersonFriends,
                ImmutableMap.of("personId", getConverter().convertId(operation.personId()))
        );
	}

	public String getShortQuery4MessageContent(LdbcShortQuery4MessageContent operation) {
        return prepare(
                InteractiveQuery.ShortQuery4MessageContent,
                ImmutableMap.of("messageId", getConverter().convertId(operation.messageId()))
        );
	}

	public String getShortQuery5MessageCreator(LdbcShortQuery5MessageCreator operation) {
        return prepare(
                InteractiveQuery.ShortQuery5MessageCreator,
                ImmutableMap.of("messageId", getConverter().convertId(operation.messageId()))
        );
	}

	public String getShortQuery6MessageForum(LdbcShortQuery6MessageForum operation) {
        return prepare(
                InteractiveQuery.ShortQuery6MessageForum,
                ImmutableMap.of("messageId", getConverter().convertId(operation.messageId()))
        );
	}

	public String getShortQuery7MessageReplies(LdbcShortQuery7MessageReplies operation) {
        return prepare(
                InteractiveQuery.ShortQuery7MessageReplies,
                ImmutableMap.of("messageId", getConverter().convertId(operation.messageId()))
        );
	}

//	public List<String> getUpdate1AddPerson(LdbcUpdate1AddPerson operation) {
//		ArrayList<String> list = new ArrayList<String>();
//		list.add(getSql(InteractiveQuery.Update1AddPerson,
//				operation.personId(),
//				operation.personFirstName(),
//				operation.personLastName(),
//				operation.gender(),
//				convertDate(operation.birthday()),
//				convertDate(operation.creationDate()),
//				operation.locationIp(),
//				operation.browserUsed(),
//				operation.cityId()));
//		for (int i = 0; i < operation.workAt().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update1AddPersonCompanies,
//					operation.personId(),
//					operation.workAt().get(i).organizationId(),
//					operation.workAt().get(i).year()));
//		}
//		for (int i = 0; i < operation.emails().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update1AddPersonEmails,
//					operation.personId(),
//					operation.emails().get(i)));
//		}
//		for (int i = 0; i < operation.languages().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update1AddPersonLanguages,
//					operation.personId(),
//					operation.languages().get(i)));
//		}
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update1AddPersonTags,
//					operation.personId(),
//					operation.tagIds().get(i)));
//		}
//		for (int i = 0; i < operation.studyAt().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update1AddPersonUniversities,
//					operation.personId(),
//					operation.studyAt().get(i).organizationId(),
//					operation.studyAt().get(i).year()));
//		}
//		return list;
//	}

	public String getUpdate2AddPostLike(LdbcUpdate2AddPostLike operation) {
		return prepare(
		        InteractiveQuery.Update2AddPostLike,
                ImmutableMap.of(
                        "personId", getConverter().convertId(operation.personId()),
                        "postId", getConverter().convertId(operation.postId()),
                        "creationDate", getConverter().convertDateTime(operation.creationDate())
                )
        );
	}

//	public String getUpdate3AddCommentLike(LdbcUpdate3AddCommentLike operation) {
//		return getSql(InteractiveQuery.Update3AddCommentLike,
//				operation.personId(),
//				operation.commentId(),
//				convertDate(operation.creationDate()));
//	}
//
//	public List<String> getUpdate4AddForum(LdbcUpdate4AddForum operation) {
//		ArrayList<String> list = new ArrayList<String>();
//		list.add(getSql(InteractiveQuery.Update4AddForum,
//				operation.forumId(),
//				operation.forumTitle(),
//				convertDate(operation.creationDate()),
//				operation.moderatorPersonId()));
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update4AddForumTags,
//					operation.forumId(),
//					operation.tagIds().get(i)));
//		}
//		return list;
//	}
//
//
//	public String getUpdate5AddForumMembership(LdbcUpdate5AddForumMembership operation) {
//		return getSql(InteractiveQuery.Update5AddForumMembership,
//				operation.forumId(),
//				operation.personId(),
//				convertDate(operation.joinDate()));
//	}
//
//	public List<String> getUpdate6AddPost(LdbcUpdate6AddPost operation) {
//		ArrayList<String> list = new ArrayList<String>();
//		list.add(getSql(InteractiveQuery.Update6AddPost,
//				operation.postId(),
//				operation.imageFile(),
//				convertDate(operation.creationDate()),
//				operation.locationIp(),
//				operation.browserUsed(),
//				operation.language(),
//				operation.content(),
//				operation.length(),
//				operation.authorPersonId(),
//				operation.forumId(),
//				operation.countryId()));
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update6AddPostTags,
//					operation.postId(),
//					operation.tagIds().get(i)));
//		}
//		return list;
//	}
//
//	public List<String> getUpdate7AddComment(LdbcUpdate7AddComment operation) {
//		ArrayList<String> list = new ArrayList<String>();
//		list.add(getSql(InteractiveQuery.Update7AddComment,
//				operation.commentId(),
//				convertDate(operation.creationDate()),
//				operation.locationIp(),
//				operation.browserUsed(),
//				operation.content(),
//				operation.length(),
//				operation.authorPersonId(),
//				operation.countryId(),
//				operation.replyToPostId(),
//				operation.replyToCommentId()));
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update7AddCommentTags,
//					operation.commentId(),
//					operation.tagIds().get(i)));
//		}
//		return list;
//	}
//
//	public String getUpdate8AddFriendship(LdbcUpdate8AddFriendship operation) {
//		return getSql(InteractiveQuery.Update8AddFriendship,
//				operation.person1Id(),
//				operation.person2Id(),
//				convertDate(operation.creationDate()));
//	}

}
