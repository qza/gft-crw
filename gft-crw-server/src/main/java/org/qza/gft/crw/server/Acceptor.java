package org.qza.gft.crw.server;

import java.util.Arrays;
import java.util.List;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.ValidUtils;

/**
 * @author gft
 */
public class Acceptor {

	private final String[] categories_ban;

	private final List<String> BAN;

	public Acceptor(final Props props) {
		this.categories_ban = props.getBanCategories();
		this.BAN = Arrays.asList(categories_ban);
	}

	public boolean accept(Message message) {
		return checkBlanks(message);
//		return checkBlanks(message) && acceptCategory(message.getCategory());
	}

	private boolean acceptCategory(String category) {
		return !BAN.contains(category);
	}

	private boolean checkBlanks(Message message) {
		return message != null
				&& ValidUtils.notBlank(message.getName(),
						message.getCategory(), message.getImage(),
						message.getUrl());
	}

}
