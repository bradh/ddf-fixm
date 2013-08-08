/**
 * Copyright (c) Codice Foundation
 *
 * This is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or any later version. 
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details. A copy of the GNU Lesser General Public License is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 *
 **/
package net.frogmouth.ddf.fixminputtransformer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.Object;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import ddf.catalog.data.AttributeDescriptor;
import ddf.catalog.data.AttributeType.AttributeFormat;
import ddf.catalog.data.BasicTypes;
import ddf.catalog.data.Metacard;
import ddf.catalog.data.MetacardImpl;
import ddf.catalog.data.QualifiedMetacardType;
import ddf.catalog.data.MetacardTypeRegistry;
import ddf.catalog.CatalogFramework;
import ddf.catalog.transform.CatalogTransformerException;
import ddf.catalog.transform.InputTransformer;
import ddf.geo.formatter.CompositeGeometry;

// import com.vividsolutions.jts.geom.Coordinate;
// import com.vividsolutions.jts.geom.Geometry;
// import com.vividsolutions.jts.geom.GeometryFactory;
// import com.vividsolutions.jts.geom.PrecisionModel;

/**
 * Converts FIXM flight plans into a Metacard.
 * 
 * @author Brad Hards
 * @author bradh@frogmouth.net
 * @since DDF 2.2.0
 */
public class FixmInputTransformer implements InputTransformer {

	private static final String METACARD_TYPE_PROPERTY_KEY = "metacard-type";
	private static final String ID = "fixm";
	private static final String MIME_TYPE = "text/xml";

	private static final Logger LOGGER = Logger.getLogger(FixmInputTransformer.class);
	private CatalogFramework mCatalog;
	
	/**
	 * Transforms a FIXM flight plan into a {@link Metacard}
	 */
	@Override
	public Metacard transform(InputStream input) throws IOException, CatalogTransformerException {
		return transform(input, null);
	}

	@Override
	public Metacard transform(InputStream input, String id) throws IOException, CatalogTransformerException {

		if (input == null) {
			throw new CatalogTransformerException("Cannot transform null input.");
		}

		MetacardImpl metacard = new MetacardImpl(BasicTypes.BASIC_METACARD);
		// try {
			if (id != null) {
				metacard.setId(id);
			} else {
				metacard.setId(null);
			}

			metacard.setContentTypeName(MIME_TYPE);

			// TODO: set metadata on  metacard
		// }  catch (JAXBException e) {
		//	LOGGER.warn(e);
		//	throw new CatalogTransformerException(e);
		//}

		return metacard;
	}

	@Override
	public String toString() {
		return "InputTransformer {Impl=" + this.getClass().getName() + ", id=" + ID + ", mime-type=" + MIME_TYPE + "}";
	}

	public void setCatalog(CatalogFramework catalog) {
	    this.mCatalog = catalog;
	}	
}
