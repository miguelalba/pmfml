/***************************************************************************************************
 * Copyright (c) 2015 Federal Institute for Risk Assessment (BfR), Germany
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors: Department Biological Safety - BfR
 **************************************************************************************************/
package de.bund.bfr.pmfml.sbml;

import org.apache.commons.lang3.StringUtils;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;

import java.util.Objects;

public class PMFSpeciesImpl implements PMFSpecies {

    private static final int LEVEL = 3;
    private static final int VERSION = 1;

    private static final String SOURCE_NS = "dc";
    private static final String SOURCE_TAG = "source";
    private static final String DETAIL_NS = "pmmlab";
    private static final String DETAIL_TAG = "detail";
    private static final String DESC_NS = "pmmlab";
    private static final String DESC_TAG = "desc";

    private static final String METADATA_NS = "pmf";
    private static final String METADATA_TAG = "metadata";

    private Species species;
    private String combaseCode;
    private String detail;
    private String description;

    /**
     * Creates a PMFSpeciesImpl instance from an Species.
     */
    public PMFSpeciesImpl(final Species species) {
        this.species = species;
        if (species.getAnnotation().isSetNonRDFannotation()) {
            // Parses annotation
            XMLNode metadata =
                    species.getAnnotation().getNonRDFannotation().getChildElement(METADATA_TAG, "");

            // Gets CAS number
            XMLNode sourceNode = metadata.getChildElement(SOURCE_TAG, "");
            if (sourceNode != null) {
                String wholeReference = sourceNode.getChild(0).getCharacters();
                combaseCode = wholeReference.substring(wholeReference.lastIndexOf('/') + 1);
            }

            // Gets description
            XMLNode detailNode = metadata.getChildElement(DETAIL_TAG, "");
            if (detailNode != null) {
                detail = detailNode.getChild(0).getCharacters();
            }

            // Gets dep description
            XMLNode descNode = metadata.getChildElement(DESC_TAG, "");
            if (descNode != null) {
                description = descNode.getChild(0).getCharacters();
            }
        }
    }

    /**
     * Creates a PMFSpeciesImpl instance from a compartment, id, name, substanceUnits, combaseCode,
     * detail and description.
     */
    public PMFSpeciesImpl(final String compartment, final String id, final String name,
                          final String substanceUnits, final String combaseCode, final String detail,
                          final String description) {
        species = new Species(id, name, LEVEL, VERSION);
        species.setCompartment(compartment);
        species.setSubstanceUnits(substanceUnits);
        species.setBoundaryCondition(BOUNDARY_CONDITION);
        species.setConstant(CONSTANT);
        species.setHasOnlySubstanceUnits(ONLY_SUBSTANCE_UNITS);

        if (combaseCode != null || detail != null || description != null) {
            // Builds PMF container
            XMLNode metadataNode = new XMLNode(new XMLTriple(METADATA_TAG, null, METADATA_NS));
            species.getAnnotation().setNonRDFAnnotation(metadataNode);

            // Builds reference tag
            if (combaseCode != null) {
                XMLNode refNode = new XMLNode(new XMLTriple(SOURCE_TAG, null, SOURCE_NS));
                refNode.addChild(new XMLNode("http://identifiers.org/ncim/" + combaseCode));
                metadataNode.addChild(refNode);
                this.combaseCode = combaseCode;
            }

            // Builds detail tag
            if (detail != null) {
                XMLNode detailNode = new XMLNode(new XMLTriple(DETAIL_TAG, null, DETAIL_NS));
                detailNode.addChild(new XMLNode(detail));
                metadataNode.addChild(detailNode);
                this.detail = detail;
            }

            // Builds dep description tag
            if (description != null) {
                XMLNode descNode = new XMLNode(new XMLTriple(DESC_TAG, null, DESC_NS));
                descNode.addChild(new XMLNode(description));
                metadataNode.addChild(descNode);
                this.description = description;
            }
        }
    }

    /**
     * Creates a PMFSpeciesImpl instance from a compartment, id, name and substanceUnits.
     */
    public PMFSpeciesImpl(String compartment, String id, String name, String substanceUnits) {
        this(compartment, id, name, substanceUnits, null, null, null);
    }

    /**
     * {@inheritDoc}
     */
    public Species getSpecies() {
        return species;
    }

    /**
     * {@inheritDoc}
     */
    public String getCompartment() {
        return species.getCompartment();
    }

    /**
     * {@inheritDoc}
     */
    public String getId() {
        return species.getId();
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return species.getName();
    }

    /**
     * {@inheritDoc}
     */
    public String getUnits() {
        return species.getUnits();
    }

    /**
     * {@inheritDoc}
     */
    public String getCombaseCode() {
        return combaseCode;
    }

    /**
     * {@inheritDoc}
     */
    public String getDetail() {
        return detail;
    }

    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    public void setCompartment(final String compartment) {
        species.setCompartment(compartment);
    }

    /**
     * {@inheritDoc}
     */
    public void setId(final String id) {
        species.setId(id);
    }

    /**
     * {@inheritDoc}
     */
    public void setName(final String name) {
        species.setName(name);
    }

    /**
     * {@inheritDoc}
     */
    public void setUnits(final String units) {
        species.setUnits(units);
    }

    /**
     * {@inheritDoc}
     */
    public void setCombaseCode(final String combaseCode) {
        if (StringUtils.isNotEmpty(combaseCode)) {
            this.combaseCode = combaseCode;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setDetail(final String detail) {
        if (StringUtils.isNotEmpty(detail)) {
            this.detail = detail;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setDescription(final String description) {
        if (StringUtils.isNotEmpty(description)) {
            this.description = description;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSetCombaseCode() {
        return combaseCode != null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSetDetail() {
        return detail != null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSetDescription() {
        return description != null;
    }

    @Override
    public String toString() {
        return "Species [compartment=" + species.getCompartment() + ", id=" + species.getId() + ", name=" + species
                .getName() + ", substanceUnits=" + species.getSubstanceUnits() + "]";
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        PMFSpeciesImpl other = (PMFSpeciesImpl) obj;
        return Objects.equals(species.getCompartment(), other.species.getCompartment()) &&
                Objects.equals(species.getId(), other.species.getId()) &&
                Objects.equals(species.getName(), other.species.getName()) &&
                Objects.equals(species.getSubstanceUnits(), other.species.getSubstanceUnits()) &&
                Objects.equals(combaseCode, other.combaseCode) &&
                Objects.equals(detail, other.detail) &&
                Objects.equals(description, other.description);
    }
}
