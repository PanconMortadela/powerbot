package org.powerbot.util.io;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.powerbot.util.Configuration;

/**
 * @author Paris
 */
public class Resources {
	private final static String SERVERDATAPATH = "/" + Configuration.NAME.toLowerCase() + "/server.ini";
	private static Map<String, Map<String, String>> serverData;

	public static class Paths {
		public static final String ROOT = "resources";
		public static final String SERVER = ROOT + "/server.ini";
		public static final String ROOT_IMG = ROOT + "/images";
		public static final String ICON = ROOT_IMG + "/icon.png";
		public static final String ICON_SMALL = ROOT_IMG + "/icon_small.png";
		public static final String INFORMATION = ROOT_IMG + "/information.png";
		public static final String ADD = ROOT_IMG + "/add.png";
		public static final String ARROWS = ROOT_IMG + "/arrows.png";
		public static final String CONTROL_PAUSE = ROOT_IMG + "/control_pause.png";
		public static final String CONTROL_PLAY = ROOT_IMG + "/control_play.png";
		public static final String CONTROL_STOP = ROOT_IMG + "/control_stop.png";
		public static final String DELETE = ROOT_IMG + "/delete.png";
		public static final String REPORT_KEY = ROOT_IMG + "/report_key.png";
		public static final String TAB_ADD = ROOT_IMG + "/tab_add.png";
		public static final String TAB_DELETE = ROOT_IMG + "/tab_delete.png";
		public static final String VERSION = ROOT + "/version.txt";
		public static final String WRENCH = ROOT_IMG + "/wrench.png";
	}

	public static URL getResourceURL(final String path) throws MalformedURLException {
		return Configuration.FROMJAR ? Configuration.class.getResource("/" + path) : new File(path).toURI().toURL();
	}

	public static Image getImage(final String resource) {
		try {
			return Toolkit.getDefaultToolkit().getImage(getResourceURL(resource));
		} catch (final Exception ignored) {
		}
		return null;
	}

	public static Map<String, Map<String, String>> getServerData() throws IOException {
		if (serverData == null) {
			final File local = new File(Paths.SERVER);
			if (local.exists()) {
				serverData = IniParser.deserialise(local);
			} else {
				final HttpURLConnection con = HttpClient.getHttpConnection(new URL(Configuration.URLs.CONTROL));
				final URL base = new URL(con.getHeaderField("Location"));
				final URL location = new URL(base, SERVERDATAPATH);
				serverData = IniParser.deserialise(HttpClient.openStream(location));
			}
		}
		return serverData;
	}

	public static Map<String, String> getServerLinks() {
		try {
			return getServerData().get("links");
		} catch (final IOException ignored) {
			return null;
		}
	}
}
