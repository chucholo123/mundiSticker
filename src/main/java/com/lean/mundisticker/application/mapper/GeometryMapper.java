package com.lean.mundisticker.application.mapper;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Component;

@Component
public class GeometryMapper {
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public Point toPoint(Double latitud, Double longitud) {
        if (latitud == null || longitud == null) {
            return null;
        }
        return geometryFactory.createPoint(new Coordinate(longitud, latitud));
    }

    public Double toLatitud(Point point) {
        return point != null ? point.getY() : null;
    }

    public Double toLongitud(Point point) {
        return point != null ? point.getX() : null;
    }
}
