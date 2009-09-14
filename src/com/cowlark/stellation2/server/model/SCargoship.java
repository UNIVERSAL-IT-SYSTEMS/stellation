/* Server-side cargoship.
 * $Source: /cvsroot/stellation/stellation2/src/com/cowlark/stellation2/server/model/SCargoship.java,v $
 * $Date: 2009/09/14 22:15:34 $
 * $Author: dtrg $
 * $Revision: 1.3 $
 */

package com.cowlark.stellation2.server.model;

import com.cowlark.stellation2.common.Resources;
import com.cowlark.stellation2.common.S;
import com.cowlark.stellation2.common.data.Properties;
import com.cowlark.stellation2.common.data.PropertyStore;
import com.cowlark.stellation2.common.exceptions.ResourcesNotAvailableException;
import com.cowlark.stellation2.common.model.CCargoship;
import com.cowlark.stellation2.server.db.CClass;
import com.cowlark.stellation2.server.db.Property;

@CClass(name = CCargoship.class)
public class SCargoship extends SShip
{
    private static final long serialVersionUID = -6928260816716072277L;
    
    private static double MASS_SCALE = 300.0;

    @Property(scope = S.OWNER)
    private Resources _cargo = new Resources(0.0, 0.0, 0.0);
    
    @Override
    public SCargoship initialise()
    {
        super.initialise();
        return this;
    }
    
    @Override
    public SCargoship toCargoship()
    {
        return this;
    }
    
	public Properties getProperties()
	{
		return PropertyStore.Cargoship;
	}
	
    private void updateMass()
    {
		double mass = getProperties().getMass();
		
		mass += _cargo.getMetal() / MASS_SCALE;
		mass += _cargo.getAntimatter() / MASS_SCALE;
		mass += _cargo.getOrganics() / MASS_SCALE;
		
	    setMass(mass);
    }

	public Resources getCargo()
    {
    	return _cargo;
    }

	public SCargoship setCargo(Resources cargo)
    {
    	_cargo = cargo;
    	updateMass();
    	dirty();
    	return this;
    }
	
	public void destroyResources(Resources r) throws ResourcesNotAvailableException
	{
		double antimatter = _cargo.getAntimatter() - r.getAntimatter();
		double metal = _cargo.getMetal() - r.getMetal();
		double organics = _cargo.getOrganics() - r.getOrganics();
		
		if ((antimatter < 0.0) || (metal < 0.0) || (organics < 0.0))
			throw new ResourcesNotAvailableException();
		
		setCargo(new Resources(antimatter, metal, organics));
	}
	
	public void loadUnloadCmd(double antimatter, double metal, double organics)
			throws ResourcesNotAvailableException
	{
		SStar star = getStar();

		double shipantimatter = _cargo.getAntimatter() + antimatter;
		double shipmetal = _cargo.getMetal() + metal;
		double shiporganics = _cargo.getOrganics() + organics;

		Resources starr = star.getResources();
		double starantimatter = starr.getAntimatter() - antimatter;
		double starmetal = starr.getMetal() - metal;
		double starorganics = starr.getOrganics() - organics;
		
		if ((shipantimatter < 0.0) || (shipmetal < 0.0) || (shiporganics < 0.0))
			throw new ResourcesNotAvailableException();
		if ((starantimatter < 0.0) || (starmetal < 0.0) || (starorganics < 0.0))
			throw new ResourcesNotAvailableException();
		
		setCargo(new Resources(shipantimatter, shipmetal, shiporganics));
		star.setResources(new Resources(starantimatter, starmetal, starorganics));
	}
}
