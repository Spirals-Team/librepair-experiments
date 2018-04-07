package ru.maksimov.andrey.golos4j.api.method;

import java.util.ArrayList;
import java.util.List;

/**
 * Method get info about post
 * 
 * @author <a href="mailto:onixbed@gmail.com">amaksimov</a>
 */
public class GetContent extends BaseMethod {

	public GetContent(Integer id, String account, String permlink) {
		super(id, SteemApis.SOCIAL_NETWORK, RequestMethods.GET_CONTENT);
		setParams(account, permlink);
	}

	public void setParams(String account, String permlink) {
		List<Object> params = new ArrayList<Object>();
		params.add(account);
		params.add(permlink);
		super.setParams(params);
	}
}
