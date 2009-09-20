/* Server-side fleet.
 * $Source: /cvsroot/stellation/stellation2/src/com/cowlark/stellation2/server/model/SFleet.java,v $
 * $Date: 2009/09/20 21:50:35 $
 * $Author: dtrg $
 * $Revision: 1.6 $
 */

package com.cowlark.stellation2.server.model;

import com.cowlark.stellation2.common.Resources;
import com.cowlark.stellation2.common.S;
import com.cowlark.stellation2.common.data.Properties;
import com.cowlark.stellation2.common.data.PropertyStore;
import com.cowlark.stellation2.common.exceptions.ResourcesNotAvailableException;
import com.cowlark.stellation2.common.exceptions.StellationException;
import com.cowlark.stellation2.common.model.CFleet;
import com.cowlark.stellation2.server.db.CClass;
import com.cowlark.stellation2.server.db.Property;

@CClass(name = CFleet.class)
public class SFleet extends SObject
{
    private static final long serialVersionUID = 1074019239771839924L;

	@Property(scope = S.GLOBAL)
    private String _name;
    
    @Property(scope = S.OWNER)
    private int _jumpshipCount;
	
	public SFleet initialise(SStar location, SPlayer owner, String name)
    {
		super.initialise();
		
		setOwner(owner);
		_name = name;
		_jumpshipCount = 0;
		location.add(this);
		return this;
    }
	
    public SFleet toFleet()
    {
    	return this;
    }
    
	public Properties getProperties()
	{
		return PropertyStore.Fleet;
	}
	
	public String getName()
    {
	    return _name;
    }
	
	public SFleet setName(String name)
    {
	    _name = name;
	    dirty();
	    return this;
    }
	
	public final int getJumpshipCount()
    {
    	return _jumpshipCount;
    }

	public final SFleet setJumpshipCount(int jumpshipCount)
    {
    	/* Bit of a hack: when fleets change visibility, all units in the star
    	 * system may also change visibility as the player is able/unable to
    	 * see them. In order to make the client flush the cached object and
    	 * replace it with something with the correct scope, we mark everything
    	 * in the system as dirty. */
    	
		if (((jumpshipCount == 0) && (_jumpshipCount != 0)) ||
		    ((jumpshipCount != 0) && (_jumpshipCount == 0)))
		{
			SStar star = getLocation().toStar();
			if (star != null)
				dirtyAll();
		}
		
    	_jumpshipCount = jumpshipCount;
    	return this;
    }

	@Override
	public void consume(Resources r) throws ResourcesNotAvailableException
	{
		for (SObject o : this)
		{
			SCargoship cargoship = o.toCargoship();
			if (cargoship != null)
			{
				try
				{
					cargoship.destroyResources(r);
					return;
				}
				catch (ResourcesNotAvailableException e)
				{
				}
			}
		}
		
		getLocation().consume(r);
	}
	
	SFleet log(String message)
	{
		if (_jumpshipCount > 0)
			getOwner().log(message);
		return this;
	}
	
	public void checkObjectVisibleTo(SPlayer player)
		throws StellationException
	{
		/* Delegate to Star. */
		
		getLocation().checkObjectVisibleTo(player);
	}
}
