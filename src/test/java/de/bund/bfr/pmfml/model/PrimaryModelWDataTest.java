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
package de.bund.bfr.pmfml.model;

import de.bund.bfr.pmfml.numl.NuMLDocument;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;

import static org.junit.Assert.assertEquals;

public class PrimaryModelWDataTest {

    @Test
    public void test() {
        SBMLDocument modelDoc = ModelTestUtil.createDummyModel();
        NuMLDocument dataDoc = ModelTestUtil.createDummyData();
        PrimaryModelWData model =
                new PrimaryModelWData("model.sbml", modelDoc, "data.numl", dataDoc);

        assertEquals("model.sbml", model.getModelDocName());
        assertEquals(modelDoc, model.getModelDoc());
        assertEquals("data.numl", model.getDataDocName());
        assertEquals(dataDoc, model.getDataDoc());
    }
}
