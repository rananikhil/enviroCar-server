/*
 * Copyright (C) 2013 The enviroCar project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.envirocar.server.rest.encoding.rdf.linker;

import javax.ws.rs.core.UriBuilder;

import org.envirocar.server.core.entities.Measurement;
import org.envirocar.server.rest.encoding.rdf.RDFLinker;
import org.envirocar.server.rest.encoding.rdf.vocab.W3CGeo;
import org.envirocar.server.rest.resources.MeasurementsResource;
import org.envirocar.server.rest.resources.RootResource;
import org.envirocar.server.rest.rights.AccessRights;

import com.google.inject.Provider;
import com.hp.hpl.jena.rdf.model.Model;
import com.vividsolutions.jts.geom.Point;

/**
 *
 * @author Arne de Wall
 *
 */
public class W3CGeoMeasurementLinker implements RDFLinker<Measurement> {
    @Override
    public void link(Model m, Measurement t, AccessRights rights,
                     Provider<UriBuilder> uriBuilder) {
        UriBuilder measurementBuilder = uriBuilder.get()
                .path(RootResource.class).path(RootResource.MEASUREMENTS)
                .path(MeasurementsResource.MEASUREMENT);

        if (t.getGeometry() instanceof Point) {
            Point p = (Point) t.getGeometry();
            m.createResource(
                    measurementBuilder.build(t.getIdentifier()).toASCIIString())
                    .addLiteral(W3CGeo.lat, p.getY())
                    .addLiteral(W3CGeo.lon, p.getX());
        }
    }
}
