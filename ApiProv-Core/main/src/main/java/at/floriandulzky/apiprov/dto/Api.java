package at.floriandulzky.apiprov.dto;

public class Api {

    private String _id;

    private ApiData apiData;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ApiData getApiData() {
        return apiData;
    }

    public void setApiData(ApiData apiData) {
        this.apiData = apiData;
    }
}
