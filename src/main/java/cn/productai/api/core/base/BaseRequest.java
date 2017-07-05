package cn.productai.api.core.base;

import cn.productai.api.core.enums.ContentType;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.enums.LanguageType;
import cn.productai.api.core.helper.EnumHelper;

import java.util.HashMap;

public abstract class BaseRequest<T extends BaseResponse> {

    private static HashMap<Integer, String> _contentTypeDicts = EnumHelper.toHashMap(ContentType.class);
    private static HashMap<Integer, String> _languageDicts = EnumHelper.toHashMap(LanguageType.class);
    private static HashMap<Integer, String> _httpMethodDicts = EnumHelper.toHashMap(HttpMethod.class);
    private HashMap<String, String> _headers = new HashMap<>();

    private String host = "api.productai.cn";

    private String userAgent = "Product AI java SDK 1.0";

    private HttpMethod requestMethod = HttpMethod.POST;

    private ContentType contentType = ContentType.Default;

    private LanguageType language = LanguageType.English;

    /**
     * get the reponse class type
     * @return Class
     */
    public abstract Class<T> getResponseClass();

    /**
     * get the content-type header, e.g. application/x-www-form-urlencoded; charset=UTF-8
     * @return String
     */
    public String getContentTypeHeader() {
        return _contentTypeDicts.get(this.contentType.ordinal());
    }

    /**
     * get the HTTP Method header, e.g. POST or GET
     * @return String
     */
    public String getRequestMethodHeader(){
        return _httpMethodDicts.get(this.requestMethod.ordinal());
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public HttpMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(HttpMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public LanguageType getLanguage() {
        return language;
    }

    public void setLanguage(LanguageType language) {
        this.language = language;
        this.setHeader("Accept-Language", _languageDicts.get(language.ordinal()));
    }

    public abstract String getApiUrl();

    public abstract String getQueryString();

    public byte[] getQueryBytes() {
        return null;
    }

    public HashMap<String, String> getHeaders() {
        return _headers;
    }

    public void setHeader(String key, String value) {
        if (!_headers.containsKey(key)) {
            _headers.put(key, value);
        } else {
            _headers.replace(key, value);
        }
    }

    public void removeHeader(String key) {
        if (_headers.containsKey(key)) {
            _headers.remove(key);
        }
    }

    public BaseRequest(){
        this.setHeader("Accept-Encoding", "gzip, deflate");
        this.setLanguage(LanguageType.English);
    }
}