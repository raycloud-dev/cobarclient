package com.alibaba.cobarclient.route;

import com.alibaba.cobarclient.Shard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class SimpleRouter implements Router {

    protected Logger logger = Logger.getLogger("SimpleRouter");

    private Map<String, RouteGroup> routes = new HashMap<String, RouteGroup>();

    private Set<Shard> EMPTY_SHARD_SET = new HashSet<Shard>();

    public SimpleRouter(Set<Route> routeSet) {
        if (!(routeSet == null || routeSet.isEmpty())) {
            for (Route route : routeSet) {
                if (!routes.containsKey(route.getSqlmap())) routes.put(route.getSqlmap(), new RouteGroup());
                if (route.getExpression() == null)
                    routes.get(route.getSqlmap()).setFallbackRoute(route);
                else
                    routes.get(route.getSqlmap()).getSpecificRoutes().add(route);
            }
        }
    }

    public Set<Shard> route(String action, Object argument) {
        Route resultRoute = findRoute(action, argument);
        if (resultRoute == null) {
            if (action != null) {
                String namespace = action.substring(0, action.lastIndexOf("."));
                resultRoute = findRoute(namespace, argument);
            }
        }
        if (resultRoute == null) {
            return EMPTY_SHARD_SET;
        } else {
            return resultRoute.getShards();
        }
    }


    protected Route findRoute(String action, Object argument) {
        if (routes.containsKey(action)) {
            RouteGroup routeGroup = routes.get(action);
            for (Route route : routeGroup.getSpecificRoutes()) {
                if (route.apply(action, argument)) {
                    return route;
                }
            }
            if (routeGroup.getFallbackRoute() != null && routeGroup.getFallbackRoute().apply(action, argument))
                return routeGroup.getFallbackRoute();
        }
        return null;
    }
}
