package at.floriandulzky.apiprov.service;

import at.floriandulzky.apiprov.dao.ConfigurationDAO;
import at.floriandulzky.apiprov.dto.Api;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class ConfigurationService {

    @Inject
    ConfigurationDAO configurationDAO;

    public List<Api> getConfiguredApis(){
        return configurationDAO.getAllApis();
    }

    public void saveApi(Api api){
        configurationDAO.saveApi(api);
    }

}
