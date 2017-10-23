package org.raml.store;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

public class HttpCache {

	private static final String UTF_8 = "UTF-8";
	protected File root;
	protected boolean cacheOn = true;

	public HttpCache() {
		try {
			this.root = File.createTempFile("asa", "asas").getParentFile();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public HttpCache(File root) {
		super();
		this.root = root;
	}

	String escape(String url) {
		return url.replace(':', '_').replace('/', '_').replace('?', '_').replace('?', '_').replace('&', '_')
				.replace('=', '_');
	}

	public JSONObject getObject(String url) {
		Path resolve = root.toPath().resolve(escape(url));
		File file = resolve.toFile();
		if (file.exists() && cacheOn) {
			try {
				return new JSONObject(new String(Files.readAllBytes(resolve), UTF_8));
			} catch (JSONException | IOException e) {
				e.printStackTrace();
			}
		}
		try {
			HttpResponse<JsonNode> createRequest = createRequest(url);
			JSONObject object = createRequest.getBody().getObject();
			if (createRequest.getStatus() < 200 || createRequest.getStatus() >= 300) {
				if (createRequest.getStatus() != 404) {
					List<String> remainingLimit = createRequest.getHeaders().get("X-RateLimit-Remaining");
					if (remainingLimit!=null&&remainingLimit.contains("0")) {
						long currentTimeMillis = System.currentTimeMillis() / 1000;
						List<String> list = createRequest.getHeaders().get("X-RateLimit-Reset");
						if (list != null && !list.isEmpty()) {
							String reset = list.get(0);
							long delta = Long.parseLong(reset) - currentTimeMillis;
							try {
								System.out.println("Rate limit reached, sleeping for:" + delta + " seconds");
								Thread.sleep(delta * 1000);
							} catch (InterruptedException e) {

							}
							return getObject(url);
						}
					}
					throw new IllegalStateException(
							"Status:" + createRequest.getStatus() + " " + createRequest.getStatusText());
				}
			}
			try {
				Files.write(resolve, object.toString().getBytes(UTF_8));
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
			return object;
		} catch (UnirestException e) {
			throw new IllegalStateException(e);
		}
	}

	public JSONArray readArray(String url) {
		Path resolve = root.toPath().resolve(escape(url));
		File file = resolve.toFile();
		if (file.exists() && cacheOn) {
			try {
				return new JSONArray(new String(Files.readAllBytes(resolve), UTF_8));
			} catch (JSONException | IOException e) {
				e.printStackTrace();
			}
		}
		String string = url;
		ArrayList<JSONObject> result = new ArrayList<>();
		try {
			while (string != null) {
				HttpResponse<JsonNode> asJson;

				asJson = createRequest(string);
				if (asJson.getStatus() > 250) {
					if (asJson.getStatus() == 404) {
						return new JSONArray();
					}
					throw new IllegalStateException();
				}
				List<String> links = asJson.getHeaders().get("Link");
				string = null;
				if (links != null) {
					for (String s : links) {
						String[] linkWithRela = s.split(",");
						for (String link : linkWithRela) {
							String lurl = link.substring(1, link.lastIndexOf(">"));
							String relation = link.substring(link.indexOf("rel=\"") + 5, link.length() - 1);
							if (relation.equals("next")) {
								string = lurl;
							}
						}
					}
				}
				asJson.getBody().getArray().forEach(v -> {
					result.add((JSONObject) v);
				});
			}
		} catch (UnirestException e) {
			throw new IllegalStateException(e);
		}
		JSONArray arr = new JSONArray(result);
		try {
			Files.write(resolve, arr.toString().getBytes(UTF_8));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return arr;
	}

	static class AuthEntry {
		final String startsWith;
		final String password;
		final String user;

		public AuthEntry(String startsWith, String password, String user) {
			super();
			this.startsWith = startsWith;
			this.password = password;
			this.user = user;
		}
	}

	final ArrayList<AuthEntry> entries = new ArrayList<>();

	final ArrayList<Map<String, String>> extraArgs = new ArrayList<>();

	public void addBasicAuth(String url, String password, String user) {
		entries.add(new AuthEntry(url, password, user));
	}

	public void addExtra(String url, Map<String, String> args) {
		extraArgs.add(args);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HttpResponse<JsonNode> createRequest(String string) throws UnirestException {
		System.out.println(string);

		GetRequest basicAuth = Unirest.get(string);
		for (AuthEntry e : entries) {
			if (string.startsWith(e.startsWith)) {
				basicAuth = basicAuth.basicAuth(e.user, e.password);
			}
		}
		for (Map<String, String> args : extraArgs) {
			basicAuth.queryString((Map) args);
		}
		return basicAuth.asJson();
	}
}
	