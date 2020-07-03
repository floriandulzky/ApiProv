package at.floriandulzky.apiprov.plugin.postfilter;

public interface PostFilter {

    Object handle(Object routerResult);

}
