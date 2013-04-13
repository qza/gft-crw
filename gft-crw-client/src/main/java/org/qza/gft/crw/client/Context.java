package org.qza.gft.crw.client;

import java.util.List;

import org.qza.gft.crw.ContextBase;
import org.qza.gft.crw.ServerAddress;

/**
 * @author gft
 */
public class Context extends ContextBase {

	final private Props props;

	public Context(final Props props) {
		this.props = props;
	}

	public Props getProps() {
		return props;
	}

	public List<ServerAddress> getServerList() {
		return props.getServerList();
	}

}
